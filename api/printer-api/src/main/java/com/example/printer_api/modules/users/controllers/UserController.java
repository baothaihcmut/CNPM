package com.example.printer_api.modules.users.controllers;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.printer_api.modules.users.dtos.response.UserReponse;
import com.example.printer_api.modules.users.dtos.resquest.CreateUserRequest;
import com.example.printer_api.modules.users.entities.Role;
import com.example.printer_api.modules.users.entities.User;
import com.example.printer_api.modules.users.services.UserService;
import com.example.printer_api.shared.response.CustomReponse;
import com.example.printer_api.shared.response.ResponseFactory;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ResponseFactory responseFactory;

    @PostMapping("/initInfo")
    public ResponseEntity<CustomReponse> initInfo(@RequestBody @Valid CreateUserRequest dto) {
        UserReponse res = this.userService.initUserInfo(dto);
        return ResponseEntity.status(201).body(this.responseFactory.initResponse(true, "Init info success", res));
    }

    @PostMapping("/addmail")
    public ResponseEntity<CustomReponse> addEmail() {
        try {
            User res = this.userService.createEmail("bao.thaikhmt@hcmut.edu.vn", Role.CUSTOMER);
            return ResponseEntity.status(201).body(this.responseFactory.initResponse(true, "Add email success", res));
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(this.responseFactory.initResponse(true, "Add email success", null));
        }
    }
}
