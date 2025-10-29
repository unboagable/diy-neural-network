package utilities;

public class NetworkException extends Exception {
	private static final long serialVersionUID = -247899653338152191L;
	public NetworkException() { super(); }
	public NetworkException(String message) { super(message); }
	public NetworkException(String message, Throwable cause) { super(message, cause); }
	public NetworkException(Throwable cause) { super(cause); }
}
