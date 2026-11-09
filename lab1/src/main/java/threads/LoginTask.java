package threads;

public class LoginTask implements Runnable{
    String username;
    String password;

    public LoginTask(String username,String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        // Attempt to find matching user
    }
}
