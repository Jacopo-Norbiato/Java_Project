package com.example.model;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    //email per effettuare il login
    private String email;

    public LoginRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}