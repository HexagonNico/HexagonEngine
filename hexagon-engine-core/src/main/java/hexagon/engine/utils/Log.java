package hexagon.engine.utils;

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
		System.out.println("[" + getTime() + "] [HexagonEngine] [" + getCaller() + "] [Info]: " + message);
	}

	/**
	 * Prints a warning message to the console.
	 * 
	 * @param message The message to print.
	 */
	public static void warning(String message) {
		System.out.println("[" + getTime() + "] [HexagonEngine] [" + getCaller() + "] [Warning]: " + message);
	}

	/**
	 * Prints an error message to the console.
	 * 
	 * @param message The message to print.
	 */
	public static void error(String message) {
		System.err.println("[" + getTime() + "] [HexagonEngine] [" + getCaller() + "] [Error]: " + message);
	}

	/**
	 * Gets the name of the caller class.
	 * 
	 * @return Full name of the caller class.
	 */
	private static String getCaller() {
		try {
			return Class.forName(Thread.currentThread().getStackTrace()[3].getClassName()).getSimpleName();
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
