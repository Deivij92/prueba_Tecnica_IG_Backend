package com.ig.test.service;

import com.ig.test.dto.ClienteDto;
import com.ig.test.dto.InfoLaboralClienteDto;
import com.ig.test.dto.ReferenciasClientesDto;
import com.ig.test.dto.ResponseDto;

import java.util.List;

public interface ServiceCliente {
    ResponseDto saveCliente(ClienteDto clienteDto);
    ClienteDto ObtenerInfoCliente(long idcleinte);
    List<ClienteDto> listarClientes();
    ResponseDto updateInfoCliente(ClienteDto clienteDto);
    ResponseDto DeleteInfoCliente(long idcliente);

    ResponseDto saveInfoLaboral(InfoLaboralClienteDto infoLaboralClienteDto);
    ResponseDto modificarInfoLaboral(InfoLaboralClienteDto infoLaboralClienteDto);
    InfoLaboralClienteDto obtenerInfoLAboral(long idCliente);
    ResponseDto deleteInfoLaboral(long idcliente);

    ResponseDto saveReferencias(List<ReferenciasClientesDto> referenciasClientesDtos);
    List<ReferenciasClientesDto> obtnerReferencias(long idCliente);
    ReferenciasClientesDto obtnerInfoReferencias(long idCliente);
    ResponseDto UpdateInfoRef(ReferenciasClientesDto  dto);
    ResponseDto deleteInfoRef(long idcliente);

}
