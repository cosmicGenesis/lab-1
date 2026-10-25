package exceptions;

public class InvalidUserException extends Exception{
	
	public InvalidUserException(String text) {
		super(text);
	}
}
