package com.nttdata.CustomerMs.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "Clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "El nombre es obligatorio.")
    private String nombre;

    @NotEmpty(message = "El apellido es obligatorio.")
    private String apellido;

    @NotEmpty(message = "El DNI es obligatorio.")
    @Size(min = 8, max = 8, message = "El DNI debe tener exactamente 8 caracteres.")
    private String dni;

    @Email(message = "Formato de correo electrónico inválido.")
    @NotEmpty(message = "El correo electrónico es obligatorio.")
    private String email;

}
