package org.example.Exceptions;

public class ResponseMessage {
    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String toJson() {
        return "{\"message\":\"" + message + "\"}";
    }

    // Getter
    public String getMessage() {
        return message;
    }
}

