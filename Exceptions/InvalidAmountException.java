
public class InvalidAmountException extends Exception {
	public InvalidAmountException() {
		super("Amount not allowed");
	}
	public InvalidAmountException(String message) {
		super(message);
	}


}
