package exceptions;

public class InvalidPasswordException extends Exception{
	public InvalidPasswordException(String text) {
		super(text);
	}
}
