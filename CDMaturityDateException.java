
public class CDMaturityDateException extends Exception {

	public CDMaturityDateException() {
		super("CD maturity date not reached");
	}
	public CDMaturityDateException(String message) {
		super(message);
	}
}
