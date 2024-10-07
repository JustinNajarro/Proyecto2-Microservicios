package com.nttdata.AccountMs.business;


import com.nttdata.AccountMs.model.CuentaRequest;
import com.nttdata.AccountMs.model.CuentaResponse;
import com.nttdata.AccountMs.model.InlineObject;
import com.nttdata.AccountMs.model.SaldoTipoResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CuentaService {
    List<CuentaResponse> listAllAccounts();
    CuentaResponse createAccount(CuentaRequest cuentaRequest);
    CuentaResponse getAccountById(Integer id);
    void deleteAccountById(Integer id);
    void updateAccountBalance(String numeroCuenta, InlineObject inlineObject);
    SaldoTipoResponse getAccountBalanceAndType(String numeroCuenta);
}
