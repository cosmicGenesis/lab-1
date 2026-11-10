package threads;

import controllers.LoginController;
import javafx.application.Platform;
import util.Database;

public class CountdownTask implements Runnable{
    private LoginController controller;

    public CountdownTask(LoginController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        int secondsRemaining = Database.getAttemptTracker().getBlockTime();
        while (secondsRemaining > 0) {
            final int currCount = secondsRemaining;
            Platform.runLater(()-> {
                controller.updateBlockTime(currCount);
            });

            try {
                Thread.sleep(1000);
                secondsRemaining--;
            } catch (InterruptedException e) {
                break;
            }
        }

        Platform.runLater(()->{
            controller.releaseBlock();
        });
    }
}
