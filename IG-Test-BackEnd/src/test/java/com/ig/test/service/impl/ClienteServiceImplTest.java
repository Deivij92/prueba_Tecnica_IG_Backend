package com.ig.test.service.impl;

import com.ig.test.dto.ClienteDto;
import com.ig.test.dto.ReferenciasClientesDto;
import com.ig.test.dto.ResponseDto;
import com.ig.test.model.Cliente;
import com.ig.test.model.ReferenciasClientes;
import com.ig.test.model.repository.RepositoryCliente;
import com.ig.test.model.repository.RepositoryReferencias;
import com.ig.test.service.ServiceCliente;
import com.ig.test.util.ConvertirDatos;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
 import static org.assertj.core.api.Assertions.*;
class ClienteServiceImplTest {
    @Mock
    private ConvertirDatos convertirDatos;

    @Autowired
    RepositoryReferencias repositoryReferencias;

    @Autowired
    private ServiceCliente serviceCliente;

    @Autowired
    private RepositoryCliente clienteRepository;

    @Test
    void deleteInfoReferencia() {
    }

    @Test
    void deleteInfoLab() {
    }

    @Test
    void deleteInfoPrestamos() {
    }

    @Test
    void saveInfoLaboral() {
    }
    @Test
    void saveReferencias() {
        // Preparar datos de prueb
            // Preparar datos de prueba
            long idCliente = 1L;
            ReferenciasClientesDto referenciaDto = new ReferenciasClientesDto();
            referenciaDto.setNumeroDocumento("1234567890");
            referenciaDto.setNombresApellidos("Juan Pérez");
            referenciaDto.setTelefono("123456789");
            referenciaDto.setTipoDocumento("CC");
            referenciaDto.setParentezco("Amigo");
            referenciaDto.setResidencia("Bogota");
            referenciaDto.setCiudad("Bogota");
            referenciaDto.setIdcliente(idCliente); // Ajustar el ID del cliente correctamente

            Cliente cliente = new Cliente();
            cliente.setId(idCliente);

            ReferenciasClientes referencia = new ReferenciasClientes();
            referencia.setId(1L); // Ajustar el ID de la referencia según tu lógica de negocio
           referencia.setNumeroDocumento("1234567890");
           referencia.setNombresApellidos("Juan Pérez");
           referencia.setTelefono("123456789");
           referencia.setTipoDocumento("CC");
           referencia.setParentezco("Amigo");
           referencia.setResidencia("Bogota");
           referencia.setCiudad("Bogota");

            // Configurar el comportamiento del mock
            //when(convertirDatos.convertToEntityReferenciasClientes(referenciaDto)).thenReturn(referencia);
            when(repositoryReferencias.save(any(ReferenciasClientes.class))).thenReturn(referencia);

            // Ejecutar el método a probar
            List<ReferenciasClientesDto> referenciasClientes = new ArrayList<>();
            referenciasClientes.add(referenciaDto);
            serviceCliente.saveReferencias(referenciasClientes);

            // Verificar que se llame al método save del repository con la entidad correcta
            verify(repositoryReferencias, times(1)).save(any(ReferenciasClientes.class));
        }

    @Test
    void listarClientes() {
    }

    @Test
    void obtenerInfoLAboral() {
    }

    @Test
    void deleteInfoLaboral() {
    }

    @Test
    void deleteInfoRef() {
    }

    @Test
    void obtnerReferencias() {
    }

    @Test
    void modificarInfoLaboral() {
    }

    @Test
    void obtnerInfoReferencias() {
    }

    @Test
    void updateInfoRef() {
    }

    @Test
    void saveCliente() {
        // Preparar datos de prueba
        Cliente clienteDto = new Cliente();
        clienteDto.setId(1L); // Simulamos que el ID se genera automáticamente
        clienteDto.setTipoDocumento("CC");
        clienteDto.setNumeroDocumento("123456789");

        clienteDto.setApellidos("Juan");
        clienteDto.setApellidos("Pérez");
        clienteDto.setResidencia("Bogotá");
        clienteDto.setCiudad("Bogotá");
        clienteDto.setTelefono("123456789");
        clienteDto.setEmail("juan@example.com");

        Cliente clienteSave = clienteRepository.save(clienteDto);
        // Configurar el comportamiento del mock
         assertThat(clienteSave).isNotNull();
         assertThat(clienteSave).isNotNull();
         assertThat(clienteSave.getId()).isGreaterThan(0
         );
    }
}