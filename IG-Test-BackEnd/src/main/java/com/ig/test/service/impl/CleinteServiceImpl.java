package com.ig.test.service.impl;

import com.ig.test.dto.*;
import com.ig.test.model.Cliente;
import com.ig.test.model.InfoLaboralCliente;
import com.ig.test.model.Prestamo;
import com.ig.test.model.ReferenciasClientes;
import com.ig.test.model.repository.RepositoryCliente;
import com.ig.test.model.repository.RepositoryInfoLAboral;
import com.ig.test.model.repository.RepositoryReferencias;
import com.ig.test.service.ServiceCliente;
import com.ig.test.util.ConvertirDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CleinteServiceImpl implements ServiceCliente {
    @Autowired
    private RepositoryCliente clienteRepository;

    @Autowired
    private RepositoryInfoLAboral repositoryInfoLAboral;

    @Autowired
    RepositoryReferencias repositoryReferencias;

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


 /*   @Override
    public ResponseDto updateCliente(ClienteDto clienteDto) {
        return null;
    }

    @Override
    public ClienteDto getClienteById(Long clienteId) {
        return null;
    }

    @Override
    public ResponseDto deleteCliente(Long clienteId) {
        return null;
    }*/

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


/*    @Override
    public ResponseDto updateInfoLaboral(InfoLaboralClienteDto infoLaboralClienteDto) {
        return null;
    }

    @Override
    public InfoLaboralClienteDto getInfoLaboralByClienteId(Long clienteId) {
        return null;
    }

    @Override
    public ResponseDto deleteInfoLaboral(Long clienteId) {
        return null;
    }*/
}
