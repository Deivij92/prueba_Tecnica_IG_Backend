package com.ig.test.service.impl;

import com.ig.test.dto.*;
import com.ig.test.model.Cliente;
import com.ig.test.model.InfoLaboralCliente;
import com.ig.test.model.Prestamo;
import com.ig.test.model.ReferenciasClientes;
import com.ig.test.model.repository.*;
import com.ig.test.util.ConvertirDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl {
    @Autowired
    private ClienteDAO clienteDAO;
    @Autowired
    InfoLabDAO infoLabDAO;
    @Autowired
    ReferenciasDAO referenciasDAO;

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

    /**
     * Inicio del CRUD del cliente
     */
    @Transactional
    public ResponseDto saveCliente(ClienteDto clienteDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            Optional<Cliente> clienteExistente = Optional.ofNullable(clienteRepository.findByNumeroDocumento(clienteDto.getNumeroDocumento()));
            if (clienteExistente.isPresent()) {
                return new ResponseDto(409, "Ya existe un cliente con el número de documento proporcionado");
            }
            Cliente cliente = convertirDatos.convertToEntityCliente(clienteDto);
            clienteDAO.create(cliente);
            responseDto.setCodeResponse(200L);
            responseDto.setMessageResponse("Cliente guardado correctamente");
            responseDto.setClienteId(cliente.getId());
        } catch (Exception e) {
            responseDto.setCodeResponse(500L);
            responseDto.setMessageResponse("Error al guardar el cliente: " + e.getMessage());
        }
        return responseDto;
    }
    public ResponseDto getCliente(long idCliente) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setClienteDto(null);
        try{
            Optional<Cliente> clienteOptional = Optional.ofNullable(clienteDAO.read(idCliente));
            if (clienteOptional.isPresent()) {
                Cliente cliente = clienteOptional.get();
                ClienteDto clienteDto = convertirDatos.convertToDtoCliente(cliente);
                responseDto.setClienteDto(clienteDto);
                responseDto.setCodeResponse(200L);
                responseDto.setMessageResponse("Informacon del cliente listada");
            }else {
                responseDto.setCodeResponse(204L);
                responseDto.setMessageResponse("Cliente no encontrado");
            }
        }catch (Exception e){
            responseDto.setCodeResponse(500);
            responseDto.setMessageResponse("Se ha presentado un error al obtener la inf. del cleinte : " + e);
        }
        return responseDto;
    }
    @Transactional
    public ResponseDto updateCliente(Long id, ClienteDto clienteDto) {
        try {
            Cliente cliente = clienteDAO.read(id);
            if (cliente != null) {
                Cliente clienteActualizado = convertirDatos.convertToEntityCliente(clienteDto);
                clienteDAO.update(clienteActualizado);
                return new ResponseDto(200L, "Cliente actualizado correctamente");
            } else {
                return new ResponseDto(404, "Cliente no encontrado");
            }
        } catch (Exception e) {
            return new ResponseDto(500, "Error al actualizar el cliente: " + e.getMessage());
        }
    }
    @Transactional(readOnly = true)
    public ResponseDto getAllClientes() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setClienteDtoList(null);
        try {
            List<Cliente> clientes = clienteDAO.listAll();
            List<ClienteDto> clienteDtoList =  new ArrayList<>();
            for (Cliente clien : clientes){
                ClienteDto clienteDto =  convertirDatos.convertToDtoCliente(clien);
                clienteDtoList.add(clienteDto);
                responseDto.setClienteDtoList(clienteDtoList);
                responseDto.setCodeResponse(200L);
                responseDto.setMessageResponse("Clientes obtenidos correctamente");
            }
        } catch (Exception e) {
            responseDto.setCodeResponse(500L);
            responseDto.setMessageResponse("Error al obtener los clientes: " + e.getMessage());
        }
        return responseDto;
    }
    @Transactional
    public ResponseDto DeleteInfoCliente(long idCliente) {
        ResponseDto response = new ResponseDto();
        try {
            Optional<Cliente> clienteOptional = Optional.ofNullable(clienteDAO.read(idCliente));
            if (clienteOptional.isPresent()) {
                Cliente cliente = clienteOptional.get();
            //response =  deleteInfoLab(cliente);
          //  response = deleteInfoReferencia(cliente);
         //   response = deleteInfoPrestamos(cliente);

                clienteDAO.delete(cliente);
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
    /**
     * Fin CRUD Cliente
     * Incio CRUD info - Lab
     */
    @Transactional
    public ResponseDto saveInfoLaboral(InfoLaboralClienteDto infoLaboralClienteDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            Optional<Cliente> clienteExistente = Optional.ofNullable(clienteDAO.read(infoLaboralClienteDto.getIdcliente()));

            if (!clienteExistente.isPresent()) {
                // Cliente ya existe, retornar un mensaje de error
                return new ResponseDto(409, "El cliente para crear la informacion laboral no existe");
            } else {
                // Lógica de guardar información laboral del cliente
                InfoLaboralCliente infoLaboralCliente = convertirDatos.convertToEntityInfoLaboral(infoLaboralClienteDto);
                infoLaboralCliente.setCliente(clienteExistente.get());
                infoLaboralCliente = infoLabDAO.create(infoLaboralCliente);
                responseDto.setCodeResponse(200L);
                responseDto.setMessageResponse("Información laboral guardada correctamente");
                return responseDto;
            }
        } catch (Exception e) {
            return new ResponseDto(500, "Error al guardar la información laboral: " + e.getMessage());
        }
    }
    @Transactional
    public ResponseDto modificarInfoLaboral(InfoLaboralClienteDto dto) {
        Optional<InfoLaboralCliente> infoLaboralOptional = Optional.ofNullable(infoLabDAO.read(dto.getIdInfoLab()));

        if (!infoLaboralOptional.isPresent()) {
            return new ResponseDto(409, "No se encontró la información laboral con el ID proporcionado");
        }

        InfoLaboralCliente infoLaboral = infoLaboralOptional.get();

        infoLaboral = convertirDatos.convertToEntityInfoLaboral(dto);

        Optional<Cliente> clienteOptional = clienteOptional = Optional.ofNullable(clienteDAO.read(dto.getIdcliente()));
        if (!clienteOptional.isPresent()) {
            return new ResponseDto(409, "No se encontró el cliente con el ID proporcionado");
        }
        infoLaboral.setCliente(clienteOptional.get());
        infoLabDAO.update(infoLaboral);
        return new ResponseDto(200, "Información laboral actualizada correctamente");
    }
    @Transactional
    public ResponseDto obtenerInfoLAboral(long idCliente) {
        ResponseDto responseDto = new ResponseDto();
        try {
            List<InfoLaboralCliente> infoLaboralList = infoLabDAO.listAllID(idCliente);
            if (!infoLaboralList.isEmpty()) {
                // Tomar el primer elemento de la lista, suponiendo que solo hay uno
                InfoLaboralCliente infoLaboral = infoLaboralList.get(0);
                InfoLaboralClienteDto dto = convertirDatos.convertToDtoInfoLaboral(infoLaboral);
                responseDto.setCodeResponse(200);
                responseDto.setMessageResponse("Info Laboral obtenida correctamente");
                responseDto.setInfoLaboralClienteDto(dto);
            } else {
                responseDto.setCodeResponse(204);
                responseDto.setMessageResponse("No se encontró información laboral");
            }
        } catch (Exception e) {
            responseDto.setCodeResponse(500);
            responseDto.setMessageResponse("Error al obtener la información laboral: " + e.getMessage());
        }
        return responseDto;
    }
    @Transactional
    public ResponseDto deleteInfoLab(long idLab) {
        ResponseDto response = new ResponseDto();
        try {
            // Buscar la información laboral asociada al cliente
            Optional<InfoLaboralCliente> infoLaboralOptional = Optional.ofNullable(infoLabDAO.read(idLab));
            if (infoLaboralOptional.isPresent()) {
                // Eliminar la información laboral
                InfoLaboralCliente infoLaboral = infoLaboralOptional.get();
                infoLabDAO.delete(infoLaboral);
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
    public ResponseDto obtnerInfoReferencias(long idReferencia) {
        ResponseDto response = new ResponseDto();
        Optional<ReferenciasClientes> referenciaOptional = Optional.ofNullable(referenciasDAO.read(idReferencia));
        if (referenciaOptional.isPresent()) {
            ReferenciasClientes referencia = referenciaOptional.get();
            ReferenciasClientesDto referenciasClientesDto = convertirDatos.convertToDtoReferenciaCliente(referencia);
            response.setReferenciasClientesDto(referenciasClientesDto);
            response.setCodeResponse(200);
            response.setMessageResponse("Informacion de la refenecia.");
        }else {
            response.setCodeResponse(204);
            response.setMessageResponse("No se encontro la referncioa para el cliente");
        }
        return response;
    }/**/
    /**
     * Fin CRUD info - Lab
     * Incio CRUD Refe
     */
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
    

    @Transactional
    public ResponseDto saveReferencias(List<ReferenciasClientesDto> referenciasClientesDtos) {
        try {
            ReferenciasClientes referenciasClientes = new ReferenciasClientes();
            Optional<Cliente> clienteExistente = Optional.ofNullable(clienteDAO.read(referenciasClientesDtos.get(0).getIdcliente()));
            if (!clienteExistente.isPresent()) {
                return new ResponseDto(204, "Cliente con ID " + referenciasClientesDtos.get(0).getIdcliente() + " no encontrado");
            }
            referenciasClientes.setCliente(clienteExistente.get());

            if (referenciasClientesDtos.get(0).getActionType().equals("CREATE")){
                for (ReferenciasClientesDto dto : referenciasClientesDtos) {
                    referenciasClientes = convertirDatos.convertToEntityReferenciasClientes(dto, clienteExistente.get());
                    //ReferenciasClientes referenciaCliente = convertirDatos.convertToEntityReferenciasClientes(dto,  null);
                     // Asignamos el cliente a la referencia
                    referenciasDAO.create(referenciasClientes);
                }
            }else {
                referenciasClientes = convertirDatos.convertToEntityReferenciasClientes(referenciasClientesDtos.get(0), clienteExistente.get());
                this.UpdateInfoRef(referenciasClientes);
            }

             // Guardamos todas las referencias
            return new ResponseDto(200, "Referencias guardadas/actualizadas correctamente");
        } catch (Exception e) {
            return new ResponseDto(500, "Error al guardar las referencias: " + e.getMessage());
        }
    }

    @Transactional
    public ResponseDto UpdateInfoRef(ReferenciasClientes dto) {
        ResponseDto response = new ResponseDto();
        try {
            Optional<ReferenciasClientes> referenciaOptional = Optional.ofNullable(referenciasDAO.read(dto.getId()));
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
                //repositoryReferencias.save(referencia);
                referenciasDAO.update(referencia);

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
    @Transactional
    public ResponseDto obtnerListaReferencias(long idCliente) {
        ResponseDto responseDto = new ResponseDto();
        Optional<Cliente> clienteOptional = Optional.ofNullable(clienteDAO.read(idCliente));
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            List<ReferenciasClientes> referencias = referenciasDAO.listAllID(idCliente);
            List<ReferenciasClientesDto> referenciasClientesDtoList =  new ArrayList<>();
            for (ReferenciasClientes referenciasClientes : referencias){
                ReferenciasClientesDto dto = convertirDatos.convertToDtoReferenciaCliente(referenciasClientes);
                referenciasClientesDtoList.add(dto);
                responseDto.setReferenciasClientesDtoList(referenciasClientesDtoList);
                responseDto.setCodeResponse(200L);
                responseDto.setMessageResponse("Referencias listadas correctamente.");
            }
        }
        return responseDto;
    }
    @Transactional
    public ResponseDto deleteInfoReferencia(long idRef) {
        ResponseDto response = new ResponseDto();
        try {
           Optional<ReferenciasClientes> referencias = Optional.ofNullable(referenciasDAO.read(idRef));
            if (referencias.isPresent()) {
                ReferenciasClientes referenciasClientes = referencias.get();
                referenciasDAO.delete(referenciasClientes);
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
}
