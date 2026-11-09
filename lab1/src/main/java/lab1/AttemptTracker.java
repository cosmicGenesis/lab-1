package lab1;

import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Math.min;

public class AttemptTracker {
    ConcurrentHashMap<String,UserState> states;
    private int attemptsPermitted;
    private int blockTime;

    public AttemptTracker(int attemptsPermitted, int blockTime) {
        this.attemptsPermitted = attemptsPermitted;
        this.blockTime = blockTime;
        this.states = new ConcurrentHashMap<String, UserState>();
    }

    public void addUser(String username) {
        this.states.put(username,new UserState());
    }

    public UserState getUserState(String username) {
        return this.states.getOrDefault(username,null);
    }

    //methods

    // increase a users' failed login count, returns if results in a block or not
    public boolean increaseFailedAttempts(String username) {
        UserState state = getUserState(username);
        int failedAttempts = state.getFailedAttempts() + 1;

        if (failedAttempts >= this.attemptsPermitted) {
            state.setFailedAttempts(this.attemptsPermitted);
            // set block timer
            return true;
        } else {
            return false;
        }
    }
}
