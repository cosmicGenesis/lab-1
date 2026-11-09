package threads;

import javafx.application.Platform;
import util.Database;
import util.FailedAttempt;
import util.LoginStatus;
import util.ThreadCommunications;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LoginTask implements Runnable{
    private String username;
    private String password;
    private BlockingQueue<FailedAttempt> failQueue;

    public LoginTask(String username,String password) {
        this.username = username;
        this.password = password;
        this.failQueue = ThreadCommunications.getLoginFailQueue();
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
        LoginStatus status = attemptLogin();
        Platform.runLater(()->{
            switch ((status)){

            }
        });
    }

}
