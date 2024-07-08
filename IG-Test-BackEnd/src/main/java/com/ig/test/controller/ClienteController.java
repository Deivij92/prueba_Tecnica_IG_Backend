package com.ig.test.controller;

import com.ig.test.dto.*;
import com.ig.test.service.impl.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("api/dinerofacil")
public class ClienteController {
    @Autowired
    private ClienteServiceImpl serviceCliente;

    // Endpoint para crear un cliente
    @PostMapping("/cliente-crud") //Crud del cliente
    public ResponseEntity<ResponseDto> crudCliente(@RequestBody ClienteDto clienteDto) {
        ResponseDto responseDto = new ResponseDto();
        switch (clienteDto.getActionType().toUpperCase()){
            case "CREATE":
                responseDto = serviceCliente.saveCliente(clienteDto);
            break;
            case "UPDATE":
                responseDto = serviceCliente.updateCliente(clienteDto.getId(), clienteDto);
            break;
            case "INFO-ID":
                responseDto = serviceCliente.getCliente(clienteDto.getId());
            break;
            case "DELETE":
                responseDto =  serviceCliente.DeleteInfoCliente(clienteDto.getId());
            break;
            case "LIST_ALL":
                responseDto = serviceCliente.getAllClientes();
                break;
            default:
                responseDto.setCodeResponse(300L);
                responseDto.setMessageResponse("Error validando campos");
                return new ResponseEntity<>(responseDto, HttpStatus.OK);

        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @PostMapping("/cliente-lab") //Crud del cliente
    public ResponseEntity<ResponseDto> crudInfoLab(@RequestBody InfoLaboralClienteDto lab) {
        ResponseDto responseDto = new ResponseDto();
        switch (lab.getActionType().toUpperCase()){
            case "CREATE":
                responseDto = serviceCliente.saveInfoLaboral(lab);
            break;
            case "UPDATE":
                responseDto = serviceCliente.modificarInfoLaboral(lab);
            break;
            case "INFO-ID":
                responseDto = serviceCliente.obtenerInfoLAboral(Long.parseLong(lab.getIdcliente()));
            break;
            case "DELETE":
                responseDto =  serviceCliente.deleteInfoLab(lab.getIdInfoLab());
            break;
            case "LIST_ALL":
                //responseDto = serviceCliente.getAllClientes();
                break;
            default:
                responseDto.setCodeResponse(300L);
                responseDto.setMessageResponse("Error validando campos");
                return new ResponseEntity<>(responseDto, HttpStatus.OK);

        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @PostMapping("/cliente-ref-crud") //Crud del cliente
    public ResponseEntity<ResponseDto> crudReferencia(@RequestBody List<ReferenciasClientesDto> ref, @PathVariable String action, @PathVariable long idRef) {
        ResponseDto responseDto = new ResponseDto();
        switch (ref.get(0).getActionType()){
            case "CREATE":
                responseDto = serviceCliente.saveReferencias((List<ReferenciasClientesDto>) ref);
            break;
            case "UPDATE":
                responseDto = serviceCliente.saveReferencias((List<ReferenciasClientesDto>) ref);
            break;
            case "INFO-ID":
                responseDto = serviceCliente.obtnerInfoReferencias(ref.get(0).getIdRef());
            break;
            case "DELETE":
                responseDto =  serviceCliente.deleteInfoReferencia(ref.get(0).getIdRef());
            break;
            case "LIST_ALL":
                responseDto = serviceCliente.obtnerListaReferencias(ref.get(0).getIdcliente());
                break;
            default:
                responseDto.setCodeResponse(300L);
                responseDto.setMessageResponse("Error validando campos");
                return new ResponseEntity<>(responseDto, HttpStatus.OK);

        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }



}
