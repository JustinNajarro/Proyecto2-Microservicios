package com.nttdata.CustomerMs.business.Imp;

import com.nttdata.CustomerMs.business.ClienteMapper;
import com.nttdata.CustomerMs.business.ClienteService;
import com.nttdata.CustomerMs.exception.CustomExceptions;
import com.nttdata.CustomerMs.model.ClienteRequest;
import com.nttdata.CustomerMs.model.ClienteResponse;
import com.nttdata.CustomerMs.model.entity.Cliente;
import com.nttdata.CustomerMs.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImp implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ClienteMapper clienteMapper;

    @Override
    public List<ClienteResponse> listAllCustomers() {
        return Optional.of(clienteRepository.findAll().stream()
                        .map(clienteMapper::getClienteResponseOfCliente)
                        .collect(Collectors.toList()))
                .filter(clientes -> !clientes.isEmpty())
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("No se encontraron clientes"));
    }

    @Override
    public ClienteResponse createCustomer(ClienteRequest clienteRequest) {
        validarCamposObligatorios(clienteRequest);

        if (clienteRepository.existsByDni(clienteRequest.getDni())) {
            throw new CustomExceptions.DniAlreadyExistsException("El cliente con DNI " + clienteRequest.getDni() + " ya existe");
        }

        return clienteMapper.getClienteResponseOfCliente(
                clienteRepository.save(clienteMapper.getClienteOfClienteRequest(clienteRequest))
        );
    }


    @Override
    public ClienteResponse getCustomerById(Integer id) {
        return clienteRepository.findById(id)
                .map(clienteMapper::getClienteResponseOfCliente)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Cliente no encontrado con ID: " + id));
    }

    @Override
    public void deleteCustomerById(Integer id) {
        clienteRepository.findById(id)
                .ifPresentOrElse(clienteRepository::delete,
                        () -> { throw new CustomExceptions.ResourceNotFoundException("Cliente no encontrado con ID: " + id); });
    }

    @Override
    public Boolean checkIfCustomerExists(Integer clienteId) {
        return clienteRepository.existsById(clienteId);
    }

    @Override
    public void updateCustomerById(Integer id, ClienteRequest clienteRequest) {
        clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombre(clienteRequest.getNombre());
                    cliente.setApellido(clienteRequest.getApellido());
                    cliente.setDni(clienteRequest.getDni());
                    cliente.setEmail(clienteRequest.getEmail());
                    return cliente;
                })
                .map(clienteRepository::save)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Cliente no encontrado con ID: " + id));
    }

    private void validarCamposObligatorios(ClienteRequest clienteRequest) {
        if (clienteRequest.getNombre() == null || clienteRequest.getNombre().trim().isEmpty()) {
            throw new CustomExceptions.BadRequestException("El campo 'nombre' es obligatorio y no puede estar vacío.");
        }
        if (clienteRequest.getApellido() == null || clienteRequest.getApellido().trim().isEmpty()) {
            throw new CustomExceptions.BadRequestException("El campo 'apellido' es obligatorio y no puede estar vacío.");
        }
        if (clienteRequest.getDni() == null || clienteRequest.getDni().trim().isEmpty()) {
            throw new CustomExceptions.BadRequestException("El campo 'dni' es obligatorio y no puede estar vacío.");
        }
    }
}

