package com.nttdata.AccountMs.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CustomerMs")
public interface CustomerFeignClient {

    @GetMapping("/clientes/{id}/exists")
    Boolean checkIfCustomerExists(@PathVariable("id") Integer id);
}
