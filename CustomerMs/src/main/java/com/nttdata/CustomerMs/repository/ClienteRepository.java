package com.nttdata.CustomerMs.repository;

import com.nttdata.CustomerMs.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    boolean existsByDni(String dni);
}
