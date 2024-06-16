package com.ig.test.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "SOLICITUD_PRESTAMO")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrestamo;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private Integer plazo; // en meses

    @Column(nullable = false)
    private LocalDate fechaDesembolso;

    @Column(nullable = false)
    private LocalDate fechaSolicitud;

    private String estadoSolicitud; //Aprobado o no

    @Column(nullable = false)
    private String descripcionCredito;


    @ManyToOne
    @JoinColumn(name = "linea_credito_id", nullable = false)
    private LineaCredito lineaCredito;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;


    public Long getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Long idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public LocalDate getFechaDesembolso() {
        return fechaDesembolso;
    }

    public void setFechaDesembolso(LocalDate fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public LineaCredito getLineaCredito() {
        return lineaCredito;
    }

    public void setLineaCredito(LineaCredito lineaCredito) {
        this.lineaCredito = lineaCredito;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDescripcionCredito() {
        return descripcionCredito;
    }

    public void setDescripcionCredito(String descripcionCredito) {
        this.descripcionCredito = descripcionCredito;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
}
