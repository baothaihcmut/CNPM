package com.example.printer_api.modules.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.printer_api.modules.users.dtos.request.CreateUserDTO;
import com.example.printer_api.shared.exception.StatusCode;
import com.example.printer_api.shared.response.CustomReponse;
import com.example.printer_api.shared.response.ResponseFactory;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private ResponseFactory responseFactory;

    @PostMapping("")
    public ResponseEntity<CustomReponse> createUser(@RequestBody @Valid CreateUserDTO userDTO) {
        System.err.println("create User....");
        return ResponseEntity.status(StatusCode.CREATED.getCode()).body(this.responseFactory.initResponse(true, "create user success", userDTO));
    }
}
