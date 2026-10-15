package lab1;

import exceptions.InvalidPasswordException;
import exceptions.InvalidUserException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
	private static Pattern usernamePattern = Pattern.compile("^[a-z0-9%+\\-.]+@[a-z0-9][a-z0-9.\\-]*\\.[a-z]{2,}$", Pattern.CASE_INSENSITIVE);
	
	// fields
	protected String username;
	protected String password;
	
	//Ctor
	public User(String username, String password) throws InvalidUserException,InvalidPasswordException{
		super();
		
		//insert validation
		validateUname(username);
		validatePass(password);
		
		this.username = username;
		this.password = password;
	}
	
	
	// getters
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	//setters

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	// validation tests
	private void validateUname(String username) throws InvalidUserException{
		if (username == null || username.isEmpty()) {
			throw new InvalidUserException("Please enter a valid Email as username");
		}
		if (username.length() > 50) {
			throw new InvalidUserException("Username is too long, try something shorter");
		}
		
		Matcher usernameMatcher = usernamePattern.matcher(username);
		if(!usernameMatcher.find()) {
			throw new InvalidUserException("Please enter a valid Email as username");
		}
	}
	
	private void validatePass(String password) throws InvalidPasswordException {
		if (password == null) {
			throw new InvalidPasswordException("Please enter a valid password");
		}
		
		if (password.length() < 8) {
			throw new InvalidPasswordException("Your password is too short, add more characters");
		}
		
		if (password.length() > 12) {
			throw new InvalidPasswordException("Your password is too long, try a shorter one");
		}
		
		Pattern letterP = Pattern.compile("[a-z]",Pattern.CASE_INSENSITIVE);
		Pattern numberP = Pattern.compile("[0-9]");
		Pattern symbolP = Pattern.compile("[^a-zA-Z0-9 ]");
		
		boolean hasLetter = letterP.matcher(password).find();
		boolean hasNumber = numberP.matcher(password).find();
		boolean hasSymbol = symbolP.matcher(password).find();
		boolean hasSpace = password.contains(" ");
		if (!(hasLetter && hasNumber && hasSymbol) || hasSpace) {
			throw new InvalidPasswordException("Please enter a valid password");
		}
	}
}
