package hexagon.utils.reflection;

public class ReflectionException extends Exception {

	public ReflectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReflectionException(String message) {
		super(message);
	}

	public ReflectionException(Throwable cause) {
		super(cause);
	}

	public ReflectionException() {
		super();
	}
}
