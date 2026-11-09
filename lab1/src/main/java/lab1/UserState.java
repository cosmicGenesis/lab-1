package lab1;

public class UserState {
    private int failedAttempts;
    private int blockPeriodEnd;

    public UserState(){
        this.failedAttempts = 0;
        this.blockPeriodEnd = 0;
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

}
