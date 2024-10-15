package com.example.printer_api.modules.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.printer_api.shared.response.CustomReponse;
import com.example.printer_api.shared.response.ResponseFactory;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private ResponseFactory responseFactory;

    @GetMapping("/")
    public ResponseEntity<CustomReponse> getHello() {
        return ResponseEntity.ok().body(this.responseFactory.initResponse(true, "hello", null));
    }
}
