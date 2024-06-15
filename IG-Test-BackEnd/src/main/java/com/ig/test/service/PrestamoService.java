package com.ig.test.service;

import com.ig.test.dto.LineaCreditoDto;
import com.ig.test.dto.PrestamoDto;
import com.ig.test.dto.ResponseDto;

import java.util.List;

public interface PrestamoService {

    ResponseDto solictarPrestamo (PrestamoDto prestamoDto);

    List<LineaCreditoDto> listarPrestamos();
    List<PrestamoDto> listarPrestamosSolicitados();

}
