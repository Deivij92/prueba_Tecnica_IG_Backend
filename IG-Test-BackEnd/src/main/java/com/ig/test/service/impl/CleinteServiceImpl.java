package com.ig.test.service.impl;

import com.ig.test.dto.*;
import com.ig.test.model.Cliente;
import com.ig.test.model.InfoLaboralCliente;
import com.ig.test.model.Prestamo;
import com.ig.test.model.ReferenciasClientes;
import com.ig.test.model.repository.RepositoryCliente;
import com.ig.test.model.repository.RepositoryInfoLAboral;
import com.ig.test.model.repository.RepositoryPrestamo;
import com.ig.test.model.repository.RepositoryReferencias;
import com.ig.test.service.ServiceCliente;
import com.ig.test.util.ConvertirDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CleinteServiceImpl implements ServiceCliente {
    @Autowired
    private RepositoryCliente clienteRepository;

    @Autowired
    private RepositoryInfoLAboral repositoryInfoLAboral;

    @Autowired
    RepositoryReferencias repositoryReferencias;

    @Autowired
    private RepositoryPrestamo repositoryPrestamo;
    @Autowired
    private ConvertirDatos convertirDatos;
    @Override
    public ResponseDto saveCliente(ClienteDto clienteDto) {
        ResponseDto  responseDto = new  ResponseDto();
        try {
            // Verificar si el cliente ya existe por número de documento
            Optional<Cliente> clienteExistente = Optional.ofNullable(clienteRepository.findByNumeroDocumento(clienteDto.getNumeroDocumento()));

            if (clienteExistente.isPresent()) {
                return new ResponseDto(409, "Ya existe un cliente con el número de documento proporcionado");
            }
            Cliente cliente = convertirDatos.convertToEntityCliente(clienteDto);
            clienteRepository.save(cliente);
            Long idCliente = cliente.getId();
            responseDto.setCodeResponse(200L);
            responseDto.setMessageResponse("Cliente guardado correctamente ");
            responseDto.setClienteId(idCliente);
            //return new ResponseDto(200, "Cliente guardado correctamente " + idCliente);
            return responseDto;
        } catch (Exception e) {
            // Manejar errores de guardado
            return new ResponseDto(500, "Error al guardar el cliente: " + e.getMessage());
        }
    }

    @Override
    public ClienteDto ObtenerInfoCliente(long idCliente) {
        Optional<Cliente> clienteOptional = Optional.ofNullable(clienteRepository.findById(idCliente));
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            return convertirDatos.convertToDtoCliente(cliente);
        }
      return new ClienteDto();
    }

    @Override
    public ResponseDto updateInfoCliente(ClienteDto clienteDto) {
        ResponseDto response = new ResponseDto();
        try {
            // Verificar si el cliente existe en la base de datos
            Optional<Cliente> clienteOptional = clienteRepository.findById(clienteDto.getId());
            if (clienteOptional.isPresent()) {
                Cliente cliente = clienteOptional.get();
                // Actualizar los campos del cliente con los valores del ClienteDto
                cliente.setTipoDocumento(clienteDto.getTipoDocumento());
                cliente.setNumeroDocumento(clienteDto.getNumeroDocumento());
                cliente.setApellidos(clienteDto.getApellidos());
                cliente.setResidencia(clienteDto.getResidencia());
                cliente.setCiudad(clienteDto.getCiudad());
                cliente.setTelefono(clienteDto.getTelefono());
                cliente.setEmail(clienteDto.getEmail());
                // Guardar el cliente actualizado en la base de datos
                clienteRepository.save(cliente);
                response.setCodeResponse(200);
                response.setMessageResponse("Cliente actualizado correctamente");
            } else {
                response.setCodeResponse(404); // Cliente no encontrado
                response.setMessageResponse("Cliente no encontrado");
            }
        } catch (Exception e) {
            response.setCodeResponse(500); // Error interno del servidor
            response.setMessageResponse("Error al actualizar el cliente: " + e.getMessage());
        }

        return response;
    }

    @Override
    @Transactional
    public ResponseDto DeleteInfoCliente(long idCliente) {
        ResponseDto response = new ResponseDto();
        try {
            // Eliminar préstamos asociados al cliente
            List<Prestamo> prestamos = repositoryPrestamo.findByClienteId(idCliente);
            for (Prestamo prestamo : prestamos) {
                repositoryPrestamo.delete(prestamo);
            }

            // Eliminar referencias asociadas al cliente
            List<ReferenciasClientes> referencias = repositoryReferencias.findByClienteId(idCliente);
            for (ReferenciasClientes referencia : referencias) {
                repositoryReferencias.delete(referencia);
            }

            // Eliminar información laboral asociada al cliente
            Optional<InfoLaboralCliente> infoLaboralOptional = repositoryInfoLAboral.findByClienteId(idCliente);
            infoLaboralOptional.ifPresent(infoLaboral -> repositoryInfoLAboral.delete(infoLaboral));

            // Finalmente, eliminar el cliente
            Optional<Cliente> clienteOptional = Optional.ofNullable(clienteRepository.findById(idCliente));
            if (clienteOptional.isPresent()) {
                Cliente cliente = clienteOptional.get();
                clienteRepository.delete(cliente);
                response.setCodeResponse(200);
                response.setMessageResponse("Cliente eliminado correctamente");
            } else {
                response.setCodeResponse(404); // Cliente no encontrado
                response.setMessageResponse("Cliente no encontrado");
            }
        } catch (Exception e) {
            response.setCodeResponse(500); // Error interno del servidor
            response.setMessageResponse("Error al eliminar el cliente: " + e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseDto saveInfoLaboral(InfoLaboralClienteDto infoLaboralClienteDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            Optional<Cliente> clienteExistente = Optional.ofNullable(clienteRepository.findById(Long.parseLong(infoLaboralClienteDto.getIdcliente())));

            if (!clienteExistente.isPresent()) {
                // Cliente ya existe, retornar un mensaje de error
                return new ResponseDto(409, "El cliente para crear la informacion laboral no existe");
            } else {
                // Lógica de guardar información laboral del cliente
                InfoLaboralCliente infoLaboralCliente = convertirDatos.convertToEntityInfoLaboral(infoLaboralClienteDto);
                infoLaboralCliente.setCliente(clienteExistente.get());
                infoLaboralCliente = repositoryInfoLAboral.save(infoLaboralCliente);

                responseDto.setCodeResponse(200L);
                responseDto.setMessageResponse("Información laboral guardada correctamente");
                return responseDto;
            }
        } catch (Exception e) {
            return new ResponseDto(500, "Error al guardar la información laboral: " + e.getMessage());
        }
    }
    @Override
    public ResponseDto saveReferencias(List<ReferenciasClientesDto> referenciasClientesDtos) {
        try {
            List<ReferenciasClientes> referenciasClientes = new ArrayList<>();

            for (ReferenciasClientesDto dto : referenciasClientesDtos) {
                Optional<Cliente> clienteExistente = Optional.ofNullable(clienteRepository.findById(dto.getIdcliente()));
                if (!clienteExistente.isPresent()) {
                    return new ResponseDto(404, "Cliente con ID " + dto.getIdcliente() + " no encontrado");
                }
                ReferenciasClientes referenciaCliente = convertirDatos.convertToEntityReferenciasClientes(dto);
                referenciaCliente.setCliente(clienteExistente.get()); // Asignamos el cliente a la referencia
                referenciasClientes.add(referenciaCliente); // Añadimos la referencia a la lista
            }

            repositoryReferencias.saveAll(referenciasClientes); // Guardamos todas las referencias
            return new ResponseDto(200, "Referencias guardadas correctamente");
        } catch (Exception e) {
            return new ResponseDto(500, "Error al guardar las referencias: " + e.getMessage());
        }
    }

    @Override
    public List<ClienteDto> listarClientes() {
        List<Cliente> clientes =  clienteRepository.findAll();
        List<ClienteDto> clienteDtoList =  new ArrayList<>();
        for (Cliente clien : clientes){
            ClienteDto clienteDto =  convertirDatos.convertToDtoCliente(clien);
            clienteDtoList.add(clienteDto);
        }
        return clienteDtoList;
    }

    @Override
    public InfoLaboralClienteDto obtenerInfoLAboral(long idCliente) {
        Optional<Cliente> clienteOptional = Optional.ofNullable(clienteRepository.findById(idCliente));

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            Optional<InfoLaboralCliente> infoLaboralOptional = repositoryInfoLAboral.findByCliente(cliente);

            if (infoLaboralOptional.isPresent()) {
                InfoLaboralCliente infoLaboral = infoLaboralOptional.get();
                return convertirDatos.convertToDtoInfoLaboral(infoLaboral);
            } else {
                // Maneja el caso cuando no se encuentra la información laboral
                return new InfoLaboralClienteDto(); // o null, dependiendo de tu lógica de negocio
            }
        } else {
            // Maneja el caso cuando no se encuentra el cliente
            return new InfoLaboralClienteDto(); // o null, dependiendo de tu lógica de negocio
        }
    }

    @Override
    @Transactional
    public ResponseDto deleteInfoLaboral(long idCliente) {
        ResponseDto response = new ResponseDto();
        try {
            // Verificar si el cliente existe en la base de datos
            Optional<Cliente> clienteOptional = Optional.ofNullable(clienteRepository.findById(idCliente));
            if (clienteOptional.isPresent()) {
                // Buscar la información laboral asociada al cliente
                Optional<InfoLaboralCliente> infoLaboralOptional = repositoryInfoLAboral.findByClienteId(idCliente);
                if (infoLaboralOptional.isPresent()) {
                    // Eliminar la información laboral
                    InfoLaboralCliente infoLaboral = infoLaboralOptional.get();
                    repositoryInfoLAboral.delete(infoLaboral);
                    response.setCodeResponse(200);
                    response.setMessageResponse("Información laboral eliminada correctamente");
                } else {
                    response.setCodeResponse(404); // Información laboral no encontrada
                    response.setMessageResponse("Información laboral no encontrada para el cliente");
                }
            } else {
                response.setCodeResponse(404); // Cliente no encontrado
                response.setMessageResponse("Cliente no encontrado");
            }
        } catch (Exception e) {
            response.setCodeResponse(500); // Error interno del servidor
            response.setMessageResponse("Error al eliminar la información laboral: " + e.getMessage());
        }
        return response;
    }

    @Override
    public List<ReferenciasClientesDto> obtnerReferencias(long idCliente) {
        Optional<Cliente> clienteOptional = Optional.ofNullable(clienteRepository.findById(idCliente));
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            List<ReferenciasClientes> referencias = repositoryReferencias.findByCliente(cliente);
            return referencias.stream()
                    .map(referencia -> convertirDatos.convertToDtoReferenciaCliente(referencia))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList(); // Retornar una lista vacía si el cliente no existe
    }

    public ResponseDto modificarInfoLaboral(InfoLaboralClienteDto dto) {
        Optional<InfoLaboralCliente> infoLaboralOptional = repositoryInfoLAboral.findById(dto.getIdInfoLab());

        if (!infoLaboralOptional.isPresent()) {
            return new ResponseDto(409, "No se encontró la información laboral con el ID proporcionado");
        }

        InfoLaboralCliente infoLaboral = infoLaboralOptional.get();

        infoLaboral.setNitEmpresa(dto.getNitEmpresa());
        infoLaboral.setNombreEmpresa(dto.getNombreEmpresa());
        infoLaboral.setDireccion(dto.getDireccion());
        infoLaboral.setTelefono(dto.getTelefono());
        infoLaboral.setCargo(dto.getCargo());
        infoLaboral.setFechaVinculacion(dto.getFechaVinculacion());

        Optional<Cliente> clienteOptional = clienteOptional = Optional.ofNullable(clienteRepository.findById(Long.parseLong(dto.getIdcliente())));
        if (!clienteOptional.isPresent()) {
            return new ResponseDto(409, "No se encontró el cliente con el ID proporcionado");
        }
        infoLaboral.setCliente(clienteOptional.get());
        repositoryInfoLAboral.save(infoLaboral);
        return new ResponseDto(200, "Información laboral actualizada correctamente");
    }

}
