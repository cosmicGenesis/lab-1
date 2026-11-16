package lab1;

public class UserState {
    private int failedAttempts;
    private int blockPeriodEnd;

    public UserState(){
        this.failedAttempts = 0;
        this.blockPeriodEnd = 0;
    }
    public synchronized boolean isBlocked() {
        if (blockPeriodEnd == 0) {
            return false;
        }
        if (System.currentTimeMillis() >= blockPeriodEnd) {
            this.failedAttempts = 0;
            this.blockPeriodEnd = 0;
            return false;
        }
        return true;
    }

    synchronized public int getBlockPeriodEnd() {
        return blockPeriodEnd;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public void setBlockPeriodEnd(int blockPeriodEnd) {
        this.blockPeriodEnd = blockPeriodEnd;
    }
}
