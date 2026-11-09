package lab1;

public class UserState {
    private boolean isBlocked;
    private int blockPeriodEnd;

    public UserState(){
        this.isBlocked = false;
        this.blockPeriodEnd = 0;
    }

    synchronized public boolean isBlocked() {
        return isBlocked;
    }

    synchronized public int getBlockPeriodEnd() {
        return blockPeriodEnd;
    }

    // methods
    synchronized public void blockUser(int blockPeriodEnd) {
        this.isBlocked = true;
        this.blockPeriodEnd = blockPeriodEnd;
    }

}
