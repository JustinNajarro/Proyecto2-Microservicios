package com.nttdata.CustomerMs.business;

import com.nttdata.CustomerMs.model.ClienteRequest;
import com.nttdata.CustomerMs.model.ClienteResponse;
import com.nttdata.CustomerMs.model.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    public Cliente getClienteOfClienteRequest(ClienteRequest request){
        Cliente entity = new Cliente();
        entity.setNombre(request.getNombre());
        entity.setApellido(request.getApellido());
        entity.setDni(request.getDni());
        entity.setEmail(request.getEmail());

        return entity;
    }

    public ClienteResponse getClienteResponseOfCliente(Cliente entity){
        ClienteResponse response = new ClienteResponse();
        response.setId(entity.getId());
        response.setNombre(entity.getNombre());
        response.setApellido(entity.getApellido());
        response.setDni(entity.getDni());
        response.setEmail(entity.getEmail());

        return response;
    }
}
