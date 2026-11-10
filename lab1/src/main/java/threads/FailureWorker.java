package threads;

import lab1.AttemptTracker;
import util.Database;
import util.FailedAttempt;
import util.ThreadCommunications;

import java.util.concurrent.BlockingQueue;

public class FailureWorker extends Thread{
    private BlockingQueue<FailedAttempt> failQueue;

    public FailureWorker(){
        this.failQueue = ThreadCommunications.getLoginFailQueue();
        this.setDaemon(true);
    }

    public void run() {
        try {
            while(true) {
                // wait for event
                FailedAttempt failedAttempt = this.failQueue.take();

                //Draw data from the event
                String username = failedAttempt.getUsername();

                //Increase fail count and return result
                AttemptTracker tracker = Database.getAttemptTracker();
                failedAttempt.setResponse(tracker.increaseFailedAttempts(username));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
