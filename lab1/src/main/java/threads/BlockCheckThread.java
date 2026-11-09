package threads;

import lab1.UserState;
import util.Database;

public class BlockCheckThread extends Thread{
    private String uname;
    private boolean isBlocked;

    public BlockCheckThread(String uname) {
        this.uname = uname;
    }

    // status getter
    public boolean isBlocked() {
        return this.isBlocked;
    }

    // run func
    @Override
    public void run() {
        UserState state = Database.getUserState(this.uname);
        this.isBlocked = state.getBlockPeriodEnd() != 0;
    }
}
