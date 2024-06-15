package com.ig.test.model;

import jakarta.persistence.*;

@Entity
@Table(name = "LINEA_CREDITO")
public class LineaCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLineaCredito;

    @Column(nullable = false)
    private String nombreLineaCredito;

    @Column(nullable = false)
    private Double valorMinimo;

    @Column(nullable = false)
    private Double valorMaximo;

    @Column(nullable = false)
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
