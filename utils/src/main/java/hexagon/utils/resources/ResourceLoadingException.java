package hexagon.utils.resources;

/**
 * Exception thrown when a resource cannot be loaded.
 * 
 * @author Nico
 */
public class ResourceLoadingException extends Exception {
	
	public ResourceLoadingException(String message, Throwable cause) {
		super(message, cause);
	}
}
