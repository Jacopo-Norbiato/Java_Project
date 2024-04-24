package com.example.model;

import java.io.Serializable;

public class DeleteRequest implements Serializable {

    private String email;
    private int idEmail; //numero dell'email da eliminare -> ricerca all'interno della PostBox

    public DeleteRequest(int idEmail, String email) {
        this.idEmail = idEmail;
        this.email = email;
    }

    public int getIdEmail() {
        return idEmail;
    }

    public String getEmail() {
        return email;
    }
}