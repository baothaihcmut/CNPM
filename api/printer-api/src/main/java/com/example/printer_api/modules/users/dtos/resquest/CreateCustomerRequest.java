package com.example.printer_api.modules.users.dtos.resquest;

import jakarta.validation.constraints.NotEmpty;

public class CreateCustomerRequest {
    @NotEmpty(message = "Student Id is required")
    private String studentId;
}
