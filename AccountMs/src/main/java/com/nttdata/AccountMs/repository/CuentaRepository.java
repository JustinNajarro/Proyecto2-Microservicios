package com.nttdata.AccountMs.repository;

import com.nttdata.AccountMs.model.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {
    boolean existsByNumeroCuenta(String numeroCuenta);
    List<Cuenta> findByClienteId(Integer clienteId);
}
