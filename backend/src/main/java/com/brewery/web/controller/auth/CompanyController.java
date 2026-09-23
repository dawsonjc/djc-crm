package com.brewery.web.controller.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth/company")
public class CompanyController {

    @GetMapping(path = { "" })
    public String getCompany() {
        return "";
    }

}
