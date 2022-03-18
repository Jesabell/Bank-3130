
public class PostDatedCheckException extends Exception{

	public PostDatedCheckException() {
		super("Date of check not yet reached");
	}
	public PostDatedCheckException(String message) {
		super(message);
	}

}
