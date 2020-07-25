package com.examen.shiller.httpResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
    private String message;
    private Boolean success;

    public MessageResponse(String message, Boolean success) {
        this.message = message;
        this.success=success;
    }
}
