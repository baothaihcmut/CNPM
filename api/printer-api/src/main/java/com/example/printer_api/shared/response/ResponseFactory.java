package com.example.printer_api.shared.response;

import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {
    private CustomReponse reponse;

    public ResponseFactory() {
        this.reponse = new CustomReponse();
    }

    public CustomReponse initResponse(boolean success, String message, Object data ){
        this.reponse.setSuccess(success);
        this.reponse.setMessage(message);
        this.reponse.setData(data);
        return this.reponse;
    }
}
