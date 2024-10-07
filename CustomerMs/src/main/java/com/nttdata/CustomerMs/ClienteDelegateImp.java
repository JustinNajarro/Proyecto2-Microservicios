package com.nttdata.CustomerMs;

import com.nttdata.CustomerMs.api.ClientesApiDelegate;
import com.nttdata.CustomerMs.business.ClienteService;
import com.nttdata.CustomerMs.model.ClienteRequest;
import com.nttdata.CustomerMs.model.ClienteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteDelegateImp implements ClientesApiDelegate {

    @Autowired
    ClienteService clienteService;

    @Override
    public ResponseEntity<List<ClienteResponse>> listAllCustomers() {
        List<ClienteResponse> customers = clienteService.listAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @Override
    public ResponseEntity<ClienteResponse> createCustomer(ClienteRequest clienteRequest) {
        return ResponseEntity.ok(clienteService.createCustomer(clienteRequest));
    }

    @Override
    public ResponseEntity<ClienteResponse> getCustomerById(Integer id) {
        return ResponseEntity.ok(clienteService.getCustomerById(id));
    }

    @Override
    public ResponseEntity<Void> deleteCustomerById(Integer id) {
        clienteService.deleteCustomerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> updateCustomerById(Integer id, ClienteRequest clienteRequest) {
        clienteService.updateCustomerById(id, clienteRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Boolean> checkIfCustomerExists(Integer clienteId) {
        return ResponseEntity.ok(clienteService.checkIfCustomerExists(clienteId));
    }
}
