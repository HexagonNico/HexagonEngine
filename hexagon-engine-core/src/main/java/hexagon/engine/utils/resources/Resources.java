package hexagon.engine.utils.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A utility class to manage the loading of resources.
 * 
 * @author Nico
 */
public final class Resources {
	
	/**
	 * Loads a file as a string, joining all the lines in the file with a line separator.
	 * 
	 * @param filePath Path of the file to load from the resources folder starting with {@code /}.
	 * 
	 * @return The whole content of the file as a single string.
	 * 
	 * @throws ResourceLoadingException If an IO error occurs while loading the file.
	 */
	public static String readAsString(String filePath) throws ResourceLoadingException {
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(Resources.class.getResourceAsStream(filePath)))) {
			return reader.lines().collect(Collectors.joining("\n"));
		} catch(NullPointerException e) {
			throw new ResourceLoadingException("Cannot find file " + filePath, e);
		} catch(IOException e) {
			throw new ResourceLoadingException("Exception loading resource " + filePath, e);
		}
	}

	/**
	 * Performs a certain operation for every line of the file.
	 * 
	 * @param filePath Path of the file to load from the resources folder starting with {@code /}.
	 * @param action Action to perform for each line.
	 * 
	 * @throws ResourceLoadingException If an IO error occurs while loading the file.
	 */
	public static void forEachLine(String filePath, Consumer<String> action) throws ResourceLoadingException {
		try(Scanner scanner = new Scanner(Resources.class.getResourceAsStream(filePath))) {
			while(scanner.hasNextLine()) {
				action.accept(scanner.nextLine());
			}
		} catch(NullPointerException e) {
			throw new ResourceLoadingException("Cannot find file " + filePath, e);
		}
	}
}
