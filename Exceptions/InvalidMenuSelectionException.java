
public class InvalidMenuSelectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InvalidMenuSelectionException() {
		super("Wrong menu input");
	}
	public InvalidMenuSelectionException(char message) {
		super("Wrong menu input");
	}
}
