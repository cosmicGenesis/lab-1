package threads;

import controllers.LoginController;
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
    private LoginController controller;

    public LoginTask(LoginController controller) {
        this.failQueue = ThreadCommunications.getLoginFailQueue();
        this.controller = controller;
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
            blockCheckThread.start(); // will throw an exception if blocked
            try {
                blockCheckThread.join();
                if(blockCheckThread.isBlocked()) {
                    return LoginStatus.BLOCKED;
                } else if(Database.login(username,password)) {
                    AttemptTracker tracker = Database.getAttemptTracker();
                    tracker.resetAttempts(username);
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
        LoginStatus status = attemptLogin();
        Platform.runLater(()->{
            controller.handleLoginResult(status,this.username);
        });
    }

}
