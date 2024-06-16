package com.ig.test.util;

import com.ig.test.dto.*;
import com.ig.test.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ConvertirDatos {

     public Cliente convertToEntityCliente(ClienteDto clienteDto) {
        Cliente cliente = new Cliente();
        cliente.setId(clienteDto.getId());
        cliente.setTipoDocumento(clienteDto.getTipoDocumento());
        cliente.setNumeroDocumento(clienteDto.getNumeroDocumento());
        cliente.setApellidos(clienteDto.getApellidos());
        cliente.setResidencia(clienteDto.getResidencia());
        cliente.setCiudad(clienteDto.getCiudad());
        cliente.setTelefono(clienteDto.getTelefono());
        cliente.setEmail(clienteDto.getEmail());
        return cliente;
    }

    public ClienteDto convertToDtoCliente(Cliente cliente) {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setId(cliente.getId());
        clienteDto.setTipoDocumento(cliente.getTipoDocumento());
        clienteDto.setNumeroDocumento(cliente.getNumeroDocumento());
        clienteDto.setApellidos(cliente.getApellidos());
        clienteDto.setResidencia(cliente.getResidencia());
        clienteDto.setCiudad(cliente.getCiudad());
        clienteDto.setTelefono(cliente.getTelefono());
        clienteDto.setEmail(cliente.getEmail());
        return clienteDto;
    }

    public InfoLaboralCliente convertToEntityInfoLaboral(InfoLaboralClienteDto infoLaboralClienteDto) {
        InfoLaboralCliente infoLaboralCliente = new InfoLaboralCliente();
        infoLaboralCliente.setId(infoLaboralClienteDto.getIdInfoLab());
        infoLaboralCliente.setNitEmpresa(infoLaboralClienteDto.getNitEmpresa());
        infoLaboralCliente.setNombreEmpresa(infoLaboralClienteDto.getNombreEmpresa());
        infoLaboralCliente.setDireccion(infoLaboralClienteDto.getDireccion());
        infoLaboralCliente.setTelefono(infoLaboralClienteDto.getTelefono());
        infoLaboralCliente.setCargo(infoLaboralClienteDto.getCargo());
        infoLaboralCliente.setFechaVinculacion(infoLaboralClienteDto.getFechaVinculacion());
        //infoLaboralCliente.setCliente(infoLaboralCliente.getCliente());
        // No se establece el cliente en este método, ya que es una referencia a otro objeto
        return infoLaboralCliente;
    }

    public InfoLaboralClienteDto convertToDtoInfoLaboral(InfoLaboralCliente infoLaboralCliente) {
        InfoLaboralClienteDto infoLaboralClienteDto = new InfoLaboralClienteDto();
        infoLaboralClienteDto.setIdInfoLab(infoLaboralCliente.getId());
        infoLaboralClienteDto.setNitEmpresa(infoLaboralCliente.getNitEmpresa());
        infoLaboralClienteDto.setNombreEmpresa(infoLaboralCliente.getNombreEmpresa());
        infoLaboralClienteDto.setDireccion(infoLaboralCliente.getDireccion());
        infoLaboralClienteDto.setTelefono(infoLaboralCliente.getTelefono());
        infoLaboralClienteDto.setCargo(infoLaboralCliente.getCargo());
        infoLaboralClienteDto.setFechaVinculacion(infoLaboralCliente.getFechaVinculacion());
        if (infoLaboralCliente.getCliente() != null) {
            infoLaboralClienteDto.setIdcliente(String.valueOf(infoLaboralCliente.getCliente().getId()));
        }
        return infoLaboralClienteDto;
    }

    public ReferenciasClientes convertToEntityReferenciasClientes(ReferenciasClientesDto dto) {
        ReferenciasClientes referencia = new ReferenciasClientes();
        referencia.setNombresApellidos(dto.getNombresApellidos());
        referencia.setResidencia(dto.getResidencia());
        referencia.setTelefono(dto.getTelefono());
        referencia.setCiudad(dto.getCiudad());
        referencia.setNumeroDocumento(dto.getNumeroDocumento());
        referencia.setTipoDocumento(dto.getTipoDocumento());
        referencia.setParentezco(dto.getParentezco());
        return referencia;
    }

    public ReferenciasClientesDto convertToDtoReferenciaCliente(ReferenciasClientes referencia) {
        ReferenciasClientesDto dto = new ReferenciasClientesDto();
        dto.setTipoDocumento(referencia.getTipoDocumento());
        dto.setNumeroDocumento(referencia.getNumeroDocumento());
        dto.setNombresApellidos(referencia.getNombresApellidos());
        dto.setResidencia(referencia.getResidencia());
        dto.setCiudad(referencia.getCiudad());
        dto.setTelefono(referencia.getTelefono());
        dto.setParentezco(referencia.getParentezco());
        dto.setIdcliente(referencia.getCliente().getId());
        dto.setIdRef(referencia.getId());
        return dto;
    }

    public Prestamo convertirDtoAEntity(PrestamoDto dto) {
        Prestamo prestamo = new Prestamo();
        prestamo.setMonto(dto.getMonto());
        prestamo.setPlazo(dto.getPlazo());
        prestamo.setFechaDesembolso(dto.getFechaDesembolso());
        prestamo.setEstadoSolicitud(dto.getEstadoSolicitud());
        prestamo.setDescripcionCredito(dto.getDescripcionCredito());
        prestamo.setFechaSolicitud(LocalDate.now());

        // Si 'idPrestamo' es generado automáticamente en la base de datos, no necesitas asignarlo aquí
        return prestamo;
    }
    public PrestamoDto convertirEntityADtoPrestao(Prestamo prestamo) {
        PrestamoDto dto = new PrestamoDto();
        dto.setIdPrestamo(prestamo.getIdPrestamo());
        dto.setMonto(prestamo.getMonto());
        dto.setPlazo(prestamo.getPlazo());
        dto.setFechaDesembolso(prestamo.getFechaDesembolso());
        dto.setEstadoSolicitud(prestamo.getEstadoSolicitud());
        dto.setDescripcionCredito(prestamo.getDescripcionCredito());
        dto.setIdcliente(prestamo.getCliente().getNumeroDocumento());
        dto.setIdlineaCredito(prestamo.getLineaCredito().getNombreLineaCredito());
        dto.setFechaSolicitud(prestamo.getFechaSolicitud());
        // Asigna otros campos si es necesario

        return dto;
    }

    public LineaCreditoDto convertirALineaCreditoDto(LineaCredito lineaCredito) {
        LineaCreditoDto lineaCreditoDto = new LineaCreditoDto();
        // Aquí establece las propiedades del DTO usando las propiedades de la entidad
        lineaCreditoDto.setIdLineaCredito(lineaCredito.getIdLineaCredito());
        lineaCreditoDto.setValorMaximo(lineaCredito.getValorMaximo());
        lineaCreditoDto.setValorMinimo(lineaCredito.getValorMinimo());
        lineaCreditoDto.setNombreLineaCredito(lineaCredito.getNombreLineaCredito());
        lineaCreditoDto.setPlazoMaximo(lineaCreditoDto.getPlazoMaximo());

        return lineaCreditoDto;
    }



}
