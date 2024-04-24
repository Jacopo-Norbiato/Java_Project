package com.example.model;

import java.io.Serializable;

public class ReceiveReq implements Serializable {
    private String email;

    public ReceiveReq(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}