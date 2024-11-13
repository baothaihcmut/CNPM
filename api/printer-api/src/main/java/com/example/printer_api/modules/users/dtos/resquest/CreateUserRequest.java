package com.example.printer_api.modules.users.dtos.resquest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotEmpty(message = "Phone number is required")
    private String phoneNumber;

    @Valid
    private CreateCustomerRequest customerRequest;

    @Valid
    private CreateEmployeeRequest employeeRequest;
}
