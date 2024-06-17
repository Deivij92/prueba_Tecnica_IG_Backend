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
public class ClienteServiceImpl implements ServiceCliente {
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
            Optional<Cliente> clienteOptional = Optional.ofNullable(clienteRepository.findById(idCliente));
            if (clienteOptional.isPresent()) {
                Cliente cliente = clienteOptional.get();
            response =  deleteInfoLab(cliente);
            response = deleteInfoReferencia(cliente);
            response = deleteInfoPrestamos(cliente);

            clienteRepository.deleteById(clienteOptional.get().getId());
                response.setCodeResponse(200);
                response.setMessageResponse("Informacion del cliente eliminada correctamente");
            } else {
                response.setCodeResponse(404); // Referencias no encontradas
                response.setMessageResponse("Cliente no encontradas para el cliente dado");
            }

        } catch (Exception e) {
            response.setCodeResponse(500); // Error interno del servidor
            response.setMessageResponse("Error al eliminar el cliente: " + e.getMessage());
        }
        return response;
    }
    @Transactional
    public ResponseDto deleteInfoReferencia(Cliente cliente) {
        ResponseDto response = new ResponseDto();
        try {
            // Verificar si el cliente existe en la base de datos
                // Buscar las referencias asociadas al cliente
                List<ReferenciasClientes> referencias = repositoryReferencias.findByCliente(cliente);
                if (!referencias.isEmpty()) {
                    // Eliminar las referencias de la base de datos
                    repositoryReferencias.deleteAll(referencias);
                    response.setCodeResponse(200);
                    response.setMessageResponse("Informacion eliminada correctamente");
                } else {
                    response.setCodeResponse(404); // Referencias no encontradas
                    response.setMessageResponse("Referencias no encontradas para el cliente dado");
                }
        } catch (Exception e) {
            response.setCodeResponse(500); // Error interno del servidor
            response.setMessageResponse("Error al eliminar las referencias: " + e.getMessage());
        }
        return response;
    }

    @Transactional
    public ResponseDto deleteInfoLab(Cliente cliente) {
        ResponseDto response = new ResponseDto();
        try {
            // Buscar la información laboral asociada al cliente
            Optional<InfoLaboralCliente> infoLaboralOptional = repositoryInfoLAboral.findByCliente(cliente);
            if (infoLaboralOptional.isPresent()) {
                // Eliminar la información laboral
                InfoLaboralCliente infoLaboral = infoLaboralOptional.get();
                repositoryInfoLAboral.delete(infoLaboral);
                response.setCodeResponse(200);
                response.setMessageResponse("Información eliminada correctamente");
            } else {
                response.setCodeResponse(404); // Información laboral no encontrada
                response.setMessageResponse("Información laboral no encontrada para el cliente");
            }
        } catch (Exception e) {
            response.setCodeResponse(500); // Error interno del servidor
            response.setMessageResponse("Error al eliminar la información laboral: " + e.getMessage());
        }
        return response;
    }
    @Transactional
    public ResponseDto deleteInfoPrestamos(Cliente  cliente) {
        ResponseDto response = new ResponseDto();
        try {
            // Verificar si el cliente existe en la base de datos
                // Buscar las referencias asociadas al cliente
                List<Prestamo> prestamo = repositoryPrestamo.findByCliente(cliente);
                if (!prestamo.isEmpty()) {
                    // Eliminar los prestamos
                    repositoryPrestamo.deleteAll(prestamo);
                    response.setCodeResponse(200);
                    response.setMessageResponse("Informacion eliminada correctamente");
                } else {
                    response.setCodeResponse(404); // Referencias no encontradas
                    response.setMessageResponse("Prestamos no encontrados para el cliente");
                }
        } catch (Exception e) {
            response.setCodeResponse(500); // Error interno del servidor
            response.setMessageResponse("Error al eliminar las prestamos: " + e.getMessage());
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
                    return new ResponseDto(204, "Cliente con ID " + dto.getIdcliente() + " no encontrado");
                }
                Optional<ReferenciasClientes> referenciasExiste = repositoryReferencias.findByNumeroDocumento(dto.getNumeroDocumento());
                if (referenciasExiste.isPresent()) {
                    return new ResponseDto(409, "Referencia con la cedula  " + dto.getNumeroDocumento() + " ya existe");
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
                // Buscar la información laboral asociada al cliente
                Optional<InfoLaboralCliente> infoLaboralOptional = repositoryInfoLAboral.findById(idCliente);
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
        } catch (Exception e) {
            response.setCodeResponse(500); // Error interno del servidor
            response.setMessageResponse("Error al eliminar la información laboral: " + e.getMessage());
        }
        return response;
    }
    @Override
    @Transactional
    public ResponseDto deleteInfoRef(long idRef) {
        ResponseDto response = new ResponseDto();
        try {
            // Verificar si la referencia existe en la base de datos
            Optional<ReferenciasClientes> referenciaOptional = repositoryReferencias.findById(idRef);
            if (referenciaOptional.isPresent()) {
                // Eliminar la referencia de la base de datos
                repositoryReferencias.deleteById(idRef);
                response.setCodeResponse(200);
                response.setMessageResponse("Referencia eliminada correctamente");
            } else {
                response.setCodeResponse(404); // Referencia no encontrada
                response.setMessageResponse("Referencia no encontrada");
            }
        } catch (Exception e) {
            response.setCodeResponse(500); // Error interno del servidor
            response.setMessageResponse("Error al eliminar la referencia: " + e.getMessage());
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
    @Override
    public ReferenciasClientesDto obtnerInfoReferencias(long idReferencia) {
        Optional<ReferenciasClientes> referenciaOptional = repositoryReferencias.findById(idReferencia);
        if (referenciaOptional.isPresent()) {
            ReferenciasClientes referencia = referenciaOptional.get();
            return convertirDatos.convertToDtoReferenciaCliente(referencia);
        }
        // Si la referencia no existe, devuelve null o maneja esto de otra manera
        return new ReferenciasClientesDto();
    }
    @Override
    public ResponseDto UpdateInfoRef(ReferenciasClientesDto dto) {
        ResponseDto response = new ResponseDto();
        try {
            Optional<ReferenciasClientes> referenciaOptional = repositoryReferencias.findById(dto.getIdRef());
            if (referenciaOptional.isPresent()) {
                ReferenciasClientes referencia = referenciaOptional.get();
                // Actualizar los campos de la referencia con los datos del DTO
                referencia.setTipoDocumento(dto.getTipoDocumento());
                referencia.setNumeroDocumento(dto.getNumeroDocumento());
                referencia.setNombresApellidos(dto.getNombresApellidos());
                referencia.setResidencia(dto.getResidencia());
                referencia.setCiudad(dto.getCiudad());
                referencia.setTelefono(dto.getTelefono());
                referencia.setParentezco(dto.getParentezco());

                // Guardar la referencia actualizada en la base de datos
                repositoryReferencias.save(referencia);

                response.setCodeResponse(200);
                response.setMessageResponse("Referencia actualizada correctamente");
            } else {
                response.setCodeResponse(404); // Referencia no encontrada
                response.setMessageResponse("Referencia no encontrada");
            }
        } catch (Exception e) {
            response.setCodeResponse(500); // Error interno del servidor
            response.setMessageResponse("Error al actualizar la referencia: " + e.getMessage());
        }
        return response;
      }

}
