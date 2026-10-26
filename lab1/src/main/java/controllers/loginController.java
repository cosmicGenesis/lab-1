package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lab1.User;
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
        /* temporary init arraylist
           will be replaced with actual data calls
         */
        ArrayList<User> usersList = new ArrayList<User>();

        String uname = unameField.getText();
        String pass = passwordField.getText();

        //perform data call
        boolean userFound = true;
        if (userFound) {
            ScreenManager.goToScreen("welcomeView.fxml","test");
        } else {

        }
    }

}
