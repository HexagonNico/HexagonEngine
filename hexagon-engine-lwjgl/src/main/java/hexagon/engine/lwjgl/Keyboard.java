package hexagon.engine.lwjgl;

import java.util.HashSet;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

/**
 * Class used to handle key presses.
 * 
 * @author Nico
 */
public final class Keyboard implements GLFWKeyCallbackI {

	/**Holds the currently pressed keys */
	private static final HashSet<Integer> pressedKeys = new HashSet<>();

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if(action == GLFW.GLFW_PRESS) {
			pressedKeys.add(key);
		} else if(action == GLFW.GLFW_RELEASE) {
			pressedKeys.remove(key);
		}
	}

	/**
	 * Checks if a key is currently being held down.
	 * 
	 * @param key Key to check.
	 * 
	 * @return True if the key is pressed, otherwise false.
	 */
	public static boolean isKeyDown(Key key) {
		return isKeyDown(key.code);
	}

	/**
	 * Checks if a key is currently being held down.
	 * 
	 * @param keyCode Code of the key to check.
	 * 
	 * @return True if the key is pressed, otherwise false.
	 */
	public static boolean isKeyDown(int keyCode) {
		return pressedKeys.contains(keyCode);
	}

	/**
	 * Gets a positive or negative value controlled by two keys.
	 * 
	 * @param positiveKey Positive direction of the axis.
	 * @param negativeKey Negative direction of the axis.
	 * 
	 * @return 1 if positive key is pressed, -1 if negative key is pressed, 0 if both or neither are pressed.
	 */
	public static int getAxis(Key positiveKey, Key negativeKey) {
		boolean positive = isKeyDown(positiveKey);
		boolean negative = isKeyDown(negativeKey);
		return positive ? (negative ? 0 : 1) : (negative ? -1 : 0);
	}

	/**
	 * Wrapper for GLFW key codes.
	 */
	public static enum Key {
		W(GLFW.GLFW_KEY_W),
		A(GLFW.GLFW_KEY_A),
		S(GLFW.GLFW_KEY_S),
		D(GLFW.GLFW_KEY_D),
		LEFT_SHIFT(GLFW.GLFW_KEY_LEFT_SHIFT),
		SPACE(GLFW.GLFW_KEY_SPACE),
		UP(GLFW.GLFW_KEY_UP),
		DOWN(GLFW.GLFW_KEY_DOWN),
		LEFT(GLFW.GLFW_KEY_LEFT),
		RIGHT(GLFW.GLFW_KEY_RIGHT);

		private int code;

		Key(int code) {
			this.code = code;
		}
	}
}
