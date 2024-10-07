package com.nttdata.AccountMs.business;


import com.nttdata.AccountMs.model.CuentaRequest;
import com.nttdata.AccountMs.model.CuentaResponse;
import com.nttdata.AccountMs.model.SaldoTipoResponse;
import com.nttdata.AccountMs.model.entity.Cuenta;
import com.nttdata.AccountMs.model.entity.TipoCuentaEnum;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper {

    public Cuenta getCuentaOfCuentaRequest(CuentaRequest request) {
        Cuenta entity = new Cuenta();
        entity.setNumeroCuenta(request.getNumeroCuenta());
        entity.setTipoCuenta(mapToEntityTipoCuenta(request.getTipoCuenta()));
        return entity;
    }

    public TipoCuentaEnum mapToEntityTipoCuenta(CuentaRequest.TipoCuentaEnum tipoCuentaRequest) {
        return TipoCuentaEnum.valueOf(tipoCuentaRequest.name());
    }

    public CuentaResponse getCuentaResponseOfCuenta(Cuenta entity) {
        CuentaResponse cuentaResponse = new CuentaResponse();
        cuentaResponse.setId(entity.getId());
        cuentaResponse.setNumeroCuenta(entity.getNumeroCuenta());
        cuentaResponse.setSaldo(entity.getSaldo());
        cuentaResponse.setTipoCuenta(mapToResponseTipoCuenta(entity.getTipoCuenta()));
        cuentaResponse.setClienteId(entity.getClienteId());
        return cuentaResponse;
    }


    public CuentaResponse.TipoCuentaEnum mapToResponseTipoCuenta(TipoCuentaEnum tipoCuentaEntity) {
        return CuentaResponse.TipoCuentaEnum.valueOf(tipoCuentaEntity.name());
    }

    public SaldoTipoResponse getSaldoOfCuenta(Cuenta entity){
        SaldoTipoResponse saldoTipoResponse = new SaldoTipoResponse();
        saldoTipoResponse.setSaldo(entity.getSaldo());
        saldoTipoResponse.tipoCuenta(mapToResponseSaldo(entity.getTipoCuenta()));

        return saldoTipoResponse;
    }

    public SaldoTipoResponse.TipoCuentaEnum mapToResponseSaldo(TipoCuentaEnum tipoCuentaEntity) {
        return SaldoTipoResponse.TipoCuentaEnum.valueOf(tipoCuentaEntity.name());
    }
}
