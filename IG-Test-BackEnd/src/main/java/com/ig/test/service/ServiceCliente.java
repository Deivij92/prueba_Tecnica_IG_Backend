package com.ig.test.service;

import com.ig.test.dto.ClienteDto;
import com.ig.test.dto.InfoLaboralClienteDto;
import com.ig.test.dto.ReferenciasClientesDto;
import com.ig.test.dto.ResponseDto;

import java.util.List;

public interface ServiceCliente {
    ResponseDto saveCliente(ClienteDto clienteDto);
    ResponseDto saveInfoLaboral(InfoLaboralClienteDto infoLaboralClienteDto);

    ResponseDto saveReferencias(List<ReferenciasClientesDto> referenciasClientesDtos);

}
