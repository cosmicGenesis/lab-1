package lab1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import exceptions.InvalidPasswordException;
import exceptions.InvalidUserException;

public class UsersApp {

	public static void main(String[] args) {
		ArrayList<User> users = new ArrayList<User>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
			String line;
			while((line = reader.readLine()) != null) {
				try {
					String[] parts = line.split("\\s+");
					if (parts.length >= 2) {
						String username = parts[0];
						String password = parts[1];
						User newUser = new User(username, password);
						users.add(newUser);
					}	
					
				     
				}catch (InvalidUserException | InvalidPasswordException e) {
					System.out.println(line + " " + e.getMessage());
				}
			}
			reader.close();

			Collections.sort(users, (u1, u2) -> u1.getUsername().compareTo(u2.getUsername()));
			for (User u : users) {
				System.out.println(u.getUsername() + " " + u.getPassword());
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Error: The file users.txt was not found.");
		} catch (IOException e) {
			System.out.println("Error: A problem occurred while reading the file.");
		}
	}

}
