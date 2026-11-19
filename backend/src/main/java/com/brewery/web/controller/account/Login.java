package com.brewery.web.controller.account;

import com.brewery.web.dto.formdata.LoginFormData;
import com.brewery.web.model.Role;
import com.brewery.web.model.User;
import com.brewery.web.model.UserRole;
import com.brewery.web.model.company.Company;
import com.brewery.web.services.auth.CompanyService;
import com.brewery.web.services.UserTableService;
import com.brewery.web.user.SessionUser;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = { "/account" })
public class Login {

    private final UserTableService userService;
    private final CompanyService companyService;

    public Login(UserTableService userService, CompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
    }

    @GetMapping(path = { "/login" })
    public String loginView(Model model, HttpServletRequest request) {
        model.addAttribute("new_user", User.builder().build());
        model.addAttribute("login_user", User.builder().build());
        return "account/login";
    }
    
    @ResponseBody
    @PostMapping(path = { "/login" }, consumes = { "application/json" }, produces = {  "application/json" })
    public ResponseEntity<ObjectNode> login(
            HttpServletRequest request,
            @RequestBody LoginFormData formUser
    ) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode respJson = mapper.createObjectNode();
        respJson.put("success", false);
        respJson.put("message", "");
        ObjectNode data = respJson.putObject("data");

        if(!formUser.verify()) {
            respJson.set("data", mapper.convertValue(formUser.getErrors(), ObjectNode.class));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respJson);
        }

        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

        boolean isEmail = emailPattern.matcher(formUser.username()).matches();

        UUID userId;
        if(isEmail) {
            if(!this.userService.userExistsByEmail(formUser.username())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respJson);
            }

            userId = this.userService.getUserIdByEmail(formUser.username());
        } else {
            if(!this.userService.userExistsByUsername(formUser.username())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respJson);
            }

            userId = this.userService.getUserIdByUsername(formUser.username());
        }

        User user = this.userService.getUserByIdAndPassword(userId, formUser.password());

        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respJson);
        }

        user = this.userService.recordLogin(user, Instant.now(), UUID.randomUUID());

        respJson.put("success", true);

        data.put("userId", userId.toString());
        data.put("username", user.getUsername());

        HttpSession session = request.getSession(true);
        
        session.setAttribute(SessionUser.SESSION_USER, user.toSessionUser());

        if(user.getCompanyId() != null) {
            Company userCompany = this.companyService.getCompanyById(user.getCompanyId());

            if(userCompany != null) {
                session.setAttribute(SessionUser.SESSION_COMPANY, userCompany);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(respJson);
    }
}
