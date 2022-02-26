package hexagon.engine.math;

/**
 * Utility class that adds some extra math functions that are not in {@link java.lang.Math}
 * 
 * @author Nico
 */
public final class MathExtra {
	
	/**
	 * Clamps a value between a maximum and a minimum.
	 * 
	 * @param value The value to clamp.
	 * @param min Minimum value.
	 * @param max Maximum value.
	 * 
	 * @return {@code min} if {@code value} is less than {@code min},
	 * 		{@code max} if {@code value} is greater than {@code max},
	 * 		{@code value} otherwise
	 */
	public static int clamp(int value, int min, int max) {
		return Math.max(min, Math.min(value, max));
	}

	/**
	 * Clamps a value between a maximum and a minimum.
	 * 
	 * @param value The value to clamp.
	 * @param min Minimum value.
	 * @param max Maximum value.
	 * 
	 * @return {@code min} if {@code value} is less than {@code min},
	 * 		{@code max} if {@code value} is greater than {@code max},
	 * 		{@code value} otherwise
	 */
	public static long clamp(long value, long min, long max) {
		return Math.max(min, Math.min(value, max));
	}

	/**
	 * Clamps a value between a maximum and a minimum.
	 * 
	 * @param value The value to clamp.
	 * @param min Minimum value.
	 * @param max Maximum value.
	 * 
	 * @return {@code min} if {@code value} is less than {@code min},
	 * 		{@code max} if {@code value} is greater than {@code max},
	 * 		{@code value} otherwise
	 */
	public static float clamp(float value, float min, float max) {
		return Math.max(min, Math.min(value, max));
	}

	/**
	 * Clamps a value between a maximum and a minimum.
	 * 
	 * @param value The value to clamp.
	 * @param min Minimum value.
	 * @param max Maximum value.
	 * 
	 * @return {@code min} if {@code value} is less than {@code min},
	 * 		{@code max} if {@code value} is greater than {@code max},
	 * 		{@code value} otherwise
	 */
	public static double clamp(double value, double min, double max) {
		return Math.max(min, Math.min(value, max));
	}

	/**Class should not be instantiated */
	private MathExtra() {}
}
