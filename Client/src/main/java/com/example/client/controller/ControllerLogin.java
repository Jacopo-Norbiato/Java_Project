package com.example.client.controller;

import com.example.client.Connection;

import com.example.client.Main;
import com.example.model.LoginRequest;
import com.example.model.PostBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ControllerLogin implements Initializable {
    @FXML
    private ImageView btn_close;
    @FXML
    private TextField txt_email;
    @FXML
    private Label lbl_err_email;
    @FXML
    private Button btn_login;
    private final String IP = "127.0.0.1";
    private final int port = 8080;
    private ControllerHome controllerHome;
    private ControllerListEmail controllerListEmail;
    Connection connection = null;

    /**
     * Initializes the controller class.
     *
     * @param url            the URL to the FXML document
     * @param resourceBundle the resources used by the FXML document
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    public void setControllerListEmail(ControllerListEmail controllerListEmail) {
        this.controllerListEmail = controllerListEmail;
    }

    /**
     * This method is used to close the login window.
     */
    @FXML
    protected void onCloseButtonClick() {
        ((Stage) btn_close.getScene().getWindow()).close();
    }

    /**
     * Handles the action of the "Login" button. Check to see if the email has been entered and if the email is
     * syntactically correct.
     * Furthermore, if the email entered is correct, the connection takes place and the home view is opened.
     */
    @FXML
    protected void onLoginButtonClick() {
        boolean regex = Pattern.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", txt_email.getText().trim());
        // Check if the email is syntactically correct
        if (!regex) {
            if (txt_email.getText().trim().isEmpty()) {
                lbl_err_email.setText("Please enter your email"); // Attende l'inserimento di una nuove email
            }
            else  {
                lbl_err_email.setText("Enter a valid email address");
            }
        } else {
            try {
                // Initialize the connection with server
                if (connection == null) {
                    try {
                        connection = new Connection(IP, port);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(getClass() + " method onLoginButtonClick() \n");
                connection.setFlagPopUp(false);
                String email = txt_email.getText();
                LoginRequest loginRequest = new LoginRequest(email);

                /**
                 * Invio della richiesta al server -> il metodo
                 * sendRequest() esegue il metodo openConnection() !
                 */

                Object response = connection.sendRequest(loginRequest);

                /**
                 * Carico le email relative all'account
                 */
                PostBox account = (PostBox) response;
                connection.setAccount(account);

                // if response == PostBox --> load FXML client-home-view
                if (account != null && account.getEmail().equals(email)) {

                    // Opening home view
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/mail-client-home-view.fxml"));
                    Parent scene = (Parent) fxmlLoader.load();
                    controllerHome = fxmlLoader.getController();

                    /*
                     * imposto i valori di ControllerHome
                     * passando le configurazioni di ControllerLogin
                     */
                    controllerHome.setConnection(connection);
                    controllerHome.setControllerLogin(this);
                    controllerListEmail.setControllerHome(controllerHome);
                    controllerListEmail.setConnection(connection);
                    controllerListEmail.onButtonIncomingEmailClick(); //avvio la ricerca delle email nella casella di posta
                    Stage stage = new Stage();
                    stage.setScene(new Scene(scene,650,520));
                    controllerHome.setStage(stage);
                    stage.show();

                    // Closing login view
                    ((Stage) btn_login.getScene().getWindow()).close();

                } else {
                    lbl_err_email.setText("This email does not exist");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
