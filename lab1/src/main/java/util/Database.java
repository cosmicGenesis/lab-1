package util;

import lab1.AttemptTracker;
import lab1.User;
import lab1.UserState;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Database {
    private static ArrayList<User> users = new ArrayList<User>();
    private static AttemptTracker attemptTracker;

    // Getters/Setters
    public static ArrayList<User> getUsers() {
        return users;
    }

    public static AttemptTracker getAttemptTracker() {
        return attemptTracker;
    }

    /* Normally, a setter would be added for classes, however this is a security flaw
           for classes acting as databases

         */
    public static void setUsers(ArrayList<User> users) {
        Database.users = users;
    }

    // methods
    public static void addUser(User user) {
        users.add(user);
        attemptTracker.addUser(user.getUsername());
    }

    public static boolean findUser(String username) {
        for( User u : users) {
            if (u.getUsername().equals(username)) {
                //user found
                return true;
            }
        }
        // if reached here, no matches
        return false;
    }

    public static boolean login(String username,String password) {
        // Attempt to find user
        for( User u : users) {
            boolean unameMatch = u.getUsername().equals(username);
            boolean passMatch = u.getPassword().equals(password);
            if (unameMatch && passMatch) {
                //user found
                return true;
            }
        }
        // if reached here, no matches
        return false;
    }

    public static void initTracker(int maxAttempts, int blockTime) {
        attemptTracker = new AttemptTracker(maxAttempts,blockTime);
    }

    public static UserState getUserState(String username) {
        return attemptTracker.getUserState(username);
    }
}
