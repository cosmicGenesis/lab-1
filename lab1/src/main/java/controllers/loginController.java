package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class loginController {

    @FXML
    private Label errorTextField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField unameField;

    @FXML
    void login(ActionEvent event) {
        errorTextField.setText("AAAA");
    }

}
