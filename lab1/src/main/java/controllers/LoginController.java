package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lab1.AttemptTracker;
import threads.CountdownTask;
import threads.LoginTask;
import threads.ConcurrencyTest;
import util.Database;
import util.LoginStatus;
import util.ScreenManager;

import java.io.IOException;

public class LoginController {
    private LoginTask loginTask;
    private CountdownTask countdownTask;

    @FXML
    private Label errorTextField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField unameField;

    @FXML
    private Button loginBtn;

    @FXML 
    private Button testBtn;


    private void runThread(Runnable task) {
        Thread newThread = new Thread(task);
        newThread.setDaemon(true);
        newThread.start();
    }

    @FXML
    void login(ActionEvent event) {
        String uname = unameField.getText();
        String pass = passwordField.getText();

        this.loginBtn.setDisable(true);
        this.unameField.setDisable(true);   
        this.passwordField.setDisable(true);

        this.loginTask.setLoginData(uname,pass);
        runThread(this.loginTask);
    }

    @FXML
    void runConcurrencyTest(ActionEvent event) {
        if (testBtn != null) testBtn.setDisable(true);
        errorTextField.setText("Running 20-thread concurrency test...");
        runThread(() -> {
            String summary = ConcurrencyTest.runExperiment();
            javafx.application.Platform.runLater(() -> {
                errorTextField.setText(summary);
                if (testBtn != null) testBtn.setDisable(false);
            });
        });
    }


    public void handleLoginResult(LoginStatus status, String username) {
        boolean releaseBtn = false;

        switch ((status)){
            case BLOCKED:
                runThread(this.countdownTask);
                break;
            case USER_NOT_FOUND:
                errorTextField.setText("Incorrect login");
                releaseBtn = true;
                break;
            case FAILED:
                // calc attempts left and print
                AttemptTracker tracker = Database.getAttemptTracker();
                int attemptsLeft = tracker.getAttemptsLeft(username);
                if(attemptsLeft > 0) {
                    errorTextField.setText("Incorrect login, " + attemptsLeft + " attempts left");
                    releaseBtn = true;
                } else {
                    runThread(this.countdownTask);
                }
                break;
            case SUCCESS:
                errorTextField.setText("");
                releaseBtn = true;
                try {
                    ScreenManager.goToScreen("welcomeView.fxml","Welcome to the System");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }

        loginBtn.setDisable(!releaseBtn);
        unameField.setDisable(!releaseBtn);   
        passwordField.setDisable(!releaseBtn);
    }

    public void updateBlockTime(int seconds) {
        errorTextField.setText("User blocked, " + seconds + " seconds left");
    }

    public void releaseBlock() {
        errorTextField.setText("");
        loginBtn.setDisable(false);
        unameField.setDisable(false);     
        passwordField.setDisable(false);
    }

    @FXML
    void initialize() {
        this.loginTask = new LoginTask(this);
        this.countdownTask = new CountdownTask(this);
    }
}
