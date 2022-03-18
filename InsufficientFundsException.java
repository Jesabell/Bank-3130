
public class InsufficientFundsException extends Exception{
	public InsufficientFundsException() {
		super("Insufficient Funds Available");
	}
	public InsufficientFundsException(String message) {
		super(message);
	}


}
