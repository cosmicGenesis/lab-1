package threads;

import javafx.application.Platform;
import javafx.scene.control.Label;
import lab1.AttemptTracker;
import util.*;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class LoginTask implements Runnable{
    private String username;
    private String password;
    private BlockingQueue<FailedAttempt> failQueue;
    private Label statusField;
    private LoginStatus status;

    public LoginTask(Label statusField) {
        this.failQueue = ThreadCommunications.getLoginFailQueue();
        this.statusField = statusField;
    }

    public void setLoginData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // attempt login
    private LoginStatus attemptLogin() {
        //attempt to find user
        if(Database.findUser(username)) {
            // check if blocked
            BlockCheckThread blockCheckThread = new BlockCheckThread(username);
            blockCheckThread.run(); // will throw an exception if blocked
            try {
                blockCheckThread.join();
                if(blockCheckThread.isBlocked()) {
                    return LoginStatus.BLOCKED;
                } else if(Database.login(username,password)) {
                    return LoginStatus.SUCCESS;
                } else {
                    failQueue.put(new FailedAttempt(username));
                    return LoginStatus.FAILED;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            return LoginStatus.USER_NOT_FOUND;
        }
    }

    // general run func
    @Override
    public void run() {
        this.status = attemptLogin();
        Platform.runLater(()->{
            switch ((status)){
                case USER_NOT_FOUND:
                    statusField.setText("Incorrect login");
                    break;
                case FAILED:
                    // calc attempts left and print
                    AttemptTracker tracker = Database.getAttemptTracker();
                    int attemptsLeft = tracker.getAttemptsLeft(this.username);
                    if(attemptsLeft > 0) {
                        statusField.setText("Incorrect login, " + attemptsLeft + " attempts left");
                    } else {
                        statusField.setText("Incorrect login, user blocked");
                    }
                    break;
                case SUCCESS:
                    try {
                        ScreenManager.goToScreen("welcomeView.fxml","Welcome to the System");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case BLOCKED:
                    //TODO: print blocked timer
            }
        });
    }

}
