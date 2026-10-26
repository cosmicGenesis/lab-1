package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lab1.User;
import util.Database;
import util.ScreenManager;

import java.io.IOException;
import java.util.ArrayList;

public class loginController {

    @FXML
    private Label errorTextField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField unameField;

    @FXML
    void login(ActionEvent event) throws IOException {
        String uname = unameField.getText();
        String pass = passwordField.getText();

        // Attempt to find matching user
        boolean userFound = Database.findUser(uname,pass);
        if (userFound) {
            ScreenManager.goToScreen("welcomeView.fxml","Welcome to the System");
        } else {
            errorTextField.setText("Username or Password are incorrect");
        }
    }

}
