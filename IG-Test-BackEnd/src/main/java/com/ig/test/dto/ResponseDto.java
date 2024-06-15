package com.ig.test.dto;

public class ResponseDto {
    private long CodeResponse;
    private String MessageResponse;

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
}
