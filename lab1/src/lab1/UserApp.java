package lab1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UserApp {

	public static void main(String[] args) {
		ArrayList<User> users = new ArrayList<User>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
			String line;
			while((line = reader.readLine()) != null) {
				try {
					
				} catch (Exception e) {
					
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
