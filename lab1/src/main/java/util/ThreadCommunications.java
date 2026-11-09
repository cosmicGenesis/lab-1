package util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadCommunications {
    private static BlockingQueue<FailedAttempt> loginFailQueue = new ArrayBlockingQueue<FailedAttempt>(1);

    public static BlockingQueue<FailedAttempt> getLoginFailQueue() {
        return loginFailQueue;
    }

    // methods
//    public static void putFailedAttempt(FailedAttempt failedAttempt) throws InterruptedException {
//        blockingQueue.put(failedAttempt);
//    }
//
//    public static FailedAttempt takeFailedAttempt() throws InterruptedException {
//        return blockingQueue.take();
//    }
}
