package utilities;

public class NeuronException extends Exception {
	private static final long serialVersionUID = -737858061907361193L;
	
	public NeuronException() { super(); }
	public NeuronException(String message) { super(message); }
	public NeuronException(String message, Throwable cause) { super(message, cause); }
	public NeuronException(Throwable cause) { super(cause); }
}
