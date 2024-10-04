package com.nttdata.AccountMs.business.Impl;

import com.nttdata.AccountMs.business.CuentaMapper;
import com.nttdata.AccountMs.business.CuentaService;

import com.nttdata.AccountMs.exception.CustomExceptions;
import com.nttdata.AccountMs.model.CuentaRequest;
import com.nttdata.AccountMs.model.CuentaResponse;
import com.nttdata.AccountMs.model.entity.Cuenta;
import com.nttdata.AccountMs.model.entity.TipoCuentaEnum;
import com.nttdata.AccountMs.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CuentaServiceImp implements CuentaService {

    @Autowired
    CuentaRepository cuentaRepository;

    @Autowired
    CuentaMapper cuentaMapper;

    @Override
    public List<CuentaResponse> listAllAccounts() {
        return Optional.of(cuentaRepository.findAll().stream()
                        .map(cuentaMapper::getCuentaResponseOfCuenta)
                        .collect(Collectors.toList()))
                .filter(cuentas -> !cuentas.isEmpty())
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("No se encontraron cuentas"));
    }

    @Override
    public CuentaResponse createAccount(CuentaRequest cuentaRequest) {

        if (cuentaRequest.getSaldo() <= 0) {
            throw new CustomExceptions.BadRequestException("El saldo inicial debe ser mayor a 0.");
        }

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(generarNumeroCuentaUnico());
        cuenta.setSaldo(cuentaRequest.getSaldo());
        cuenta.setTipoCuenta(cuentaMapper.mapToEntityTipoCuenta(cuentaRequest.getTipoCuenta()));
        cuenta.setClienteId(cuentaRequest.getClienteId());

        Cuenta cuentaSave = cuentaRepository.save(cuenta);

        return cuentaMapper.getCuentaResponseOfCuenta(cuentaSave);
    }

    @Override
    public CuentaResponse getAccountById(Integer id) {
        return cuentaRepository.findById(id)
                .map(cuentaMapper::getCuentaResponseOfCuenta)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Cuenta no existente con ID: " + id));
    }

    @Override
    public void deleteAccountById(Integer id) {
        cuentaRepository.findById(id)
                .ifPresentOrElse(cuentaRepository::delete,
                        () -> { throw new CustomExceptions.ResourceNotFoundException("Cuenta no existente con ID: " + id);
                });
    }

    @Override
    public void depositToAccount(Integer cuentaId, CuentaRequest cuentaRequest) {
        Optional.of(cuentaRequest)
                .filter(req -> req.getSaldo() > 0)
                .orElseThrow(() -> new CustomExceptions.BadRequestException("El monto a depositar debe ser mayor a 0."));

        cuentaRepository.findById(cuentaId)
                .map(cuenta -> {
                    cuenta.setSaldo(cuenta.getSaldo() + cuentaRequest.getSaldo());
                    return cuentaRepository.save(cuenta);
                })
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Cuenta no encontrada con ID: " + cuentaId));
    }

    @Override
    public void withdrawFromAccount(Integer cuentaId, CuentaRequest cuentaRequest) {
        Optional.of(cuentaRequest)
                .filter(req -> req.getSaldo() > 0)
                .orElseThrow(() -> new CustomExceptions.BadRequestException("El monto a retirar debe ser mayor a 0."));

        cuentaRepository.findById(cuentaId)
                .map(cuenta -> {
                    if (cuenta.getTipoCuenta() == TipoCuentaEnum.AHORROS) {
                        if (cuenta.getSaldo() - cuentaRequest.getSaldo() < 0) {
                            throw new CustomExceptions.BadRequestException("Fondos insuficientes. Las cuentas de ahorro no pueden tener saldo negativo.");
                        }
                    } else if (cuenta.getTipoCuenta() == TipoCuentaEnum.CORRIENTE) {
                        if (cuenta.getSaldo() - cuentaRequest.getSaldo() < -500) {
                            throw new CustomExceptions.BadRequestException("Fondos insuficientes. Las cuentas corrientes pueden tener un sobregiro máximo de -500.");
                        }
                    }

                    cuenta.setSaldo(cuenta.getSaldo() - cuentaRequest.getSaldo());
                    return cuentaRepository.save(cuenta);
                })
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Cuenta no encontrada con ID: " + cuentaId));
    }


    private String generarNumeroCuentaUnico() {
        String numeroCuenta;
        boolean existe;

        do {
            numeroCuenta = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            existe = cuentaRepository.existsByNumeroCuenta(numeroCuenta);
        } while (existe);

        return numeroCuenta;
    }

}
