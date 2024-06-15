package com.ig.test.dto;


public class LineaCreditoDto {

    private Long idLineaCredito;


    private String nombreLineaCredito;

    private Double valorMinimo;


    private Double valorMaximo;


    private Integer plazoMaximo; // en meses

    public Long getIdLineaCredito() {
        return idLineaCredito;
    }

    public void setIdLineaCredito(Long idLineaCredito) {
        this.idLineaCredito = idLineaCredito;
    }

    public String getNombreLineaCredito() {
        return nombreLineaCredito;
    }

    public void setNombreLineaCredito(String nombreLineaCredito) {
        this.nombreLineaCredito = nombreLineaCredito;
    }

    public Double getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(Double valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public Double getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(Double valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public Integer getPlazoMaximo() {
        return plazoMaximo;
    }

    public void setPlazoMaximo(Integer plazoMaximo) {
        this.plazoMaximo = plazoMaximo;
    }
}
