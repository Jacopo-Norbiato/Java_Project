module com.example.model {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.model to com.example.client, com.example.server, javafx.fxml;
    opens com.example.model to javafx.fxml;
}