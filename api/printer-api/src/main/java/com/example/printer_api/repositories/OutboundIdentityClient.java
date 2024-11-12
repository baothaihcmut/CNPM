package com.example.printer_api.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.printer_api.modules.authentication.dtos.response.ExchangeTokenResponse;
import com.example.printer_api.modules.authentication.dtos.request.ExchangeTokenRequest;

import feign.QueryMap;

import org.springframework.http.MediaType;

@FeignClient(name = "outbound-identity", url = "https://oauth2.googleapis.com")
public interface OutboundIdentityClient {
    @PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ExchangeTokenResponse exchangeToken(@QueryMap ExchangeTokenRequest request);
}
