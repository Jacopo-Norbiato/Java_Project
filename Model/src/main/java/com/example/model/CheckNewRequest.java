package com.example.model;

import java.io.Serializable;

public class CheckNewRequest implements Serializable {
    private final String email;
    private final int lastEmailId;

    public CheckNewRequest(String email, int lastEmailId) {
        this.email = email;
        this.lastEmailId = lastEmailId;
    }

    public String getEmail() {
        return email;
    }

    public int getLastEmailId() {
        return lastEmailId;
    }
}
