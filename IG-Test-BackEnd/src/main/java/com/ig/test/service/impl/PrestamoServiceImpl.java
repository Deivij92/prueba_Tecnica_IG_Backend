package com.ig.test.service.impl;

import com.ig.test.dto.LineaCreditoDto;
import com.ig.test.dto.PrestamoDto;
import com.ig.test.dto.ResponseDto;
import com.ig.test.model.Cliente;
import com.ig.test.model.LineaCredito;
import com.ig.test.model.Prestamo;
import com.ig.test.model.repository.RepositoryCliente;
import com.ig.test.model.repository.RepositoryLineaCredito;
import com.ig.test.model.repository.RepositoryPrestamo;
import com.ig.test.service.PrestamoService;
import com.ig.test.util.ConvertirDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private RepositoryPrestamo repositoryPrestamo;

    @Autowired
    private RepositoryLineaCredito repositoryLineaCredito;

    @Autowired
    private RepositoryCliente clienteRepository;
    @Autowired
    private ConvertirDatos convertirDatos;

    @Override
    public ResponseDto solictarPrestamo(PrestamoDto prestamoDto) {
        try {
            // Validar si la línea de préstamo existe
            Optional<LineaCredito> lineaDePrestamoExistente = repositoryLineaCredito.findById(Long.valueOf(prestamoDto.getIdlineaCredito()));
            if (lineaDePrestamoExistente.isPresent()) {
                LineaCredito lineaCredito = lineaDePrestamoExistente.get();
                if (prestamoDto.getMonto() > lineaCredito.getValorMaximo() ||
                        prestamoDto.getMonto() < lineaCredito.getValorMinimo() ||
                        prestamoDto.getPlazo() > lineaCredito.getPlazoMaximo() ||
                        prestamoDto.getPlazo() > lineaCredito.getPlazoMaximo()) {
                    return new ResponseDto(400, "El monto del préstamo o el plazo no cumple con los criterios de la línea de crédito: " +
                            "Valor minimo: " + lineaCredito.getValorMinimo()+" "+
                            "Valor maximo: " + lineaCredito.getValorMaximo()+" "+
                            "Plazo maximo: " + lineaCredito.getPlazoMaximo()+ " Revisa la información ingresada.");
                }
            } else {
                return new ResponseDto(404, "Línea de préstamo con ID " + prestamoDto.getIdlineaCredito() + " no encontrada");
            }


            // Validar si el cliente existe
            Optional<Cliente> clienteExistente = Optional.ofNullable(clienteRepository.findById(Long.parseLong(prestamoDto.getIdcliente())));
            if (!clienteExistente.isPresent()) {
                return new ResponseDto(404, "Cliente con ID " + prestamoDto.getIdcliente() + " no encontrado");
            }
            Prestamo prestamo = convertirDatos.convertirDtoAEntity(prestamoDto);
            // Aquí iría la lógica para procesar la solicitud de préstamo, utilizando la línea de préstamo y el cliente existente
            prestamo.setCliente(clienteExistente.get());
            prestamo.setLineaCredito(lineaDePrestamoExistente.get());
            repositoryPrestamo.save(prestamo);
            // Devolver una respuesta exitosa si la solicitud se procesa correctamente
            return new ResponseDto(200, "Solicitud de préstamo procesada correctamente");
        } catch (Exception e) {
            // Devolver una respuesta de error si ocurre alguna excepción durante el procesamiento
            return new ResponseDto(500, "Error al procesar la solicitud de préstamo: " + e.getMessage());
        }
    }

    @Override
    public List<LineaCreditoDto> listarPrestamos() {
        // Obtener las líneas de crédito del repositorio
        List<LineaCredito> lineasDeCredito = repositoryLineaCredito.findAll();

        // Convertir las líneas de crédito a DTO
        List<LineaCreditoDto> lineasDeCreditoDto = new ArrayList<>();
        for (LineaCredito lineaCredito : lineasDeCredito) {
            LineaCreditoDto lineaCreditoDto = convertirDatos.convertirALineaCreditoDto(lineaCredito);
            lineasDeCreditoDto.add(lineaCreditoDto);
        }

        return lineasDeCreditoDto;
    }


}
