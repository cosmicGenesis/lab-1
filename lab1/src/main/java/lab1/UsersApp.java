package lab1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.example.lab1.HelloApplication;
import exceptions.InvalidPasswordException;
import exceptions.InvalidUserException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UsersApp extends Application {

	public static void loadUsers() {
		ArrayList<User> users = new ArrayList<User>();
		
		try {
			// read the user candidates from file
			BufferedReader reader = new BufferedReader(new FileReader("src/users.txt"));
			String line;
			while((line = reader.readLine()) != null) {
				try {
					String[] parts = line.split("\\s+");
					if (parts.length >= 2) {
						// create user one by one
						String username = parts[0];
						String password = parts[1];
						User newUser = new User(username, password);
						users.add(newUser);
					}	
					
				     
				}catch (InvalidUserException | InvalidPasswordException e) {
					// user validity error - print attempted user + error
					System.out.println(line + " " + e.getMessage());
				}
			}
			reader.close();
			
			// sort the users and print
			Collections.sort(users, (u1, u2) -> u1.getUsername().compareTo(u2.getUsername()));
			for (User u : users) {
				System.out.println(u.getUsername() + " " + u.getPassword());
			}
			
			// output valid users to desired file
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/out.txt"));
			for(User u: users) {
				String userLine = u.getUsername() + " " + u.getPassword() + "\n";
				writer.write(userLine);
			}
			writer.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Error: The file users.txt was not found.");
		} catch (IOException e) {
			System.out.println("Error: A problem occurred while reading the file.");
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		loadUsers();
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginView.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
		stage.setTitle("Login");
		stage.setScene(scene);
		stage.show();
	}
}
