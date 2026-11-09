package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private Button loginBtn;

    @FXML
    void login(ActionEvent event) throws IOException {
        String uname = unameField.getText();
        String pass = passwordField.getText();

        loginBtn.setDisable(true);

        //TODO: init login view
    }

}
