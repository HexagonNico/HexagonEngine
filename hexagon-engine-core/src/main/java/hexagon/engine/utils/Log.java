package hexagon.engine.utils;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class to print messages to the console with additional information.
 * 
 * @author Nico
 */
public final class Log {
	
	/**This class should not be instantiated */
	private Log() {}

	/**
	 * Prints an info message to the console.
	 * 
	 * @param message The message to print.
	 */
	public static void info(String message) {
		print(System.out, "Info", message);
	}

	/**
	 * Prints a warning message to the console.
	 * Used for non serious errors.
	 * 
	 * @param message The message to print.
	 */
	public static void warning(String message) {
		print(System.out, "Warning", message);
	}

	/**
	 * Prints an error message to the console.
	 * 
	 * @param message The message to print.
	 */
	public static void error(String message) {
		print(System.err, "Error", message);
	}

	/**
	 * Prints a fatal error to the console.
	 * Used for errors that inevitably cause the program to stop.
	 * 
	 * @param message The message to print.
	 */
	public static void fatal(String message) {
		print(System.err, "Fatal", message);
	}
	
	/**
	 * Prints a message to the console, avoids duplicate code.
	 * 
	 * @param stream Print stream, {@code System.out} or {@code System.err}
	 * @param level Level of severity, changes the beginning of the message
	 * @param message The message to print
	 */
	private static void print(PrintStream stream, String level, String message) {
		stream.println("[" + getTime() + "] [HexagonEngine] [" + getCaller() + "] [" + level + "]: " + message);
	}

	/**
	 * Gets the name of the caller class.
	 * 
	 * @return Full name of the caller class.
	 */
	private static String getCaller() {
		try {
			return Class.forName(Thread.currentThread().getStackTrace()[4].getClassName()).getSimpleName();
		} catch (ClassNotFoundException e) {
			return "Unknown caller";
		}
	}

	/**
	 * Gets the current time.
	 * 
	 * @return Current system time.
	 */
	private static String getTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);
	}
}
