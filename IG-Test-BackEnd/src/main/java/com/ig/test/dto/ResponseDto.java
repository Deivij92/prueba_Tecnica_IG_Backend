package com.ig.test.dto;

import java.util.List;

public class ResponseDto {
    private long CodeResponse;
    private String MessageResponse;
    private ClienteDto clienteDto;
    private List<ClienteDto> clienteDtoList;
    private InfoLaboralClienteDto infoLaboralClienteDto;
    private List<ReferenciasClientesDto> referenciasClientesDtoList;

    private ReferenciasClientesDto referenciasClientesDto;
    private Long clienteId; // Nuevo campo

    public ResponseDto(long codeResponse, String messageResponse) {
        CodeResponse = codeResponse;
        MessageResponse = messageResponse;
    }

    public ResponseDto(long codeResponse, String messageResponse, Long clienteId) {
        CodeResponse = codeResponse;
        MessageResponse = messageResponse;
        this.clienteId = clienteId;
    }

    public ResponseDto() {
    }

    public long getCodeResponse() {
        return CodeResponse;
    }

    public void setCodeResponse(long codeResponse) {
        CodeResponse = codeResponse;
    }

    public String getMessageResponse() {
        return MessageResponse;
    }

    public void setMessageResponse(String messageResponse) {
        MessageResponse = messageResponse;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public ClienteDto getClienteDto() {
        return clienteDto;
    }

    public void setClienteDto(ClienteDto clienteDto) {
        this.clienteDto = clienteDto;
    }

    public List<ClienteDto> getClienteDtoList() {
        return clienteDtoList;
    }

    public void setClienteDtoList(List<ClienteDto> clienteDtoList) {
        this.clienteDtoList = clienteDtoList;
    }

    public InfoLaboralClienteDto getInfoLaboralClienteDto() {
        return infoLaboralClienteDto;
    }

    public void setInfoLaboralClienteDto(InfoLaboralClienteDto infoLaboralClienteDto) {
        this.infoLaboralClienteDto = infoLaboralClienteDto;
    }

    public List<ReferenciasClientesDto> getReferenciasClientesDtoList() {
        return referenciasClientesDtoList;
    }

    public void setReferenciasClientesDtoList(List<ReferenciasClientesDto> referenciasClientesDtoList) {
        this.referenciasClientesDtoList = referenciasClientesDtoList;
    }

    public ReferenciasClientesDto getReferenciasClientesDto() {
        return referenciasClientesDto;
    }

    public void setReferenciasClientesDto(ReferenciasClientesDto referenciasClientesDto) {
        this.referenciasClientesDto = referenciasClientesDto;
    }
}
