package com.ig.test.controller;

import com.ig.test.dto.LineaCreditoDto;
import com.ig.test.dto.PrestamoDto;
import com.ig.test.dto.ResponseDto;
import com.ig.test.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/dinerofacil")
public class PrestamoController {

    @Autowired
    PrestamoService prestamoService;

    @PostMapping("/prestamos")
    public ResponseEntity<ResponseDto> solicitarPrestamo(@RequestBody PrestamoDto prestamoDto) {
        ResponseDto response;
        try {
            response = prestamoService.solictarPrestamo(prestamoDto);
            if (response.getCodeResponse() == 200) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(500, "Error al procesar la solicitud de préstamo: " + e.getMessage()));
        }
    }
    @GetMapping("/listarLineaCredito")
    public List<LineaCreditoDto> listarPrestamos() {
        return prestamoService.listarPrestamos();
    }

}