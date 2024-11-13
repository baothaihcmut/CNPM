package com.example.printer_api.modules.users.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.printer_api.modules.users.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReponse {
    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private CustomerResponse customer;

    private EmployeeResponse employee;
}
