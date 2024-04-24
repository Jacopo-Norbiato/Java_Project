package com.example.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostBox implements Serializable {
    private String nome;
    private String address;
    private List<Email> listReceivedEmail = new ArrayList<>();
    public transient ListProperty<Email> receivedEmails;

    public String getName() {
        return nome;
    }

    public void setName(String name) {
        this.nome = name;
    }

    public String getEmail() {
        return address;
    }

    public void setEmail(String address) {
        this.address = address;
    }

    public List<Email> getListReceivedEmails() {
        return listReceivedEmail;
    }

    public void setListReceivedEmail(List<Email> listReceivedEmail) {
        this.listReceivedEmail = listReceivedEmail;
    }

    public final ListProperty<Email> receivedEmailsProperty() {
        return this.receivedEmails;
    }

    public final ObservableList<Email> getReceivedEmails() {
        return this.receivedEmails.get();
    }

    public void setReceivedEmails(ObservableList<Email> receivedEmails) {
        this.receivedEmails = new SimpleListProperty<>();
        this.receivedEmails.set(receivedEmails);
    }

    public PostBox() {
        this.receivedEmails = new SimpleListProperty<>(FXCollections.observableArrayList());
    }
}
