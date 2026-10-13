package com.brewery.web.controller.auth;

import com.brewery.web.dto.company.CompanyDTO;
import com.brewery.web.dto.formdata.auth.CompanyFormData;
import com.brewery.web.model.company.Company;
import com.brewery.web.services.auth.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/auth/company")
public class CompanyController {

    private final CompanyService companyService;
    private final ObjectMapper mapper;

    public CompanyController(CompanyService companyService, ObjectMapper mapper) {
        this.companyService = companyService;
        this.mapper = mapper;
    }

    @GetMapping(path = { "/all" })
    public ResponseEntity<ObjectNode> getCompanies() {
        ObjectNode respJson = mapper.createObjectNode();
        respJson.put("success", false);
        respJson.put("message", "");

        ArrayNode data = respJson.putArray("data");

        List<CompanyDTO> companies = this.companyService.getCompanies();

        companies.forEach(data::addPOJO);

        respJson.put("success", true);

        return ResponseEntity.status(HttpStatus.OK).body(respJson);
    }

    @PostMapping(path = { "/add" })
    public ResponseEntity<ObjectNode> addCompany(
            @RequestBody CompanyFormData formData
    ) {
        ObjectNode respJson = mapper.createObjectNode();
        respJson.put("success", false);
        respJson.put("message", "");

        ObjectNode data = respJson.putObject("data");

        if(!formData.verify()) {
            respJson.put("message", "Invalid form data");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respJson);
        }

        Company saved = this.companyService.addCompany(Company.fromCompanyFormData(formData));
        respJson.put("success", true);
        respJson.set("data", mapper.valueToTree(CompanyDTO.from(saved)));


        return ResponseEntity.status(HttpStatus.OK).body(respJson);
    }

}
