module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.example.model;


    opens com.example.client to javafx.fxml;
    opens com.example.client.controller to javafx.fxml;
    exports com.example.client;
    exports com.example.client.controller;
}