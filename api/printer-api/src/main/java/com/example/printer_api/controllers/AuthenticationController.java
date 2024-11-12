package com.example.printer_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.printer_api.modules.authentication.dtos.response.AuthenticationResponse;
import com.example.printer_api.services.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/outbound/authentication")
    public ResponseEntity<AuthenticationResponse> outboundAuthentication(
        @RequestParam("googleCode") String googleCode
    ){
        AuthenticationResponse result = authenticationService.outboundAuthentication(googleCode);
        return ResponseEntity.ok(result);
    }
    
}
