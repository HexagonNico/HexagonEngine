package hexagon.lwjgl.glfw;

import java.util.HashSet;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

/**
 * Class used to handle key presses.
 * 
 * @author Nico
 */
public final class Keyboard implements GLFWKeyCallbackI {

	public static final int KEY_SPACE = GLFW.GLFW_KEY_SPACE;
	public static final int KEY_APOSTROPHE = GLFW.GLFW_KEY_APOSTROPHE;
	public static final int KEY_COMMA = GLFW.GLFW_KEY_COMMA;
	public static final int KEY_MINUS = GLFW.GLFW_KEY_MINUS;
	public static final int KEY_PERIOD = GLFW.GLFW_KEY_PERIOD;
	public static final int KEY_SLASH = GLFW.GLFW_KEY_SLASH;
	public static final int KEY_0 = GLFW.GLFW_KEY_0;
	public static final int KEY_1 = GLFW.GLFW_KEY_1;
	public static final int KEY_2 = GLFW.GLFW_KEY_2;
	public static final int KEY_3 = GLFW.GLFW_KEY_3;
	public static final int KEY_4 = GLFW.GLFW_KEY_4;
	public static final int KEY_5 = GLFW.GLFW_KEY_5;
	public static final int KEY_6 = GLFW.GLFW_KEY_6;
	public static final int KEY_7 = GLFW.GLFW_KEY_7;
	public static final int KEY_8 = GLFW.GLFW_KEY_8;
	public static final int KEY_9 = GLFW.GLFW_KEY_9;
	public static final int KEY_SEMICOLON = GLFW.GLFW_KEY_SEMICOLON;
	public static final int KEY_EQUAL = GLFW.GLFW_KEY_EQUAL;
	public static final int KEY_A = GLFW.GLFW_KEY_A;
	public static final int KEY_B = GLFW.GLFW_KEY_B;
	public static final int KEY_C = GLFW.GLFW_KEY_C;
	public static final int KEY_D = GLFW.GLFW_KEY_D;
	public static final int KEY_E = GLFW.GLFW_KEY_E;
	public static final int KEY_F = GLFW.GLFW_KEY_F;
	public static final int KEY_G = GLFW.GLFW_KEY_G;
	public static final int KEY_H = GLFW.GLFW_KEY_H;
	public static final int KEY_I = GLFW.GLFW_KEY_I;
	public static final int KEY_J = GLFW.GLFW_KEY_J;
	public static final int KEY_K = GLFW.GLFW_KEY_K;
	public static final int KEY_L = GLFW.GLFW_KEY_L;
	public static final int KEY_M = GLFW.GLFW_KEY_M;
	public static final int KEY_N = GLFW.GLFW_KEY_N;
	public static final int KEY_O = GLFW.GLFW_KEY_O;
	public static final int KEY_P = GLFW.GLFW_KEY_P;
	public static final int KEY_Q = GLFW.GLFW_KEY_Q;
	public static final int KEY_R = GLFW.GLFW_KEY_R;
	public static final int KEY_S = GLFW.GLFW_KEY_S;
	public static final int KEY_T = GLFW.GLFW_KEY_T;
	public static final int KEY_U = GLFW.GLFW_KEY_U;
	public static final int KEY_V = GLFW.GLFW_KEY_V;
	public static final int KEY_W = GLFW.GLFW_KEY_W;
	public static final int KEY_X = GLFW.GLFW_KEY_X;
	public static final int KEY_Y = GLFW.GLFW_KEY_Y;
	public static final int KEY_Z = GLFW.GLFW_KEY_Z;
	public static final int KEY_LEFT_BRACKET = GLFW.GLFW_KEY_LEFT_BRACKET;
	public static final int KEY_BACKSLASH = GLFW.GLFW_KEY_BACKSLASH;
	public static final int KEY_RIGHT_BRACKET = GLFW.GLFW_KEY_RIGHT_BRACKET;
	public static final int GLFW_KEY_GRAVE_ACCENT = GLFW.GLFW_KEY_GRAVE_ACCENT;
	public static final int KEY_WORLD_1 = GLFW.GLFW_KEY_WORLD_1;
	public static final int KEY_WORLD_2 = GLFW.GLFW_KEY_WORLD_2;
	public static final int KEY_ESCAPE = GLFW.GLFW_KEY_ESCAPE;
	public static final int KEY_ENTER = GLFW.GLFW_KEY_ENTER;
	public static final int KEY_TAB = GLFW.GLFW_KEY_TAB;
	public static final int KEY_BACKSPACE = GLFW.GLFW_KEY_BACKSPACE;
	public static final int KEY_INSERT = GLFW.GLFW_KEY_INSERT;
	public static final int KEY_DELETE = GLFW.GLFW_KEY_DELETE;
	public static final int KEY_ARROW_RIGHT = GLFW.GLFW_KEY_RIGHT;
	public static final int KEY_ARROW_LEFT = GLFW.GLFW_KEY_LEFT;
	public static final int KEY_ARROW_DOWN = GLFW.GLFW_KEY_DOWN;
	public static final int KEY_ARROW_UP = GLFW.GLFW_KEY_UP;
	public static final int KEY_PAGE_UP = GLFW.GLFW_KEY_PAGE_UP;
	public static final int KEY_PAGE_DOWN = GLFW.GLFW_KEY_PAGE_DOWN;
	public static final int KEY_HOME = GLFW.GLFW_KEY_HOME;
	public static final int KEY_END = GLFW.GLFW_KEY_END;
	public static final int KEY_CAPS_LOCK = GLFW.GLFW_KEY_CAPS_LOCK;
	public static final int KEY_SCROLL_LOCK = GLFW.GLFW_KEY_SCROLL_LOCK;
	public static final int KEY_NUM_LOCK = GLFW.GLFW_KEY_NUM_LOCK;
	public static final int KEY_PRINT = GLFW.GLFW_KEY_PRINT_SCREEN;
	public static final int KEY_PAUSE = GLFW.GLFW_KEY_PAUSE;
	public static final int KEY_F1 = GLFW.GLFW_KEY_F1;
	public static final int KEY_F2 = GLFW.GLFW_KEY_F2;
	public static final int KEY_F3 = GLFW.GLFW_KEY_F3;
	public static final int KEY_F4 = GLFW.GLFW_KEY_F4;
	public static final int KEY_F5 = GLFW.GLFW_KEY_F5;
	public static final int KEY_F6 = GLFW.GLFW_KEY_F6;
	public static final int KEY_F7 = GLFW.GLFW_KEY_F7;
	public static final int KEY_F8 = GLFW.GLFW_KEY_F8;
	public static final int KEY_F9 = GLFW.GLFW_KEY_F9;
	public static final int KEY_F10 = GLFW.GLFW_KEY_F10;
	public static final int KEY_F11 = GLFW.GLFW_KEY_F11;
	public static final int KEY_F12 = GLFW.GLFW_KEY_F12;
	public static final int KEY_F13 = GLFW.GLFW_KEY_F13;
	public static final int KEY_F14 = GLFW.GLFW_KEY_F14;
	public static final int KEY_F15 = GLFW.GLFW_KEY_F15;
	public static final int KEY_F16 = GLFW.GLFW_KEY_F16;
	public static final int KEY_F17 = GLFW.GLFW_KEY_F17;
	public static final int KEY_F18 = GLFW.GLFW_KEY_F18;
	public static final int KEY_F19 = GLFW.GLFW_KEY_F19;
	public static final int KEY_F20 = GLFW.GLFW_KEY_F20;
	public static final int KEY_F21 = GLFW.GLFW_KEY_F21;
	public static final int KEY_F22 = GLFW.GLFW_KEY_F22;
	public static final int KEY_F23 = GLFW.GLFW_KEY_F23;
	public static final int KEY_F24 = GLFW.GLFW_KEY_F24;
	public static final int KEY_F25 = GLFW.GLFW_KEY_F25;
	public static final int KEY_NUMPAD_0 = GLFW.GLFW_KEY_KP_0;
	public static final int KEY_NUMPAD_1 = GLFW.GLFW_KEY_KP_1;
	public static final int KEY_NUMPAD_2 = GLFW.GLFW_KEY_KP_2;
	public static final int KEY_NUMPAD_3 = GLFW.GLFW_KEY_KP_3;
	public static final int KEY_NUMPAD_4 = GLFW.GLFW_KEY_KP_4;
	public static final int KEY_NUMPAD_5 = GLFW.GLFW_KEY_KP_5;
	public static final int KEY_NUMPAD_6 = GLFW.GLFW_KEY_KP_6;
	public static final int KEY_NUMPAD_7 = GLFW.GLFW_KEY_KP_7;
	public static final int KEY_NUMPAD_8 = GLFW.GLFW_KEY_KP_8;
	public static final int KEY_NUMPAD_9 = GLFW.GLFW_KEY_KP_9;
	public static final int KEY_NUMPAD_DECIMAL = GLFW.GLFW_KEY_KP_DECIMAL;
	public static final int KEY_NUMPAD_DIVIDE = GLFW.GLFW_KEY_KP_DIVIDE;
	public static final int KEY_NUMPAD_MULTIPLY = GLFW.GLFW_KEY_KP_MULTIPLY;
	public static final int KEY_NUMPAD_SUBTRACT = GLFW.GLFW_KEY_KP_SUBTRACT;
	public static final int KEY_NUMPAD_ADD = GLFW.GLFW_KEY_KP_ADD;
	public static final int KEY_NUMPAD_ENTER = GLFW.GLFW_KEY_KP_ENTER;
	public static final int KEY_NUMPAD_EQUAL = GLFW.GLFW_KEY_KP_EQUAL;
	public static final int KEY_LEFT_SHIFT = GLFW.GLFW_KEY_LEFT_SHIFT;
	public static final int KEY_LEFT_CONTROL = GLFW.GLFW_KEY_LEFT_CONTROL;
	public static final int KEY_LEFT_ALT = GLFW.GLFW_KEY_LEFT_ALT;
	public static final int KEY_LEFT_SUPER = GLFW.GLFW_KEY_LEFT_SUPER;
	public static final int KEY_RIGHT_SHIFT = GLFW.GLFW_KEY_RIGHT_SHIFT;
	public static final int KEY_RIGHT_CONTROL = GLFW.GLFW_KEY_RIGHT_CONTROL;
	public static final int KEY_RIGHT_ALT = GLFW.GLFW_KEY_RIGHT_ALT;
	public static final int KEY_RIGHT_SUPER = GLFW.GLFW_KEY_RIGHT_SUPER;
	public static final int KEY_MENU = GLFW.GLFW_KEY_MENU;

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
	public static int getAxis(int positiveKey, int negativeKey) {
		boolean positive = isKeyDown(positiveKey);
		boolean negative = isKeyDown(negativeKey);
		return positive ? (negative ? 0 : 1) : (negative ? -1 : 0);
	}
}
