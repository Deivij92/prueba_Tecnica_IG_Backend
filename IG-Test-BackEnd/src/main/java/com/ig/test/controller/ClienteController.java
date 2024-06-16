package com.ig.test.controller;

import com.ig.test.dto.*;
import com.ig.test.service.ServiceCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.OutputKeys;
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
    }@PostMapping("/actualizar")
    public ResponseEntity<ResponseDto> actualizarCliente(@RequestBody ClienteDto clienteDto) {
        ResponseDto responseDto = serviceCliente.updateInfoCliente(clienteDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDto> obtenerInfoCliente(@PathVariable long idCliente) {
        ClienteDto clienteDto = serviceCliente.ObtenerInfoCliente(idCliente);
        if (clienteDto != null) {
            return ResponseEntity.ok(clienteDto);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteCliente(@PathVariable("id") long idCliente) {
        ResponseDto responseDto = serviceCliente.DeleteInfoCliente(idCliente);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    @DeleteMapping("/{id}/laboral")
    public ResponseEntity<ResponseDto> deleteInfoLab(@PathVariable("id") long idCliente) {
        ResponseDto responseDto = serviceCliente.deleteInfoLaboral(idCliente);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
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
    @GetMapping("/{idCliente}/infoLaboral")
    public ResponseEntity<InfoLaboralClienteDto> obtenerInfoLaboral(@PathVariable long idCliente) {
        InfoLaboralClienteDto infoLaboral =serviceCliente.obtenerInfoLAboral(idCliente);

        if (infoLaboral != null) {
            return ResponseEntity.ok(infoLaboral);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }
    @GetMapping("/{idCliente}/referencias")
    public ResponseEntity<List<ReferenciasClientesDto>> obtenerReferencias(@PathVariable long idCliente) {
        List<ReferenciasClientesDto> referencias = serviceCliente.obtnerReferencias(idCliente);
        return ResponseEntity.ok(referencias);
    }
    @GetMapping("/referencia/{idReferencia}")
    public ResponseEntity<ReferenciasClientesDto> obtenerReferencia(@PathVariable long idReferencia) {
        ReferenciasClientesDto referencia = serviceCliente.obtnerInfoReferencias(idReferencia);
        if (referencia != null) {
            return ResponseEntity.ok(referencia);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/updateinfolab")
    public ResponseEntity<ResponseDto> modificarInfoLaboral(@RequestBody InfoLaboralClienteDto dto) {
        ResponseDto response = serviceCliente.modificarInfoLaboral(dto);
        if (response.getCodeResponse() == 200) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
    @DeleteMapping("/deleteref/{idRef}")
    public ResponseEntity<ResponseDto> eliminarReferencia(@PathVariable long idRef) {
        ResponseDto response = serviceCliente.deleteInfoRef(idRef);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PutMapping("/updatereferencia")
    public ResponseEntity<ResponseDto> actualizarReferencia(@RequestBody ReferenciasClientesDto dto) {
        ResponseDto response = serviceCliente.UpdateInfoRef(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
