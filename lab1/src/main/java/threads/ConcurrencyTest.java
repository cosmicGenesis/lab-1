package threads;

import lab1.AttemptTracker;
import lab1.UserState;
import java.util.concurrent.CountDownLatch;

public class ConcurrencyTest {

    public static String runExperiment() {
        int n = 10000;
        int blockTime = 30;
        String testEmail = "stress_test@haifa.ac.il";

        AttemptTracker tracker = new AttemptTracker(n, blockTime);
        tracker.addUser(testEmail);

        CountDownLatch startGate = new CountDownLatch(1);
        Thread[] threads = new Thread[20];
        for (int i = 0; i < 20; i++) {
            threads[i] = new Thread(() -> {
                try {
                    startGate.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                for (int j = 0; j < 499; j++) {
                    tracker.increaseFailedAttempts(testEmail);
                    Thread.yield();
                }
            });
        }

        for (Thread t : threads) t.start();
        startGate.countDown();

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        UserState state = tracker.getUserState(testEmail);
        int countAfterThreads = state.getFailedAttempts();
        boolean blockedAfterThreads = state.isBlocked();

        boolean blockedDuring19 = false;
        for (int k = 1; k <= 19; k++) {
            if (tracker.increaseFailedAttempts(testEmail)) {
                blockedDuring19 = true;
            }
        }

        boolean blockedOn20th = tracker.increaseFailedAttempts(testEmail);

        String report = String.format(
            "=== Concurrency Test Results ===%n" +
            "Expected attempts after 20 threads: 9980 | Actual: %d%n" +
            "Blocked after 9980 attempts? %b (Expected: false)%n" +
            "Blocked during next 19 attempts? %b (Expected: false)%n" +
            "Blocked on 20th attempt (10,000th)? %b (Expected: true)%n" +
            "================================",
            countAfterThreads, blockedAfterThreads, blockedDuring19, blockedOn20th
        );

        System.out.println(report);
        return "Actual count: " + countAfterThreads + " | Blocked on 20th: " + blockedOn20th;
    }
}