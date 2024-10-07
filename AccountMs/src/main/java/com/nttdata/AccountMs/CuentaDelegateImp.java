package com.nttdata.AccountMs;

import com.nttdata.AccountMs.api.CuentasApiDelegate;
import com.nttdata.AccountMs.business.CuentaService;
import com.nttdata.AccountMs.model.CuentaRequest;
import com.nttdata.AccountMs.model.CuentaResponse;
import com.nttdata.AccountMs.model.InlineObject;
import com.nttdata.AccountMs.model.SaldoTipoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaDelegateImp implements CuentasApiDelegate {

    @Autowired
    private CuentaService cuentaService;

    @Override
    public ResponseEntity<List<CuentaResponse>> listAllAccounts() {
        List<CuentaResponse> cuentas = cuentaService.listAllAccounts();
        return ResponseEntity.ok(cuentas);
    }

    @Override
    public ResponseEntity<CuentaResponse> createAccount(CuentaRequest cuentaRequest) {
        CuentaResponse cuentaResponse = cuentaService.createAccount(cuentaRequest);
        return new ResponseEntity<>(cuentaResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CuentaResponse> getAccountById(Integer id) {
        CuentaResponse cuentaResponse = cuentaService.getAccountById(id);
        return ResponseEntity.ok(cuentaResponse);
    }

    @Override
    public ResponseEntity<Void> deleteAccountById(Integer id) {
        cuentaService.deleteAccountById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateAccountBalance(String numeroCuenta, InlineObject inlineObject) {
        cuentaService.updateAccountBalance(numeroCuenta, inlineObject);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<SaldoTipoResponse> getAccountBalanceAndType(String numeroCuenta) {
        SaldoTipoResponse saldoTipoResponse = cuentaService.getAccountBalanceAndType(numeroCuenta);
        return ResponseEntity.ok(saldoTipoResponse);
    }
}