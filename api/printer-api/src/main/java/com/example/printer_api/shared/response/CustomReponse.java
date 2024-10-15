package com.example.printer_api.shared.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomReponse {
    private boolean success;
    private String message;
    private Object data;
}
