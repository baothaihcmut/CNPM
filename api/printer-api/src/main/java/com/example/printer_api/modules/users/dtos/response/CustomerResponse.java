package com.example.printer_api.modules.users.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private Integer numOfPapersA3;

    private Integer numOfPapersA4;

    private Integer numOfPapersA5;
}
