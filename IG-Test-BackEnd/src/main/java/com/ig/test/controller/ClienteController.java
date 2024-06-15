package com.ig.test.controller;

import com.ig.test.dto.*;
import com.ig.test.service.ServiceCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("api/dinerofacil")
public class ClienteController {
    @Autowired
    private ServiceCliente serviceCliente;



    // Endpoint para crear un cliente
    @PostMapping("/crear")
    public ResponseEntity<ResponseDto> crearCliente(@RequestBody ClienteDto clienteDto) {
        ResponseDto responseDto = serviceCliente.saveCliente(clienteDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @PostMapping("/add-inf-lab")
    public ResponseEntity<ResponseDto> crearInfoLab(@RequestBody InfoLaboralClienteDto infoLaboralClienteDto) {
        ResponseDto responseDto = serviceCliente.saveInfoLaboral(infoLaboralClienteDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @PostMapping("/add-referencias")
    public ResponseEntity<ResponseDto> saveReferencias(@RequestBody List<ReferenciasClientesDto> referenciasClientesDtos) {
        ResponseDto response;
        try {
            response = serviceCliente.saveReferencias(referenciasClientesDtos);
            if (response.getCodeResponse() == 200) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(500, "Error al procesar la solicitud: " + e.getMessage()));
        }
    }
    @PostMapping("/listarcliente")
    public List<ClienteDto> listarClientes() {
        return serviceCliente.listarClientes();
    }
}
