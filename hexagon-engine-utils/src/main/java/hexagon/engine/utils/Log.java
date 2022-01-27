package hexagon.engine.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class to print messages to the console with the name of the caller class and the time.
 * 
 * @author Nico
 */
public final class Log {

	/**
	 * Prints an info message to the console.
	 * 
	 * @param message The message to print.
	 */
	public static void info(String message) {
		System.out.println("[" + getTime() + "] [" + getCaller() + "] [Info]: " + message);
	}

	/**
	 * Prints an error message to the console.
	 * 
	 * @param message The message to print.
	 */
	public static void error(String message) {
		System.err.println("[" + getTime() + "] [" + getCaller() + "] [Error]: " + message);
	}

	/**
	 * Gets the name of the caller class.
	 * 
	 * @return Full name of the caller class.
	 */
	private static String getCaller() {
		try {
			return Class.forName(Thread.currentThread().getStackTrace()[3].getClassName()).getName();
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
