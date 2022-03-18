
public class InvalidAccountException extends Exception{
	public InvalidAccountException() {
		super("Account not found");
	}
	public InvalidAccountException(String message) {
		super(message);
	}

}
