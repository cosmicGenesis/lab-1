package lab1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

import exceptions.InvalidPasswordException;
import exceptions.InvalidUserException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import threads.FailureWorker;
import util.Database;
import util.FailedAttempt;
import util.ScreenManager;
import util.ThreadCommunications;

public class UsersApp extends Application {

	public int getPosInt(String paramName) {
		int val = -1;
		System.out.print("Please input value for " + paramName + "(whole positive number): ");
		while (val < 1) {
            Scanner scanner = new Scanner(System.in);
            while (!scanner.hasNextInt()) {
				System.out.println("invalid input: Not an int");
				System.out.print("Please try again: ");
				scanner.next(); // Discard the invalid input token
			}

			val = scanner.nextInt();
			if(val < 1){
				System.out.println("Number must atleast 1");
				System.out.print("Please try again: ");
			}
		}
		return val;
	}

	public List<Integer> requestParams() {
		List<Integer> params = new ArrayList<>();
		params.add(getPosInt("Max Attempts"));
		params.add(getPosInt("Block Time (in seconds)"));
		return params;
	}

	public List<Integer> handleParams() {
		List<String> rawParams = getParameters().getRaw();
		List<Integer> params = new ArrayList<Integer>();

		// check if params are missing
		if (rawParams.size() < 2) {
			System.out.println("Missing Params, Please try again manually");
			params = requestParams();
		} else {
			// check param validity
			for (int i=0;i<2;i++) {
				String param = rawParams.get(i);
				String paramName = i == 0 ? "Max Attempts" : "Block Time (in seconds)";
				try {
					int val = Integer.parseInt(param);
					if (val < 1) {
						System.out.println("Invalid value for " + paramName);
						params.add(getPosInt(paramName));
					} else {
						params.add(val);
					}
                } catch (NumberFormatException e) {
					System.out.println("Invalid value for " + paramName);
					params.add(getPosInt(paramName));
				}
			}
		}
		return params;
	}

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
						Database.addUser(newUser);
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
		//init tracker
		List<Integer> params = handleParams();
		Database.initTracker(params.get(0),params.get(1));

		// init users
		loadUsers();

		// init and run FailureWorker pair
		FailureWorker fw = new FailureWorker();
		new Thread(fw).start();
		new Thread(fw).start();

		// init screen
		ScreenManager.setStage(stage);
		// Requirement 2.4: Ensure clicking 'X' closes the window and terminates the application completely
		stage.setOnCloseRequest(e -> javafx.application.Platform.exit());
		FXMLLoader fxmlLoader = new FXMLLoader(UsersApp.class.getResource("loginView.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
		stage.setTitle("Login");
		stage.setScene(scene);
		stage.show();
	}
}
