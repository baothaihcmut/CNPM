package com.example.printer_api.modules.users.dtos.request;



import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateUserDTO {
    @NotEmpty(message = "username not empty")
    private String username;

    @NotEmpty(message = "password not empty")
    private String password;
}
