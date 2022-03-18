
public class CheckTooOldException extends Exception {

	public CheckTooOldException() {
		super("Check Too Old");
	}
	public CheckTooOldException(String message) {
		super(message);
	}
}
