
public class AccountClosedException extends Exception{

	public AccountClosedException() {
		super("Account is closed");
	}
	public AccountClosedException(String message) {
		super(message);
	}
}
