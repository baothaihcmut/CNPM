package com.example.printer_api.modules.authentication.dtos.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GoogleResponse {
    private String id;
    private String email;
    private boolean verifiedEmail;
    private String name;
    private String picture;
    private String givenName;
    private String familyName;
    private String locale;
}
