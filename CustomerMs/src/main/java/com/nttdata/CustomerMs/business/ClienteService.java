package com.nttdata.CustomerMs.business;

import com.nttdata.CustomerMs.model.ClienteRequest;
import com.nttdata.CustomerMs.model.ClienteResponse;

import java.util.List;

public interface ClienteService {
    List<ClienteResponse> listAllCustomers();
    ClienteResponse createCustomer(ClienteRequest clienteRequest);
    ClienteResponse getCustomerById(Integer id);
    void updateCustomerById(Integer id, ClienteRequest clienteRequest);
    void deleteCustomerById(Integer id);
    Boolean checkIfCustomerExists(Integer id);
}
