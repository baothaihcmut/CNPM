package com.example.printer_api.modules.users.dtos.resquest;

import java.time.LocalDate;

import jakarta.validation.constraints.Past;

public class CreateEmployeeRequest {
    @Past(message = "Start work date is invalid")
    private LocalDate startWorkDate;

}
