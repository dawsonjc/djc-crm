package com.brewery.web.controller.auth;

import com.brewery.web.dto.UserDTO;
import com.brewery.web.dto.company.CompanyDTO;
import com.brewery.web.dto.formdata.auth.CompanyFormData;
import com.brewery.web.model.User;
import com.brewery.web.model.company.Company;
import com.brewery.web.services.auth.CompanyService;
import com.brewery.web.user.AuthSessionUser;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestAttribute(AuthSessionUser.AUTH_SESSION_USER) User user
    ) {
        ObjectNode respJson = mapper.createObjectNode();
        respJson.put("success", false);
        respJson.put("message", "");

        ObjectNode data = respJson.putObject("data");

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
            @PathVariable(name = "id") String id
    ) {
        ObjectNode respJson = mapper.createObjectNode();
        respJson.put("success", false);
        respJson.put("message", "");

        ObjectNode data = respJson.putObject("data");

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

    @GetMapping(path = { "/{id}/users" })
    public ResponseEntity<ObjectNode> getCompanyUsers(
            @PathVariable(name = "id") String id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        ObjectNode response = mapper.createObjectNode();
        response.put("success", false);

        UUID companyId;
        try {
            companyId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            response.put("message", "Invalid company ID.");
            return ResponseEntity.badRequest().body(response);
        }
        if (page < 0 || size < 1 || size > 500) {
            response.put("message", "Page must be nonnegative and size must be between 1 and 500.");
            return ResponseEntity.badRequest().body(response);
        }
        if (companyService.getCompanyById(companyId) == null) {
            response.put("message", "Company not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Slice<UserDTO> users = companyService.getCompanyUserPage(companyId, page, size);
        ObjectNode data = response.putObject("data");
        data.set("content", mapper.valueToTree(users.getContent()));
        data.put("number", users.getNumber());
        data.put("size", users.getSize());
        data.put("last", users.isLast());
        response.put("success", true);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}/users/{userId}")
    public ResponseEntity<ObjectNode> deleteCompanyUser(
            @PathVariable("id") String id, @PathVariable("userId") String userId) {
        ObjectNode response = mapper.createObjectNode();
        response.put("success", false);
        try {
            companyService.deleteCompanyUser(UUID.fromString(id), UUID.fromString(userId));
        } catch (IllegalArgumentException e) {
            response.put("message", "Invalid company/user ID or user does not belong to this company.");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("success", true);
        response.put("message", "User deleted.");
        return ResponseEntity.ok(response);
    }
}
