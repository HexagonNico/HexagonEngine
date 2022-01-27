package hexagon.engine.utils.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import hexagon.engine.utils.Log;

/**
 * A utility class to manage the loading of resources.
 * 
 * @author Nico
 */
public final class Resources {
	
	/**
	 * Loads a file as a string, joining all the lines in the file with a line separator.
	 * 
	 * @param filePath Path of the file to load from the resources folder starting with "/".
	 * @return The whole content of the file as a single string.
	 * @throws ResourceLoadingException If an IO error occurs while loading the file.
	 */
	public static String readAsString(String filePath) throws ResourceLoadingException {
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(Resources.class.getResourceAsStream(filePath)))) {
			return reader.lines().collect(Collectors.joining("\n"));
		} catch(NullPointerException e) {
			Log.error("Could not find file " + filePath);
			throw new ResourceLoadingException("Exception loading resource " + filePath, e);
		} catch(IOException e) {
			Log.error("IO error occurred while reading " + filePath);
			throw new ResourceLoadingException("Exception loading resource " + filePath, e);
		}
	}
}