package util;

import lab1.User;

import java.util.ArrayList;

public class Database {
    private static ArrayList<User> users = new ArrayList<User>();

    // Getters/Setters
    public static ArrayList<User> getUsers() {
        return users;
    }

    /* Normally, a setter would be added for classes, however this is a security flaw
       for classes acting as databases

     */
//    public static void setUsers(ArrayList<User> users) {
//        Database.users = users;
//    }

    // methods
    public static void addUser(User user) {
        users.add(user);
    }

    public static boolean findUser(String username,String password) {
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
}
