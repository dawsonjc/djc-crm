package com.brewery.web.controller.company;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth/company")
public class CompanyController {
    @Value(value = "${AUTH_SERVICE_SECRET_KEY}")
    private String authServiceSecretKey;

    @GetMapping(path = { "" })
    public String getCompany() {
        return this.authServiceSecretKey;
    }

}
