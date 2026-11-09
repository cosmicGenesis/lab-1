package lab1;

import java.util.concurrent.ConcurrentHashMap;

public class AttemptTracker {
    ConcurrentHashMap<String,UserState> states;
    int attemptsPermitted;
    int blockTime;

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
}
