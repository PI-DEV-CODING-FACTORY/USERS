package com.example.users.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomResponse<T> {
    private String status;
    private String message;
    private T data;

    public CustomResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public CustomResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}