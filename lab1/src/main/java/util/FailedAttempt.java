package util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FailedAttempt {
    private final String username;
    private final BlockingQueue<Boolean> responseQueue = new ArrayBlockingQueue<>(1);

    public FailedAttempt(String email) {
        this.username = email;
    }


    //getters
    public String getUsername() {
        return username;
    }

    // methods

    //set
    public void setResponse(boolean isBlocked) throws InterruptedException{
        responseQueue.put(isBlocked);
    }

    // Called by LoginTask to wait for the answer (blocks without busy waiting)
    public boolean getResponse() throws InterruptedException{
        return responseQueue.take();
    }
}
