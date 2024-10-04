package com.nttdata.AccountMs.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String numeroCuenta;

    private Double saldo;

    private TipoCuentaEnum tipoCuenta;

    private Integer clienteId;
}
