package hexagon.engine.glfw;

import java.util.HashSet;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import hexagon.engine.math.vector.Int2;

/**
 * Class used to handle mouse input.
 * 
 * @author Nico
 */
public final class Mouse {

	/**Left moues button code */
	public static final int BUTTON_LEFT = GLFW.GLFW_MOUSE_BUTTON_1;
	/**Right mouse button code */
	public static final int BUTTON_RIGHT = GLFW.GLFW_MOUSE_BUTTON_2;
	/**Middle mouse button code */
	public static final int BUTTON_MIDDLE = GLFW.GLFW_MOUSE_BUTTON_3;
	/**Thumb mouse button 4 code */
	public static final int BUTTON_4 = GLFW.GLFW_MOUSE_BUTTON_4;
	/**Thumb mouse button 5 code */
	public static final int BUTTON_5 = GLFW.GLFW_MOUSE_BUTTON_5;
	/**Thumb mouse button 6 code */
	public static final int BUTTON_6 = GLFW.GLFW_MOUSE_BUTTON_6;
	/**Thumb mouse button 7 code */
	public static final int BUTTON_7 = GLFW.GLFW_MOUSE_BUTTON_7;
	/**Thumb mouse button 8 code */
	public static final int BUTTON_8 = GLFW.GLFW_MOUSE_BUTTON_8;

	/**Set of mouse buttons that are currently pressed */
	private static final HashSet<Integer> pressedButtons = new HashSet<>();
	/**Current cursor position */
	private static Int2 cursor = new Int2(0, 0);

	// TODO - Event system

	/**
	 * Callback class that handle button presses.
	 * Protected and final since it is only used in {@link Engine}.
	 */
	protected static final class ButtonCallback implements GLFWMouseButtonCallbackI {

		@Override
		public void invoke(long window, int button, int action, int mods) {
			if(action == GLFW.GLFW_PRESS) {
				pressedButtons.add(button);
			} else if(action == GLFW.GLFW_RELEASE) {
				pressedButtons.remove(button);
			}
		}
	}

	/**
	 * Callback class that handle cursor position.
	 * Protected and final since it is only used in {@link Engine}.
	 */
	protected static final class CursorCallback implements GLFWCursorPosCallbackI {

		@Override
		public void invoke(long window, double px, double py) {
			Mouse.cursor = new Int2((int) px, (int) py);
		}
	}
	
	/**
	 * Checks if a given mouse button is currently pressed.
	 * 
	 * @param code Mouse button code (use constants in {@link Mouse} class)
	 * 
	 * @return True if that button is currently pressed, otherwise false
	 */
	public static boolean isButtonDown(int code) {
		return pressedButtons.contains(code);
	}

	/**
	 * Gets the current position of the cursor in pixels from the top left corner of the window.
	 * 
	 * @return A 2D integer vector containing the cursor position
	 */
	public static Int2 position() {
		return cursor;
	}

	/**Class should not be instantiated */
	private Mouse() {}
}
