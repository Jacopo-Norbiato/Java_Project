package com.example.client;


import com.example.model.PostBox;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

public class Connection {
    private PostBox account = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private Socket socket;
    private String host;
    private int port;
    private boolean flagPopUp = false;

    public Connection(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
    }

    public void setFlagPopUp(boolean value){
        flagPopUp = value;
    }

    public PostBox getAccount(){
        return this.account;
    }

    public String getName() {
        return this.account.getName();
    }

    public void setAccount(PostBox account){
        this.account = account;
    }

    public String getEmail() {
        return this.account.getEmail();
    }

    /**
     * This method makes the connection request to the server.
     *
     * @param request
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     *
     *
     * sendRequest() open the socket and wait for the response.
     * After that, it closes the socket
     */
    public Object sendRequest(Object request) throws IOException, ClassNotFoundException {
        try {

            openConnection();
            // Invia la richiesta al server
            out.writeObject(request);
            System.out.println(getClass() + "/ -- Richiesta inviata da Client.Connection.sendRequest() al Server");
            // Ricevi la risposta dal server
            Object response = in.readObject();
            // Chiudi la connessione con il server
            System.out.println(getClass() + "/ -- Risposta ottenuta dal Server: " + response.toString());
            closeConnection();
            //flagPopUp = true;
            return response;

        } catch (SocketException | NullPointerException | EOFException e) {
            showPopUp();
        }
        return null;
    }

    /**
     * This method shows the pop-up in case the server is shut down.
     */
    public void showPopUp(){
        if (flagPopUp) {
            Platform.runLater(() -> {  // "runLater": usato tutte le volte che vogliamo fare delle modifiche alla view ma siamo in un altro thread

                //Creo un pop-up di tipo Alert
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle("Error with server");
                alert.setContentText("Server disconnected, please wait!");
                // Add an OK button to the alert
                alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
                Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setStyle("-fx-background-color: linear-gradient(to right, rgb(248, 115, 43), rgb(246, 33, 153)); " +
                        "-fx-text-fill: white; -fx-font-weight: bold; " +
                        "-fx-text-alignment: CENTER;" +
                        "-fx-border-width: 0px;" +
                        "-fx-border-radius: 0.4em;" +
                        "-fx-background-radius: 0.4em;");
                alert.getDialogPane().setStyle("-fx-background-color: white;");
                alert.showAndWait();
            });
        }
        flagPopUp = false;
    }


    /**
     * This method opens connection with server.
     *
     * @throws IOException
     */
    public void openConnection() throws IOException {
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            out.flush(); //flush() -> it means to clear the stream of any element that may be inside the stream
        } catch (ConnectException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        System.out.println(getClass() + "/ -- Connessione eseguita correttamente\n" + "** PORT : " + socket.getPort() + " // SocketChannel : " + socket.getChannel() + " // SocketInetAddress " +socket.getInetAddress());
    }

    /**
     * This method closes the connection with server.
     *
     * @throws IOException
     */
    public void closeConnection() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

}