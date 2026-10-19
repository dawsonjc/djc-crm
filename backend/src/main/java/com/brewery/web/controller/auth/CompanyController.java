package com.brewery.web.controller.auth;

import com.brewery.web.dto.company.CompanyDTO;
import com.brewery.web.dto.formdata.auth.CompanyFormData;
import com.brewery.web.model.User;
import com.brewery.web.model.company.Company;
import com.brewery.web.services.auth.CompanyService;
import com.brewery.web.user.AuthSessionUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

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
            @RequestBody CompanyFormData formData,
            HttpServletRequest request
    ) {
        ObjectNode respJson = mapper.createObjectNode();
        respJson.put("success", false);
        respJson.put("message", "");

        ObjectNode data = respJson.putObject("data");

        HttpSession session = request.getSession(false);
        Object sessionUser = session == null ? null : session.getAttribute(AuthSessionUser.AUTH_SESSION_USER);
        if (!(sessionUser instanceof User user)) {
            respJson.put("message", "Please sign in again.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respJson);
        }


        if(!formData.verify()) {
            respJson.put("message", "Invalid form data");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respJson);
        }


        Company saved = this.companyService.addCompanyByFormData(formData, user);
        respJson.put("success", true);
        respJson.set("data", mapper.valueToTree(CompanyDTO.from(saved)));

        return ResponseEntity.status(HttpStatus.OK).body(respJson);
    }

    @GetMapping(path = { "/{id}" })
    public ResponseEntity<ObjectNode> getById(
            @PathVariable(name = "id") String id,
            HttpServletRequest request
    ) {
        ObjectNode respJson = mapper.createObjectNode();
        respJson.put("success", false);
        respJson.put("message", "");

        ObjectNode data = respJson.putObject("data");

        HttpSession session = request.getSession(false);
        Object sessionUser = session == null ? null : session.getAttribute(AuthSessionUser.AUTH_SESSION_USER);
        if (!(sessionUser instanceof User user)) {
            respJson.put("message", "Please sign in again.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respJson);
        }

        UUID companyId;
        try {
            companyId = UUID.fromString(id);
        } catch(IllegalArgumentException e) {
            respJson.put("message", "Invalid company id.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respJson);
        }

        Company company = this.companyService.getCompanyById(companyId);
        if(company == null) {
            respJson.put("message", "Company not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respJson);
        }
        respJson.put("success", true);

        respJson.set("data", mapper.valueToTree(company));

        return ResponseEntity.ok().body(respJson);
    }

}
