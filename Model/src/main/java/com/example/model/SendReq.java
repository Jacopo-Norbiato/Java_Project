package com.example.model;

import java.io.Serializable;

public class SendReq implements Serializable {
    private Email email;

    public SendReq(Email email) {
        this.email = email;
    }

    public Email getEmail() {
        return email;
    }
}