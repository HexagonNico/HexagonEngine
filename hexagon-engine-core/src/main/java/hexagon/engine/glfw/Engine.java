package hexagon.engine.glfw;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.MemoryUtil;

import hexagon.engine.Log;

/**
 * Class that wraps GLFW function used in initialization phase and to handle the GLFW window.
 * 
 * @author Nico
 */
public final class Engine {

	/**
	 * Sets the error callback to {@code System.err} and initializes GLFW.
	 * Other functions in this class will not work before calling this.
	 * <p>
	 * 	This code cannot be put in a static block
	 * 	or possible exceptions could not be handled.
	 * </p>
	 * 
	 * @throws IllegalStateException If the initialization fails.
	 */
	public static void init() {
		Log.info("Initializing");
		GLFWErrorCallback.createPrint(System.err).set();
		if(!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
	}

	/**
	 * Sets the "visible" window hint.
	 * 
	 * @param visible If false, the window will stay hidden after creation, if true it will be visible.
	 */
	public static void windowVisible(boolean visible) {
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, visible ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
	}

	/**
	 * Sets the "resizable" window hint.
	 * 
	 * @param resizable Whether the window should be resizable.
	 */
	public static void windowResizable(boolean resizable) {
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
	}

	/**
	 * Creates the GLFW window and returns the window handle.
	 * 
	 * @param title Title of the window.
	 * @param width Width of the window when created.
	 * @param height Height of the window when created.
	 * 
	 * @return The window handle, needed to call other window-related functions.
	 * 
	 * @throws RuntimeException If the creation of the window fails.
	 */
	public static long createWindow(String title, int width, int height) {
		Log.info("Creating window");
		long window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
		if(window == MemoryUtil.NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		GLFW.glfwSetKeyCallback(window, new Keyboard());
		GLFW.glfwSetWindowSizeCallback(window, new WindowSize(width, height));
		return window;
	}

	// TODO - Center the window

	/**
	 * Makes the OpenGL context current to the current thread and makes the window visible.
	 * 
	 * @param window The window handle.
	 */
	public static void showWindow(long window) {
		GLFW.glfwMakeContextCurrent(window);
		// TODO - glfwSwapInterval(1);
		GLFW.glfwShowWindow(window);
	}

	/**
	 * Can be used in main game loop to check whether the engine is running.
	 * 
	 * @param window The window handle.
	 * 
	 * @return True if the engine is running, false if the window should be closed.
	 */
	public static boolean running(long window) {
		return !GLFW.glfwWindowShouldClose(window);
	}

	/**
	 * Updates the engine every frame.
	 * Swaps the color buffers and polls for window events.
	 * 
	 * @param window The window handle.
	 */
	public static void update(long window) {
		GLFW.glfwSwapBuffers(window);
		GLFW.glfwPollEvents();
	}

	/**
	 * Terminates the engine.
	 * Frees the callbacks and destroys the window.
	 * 
	 * @param window The window handle.
	 */
	public static void terminate(long window) {
		Log.info("Terminating");
		Callbacks.glfwFreeCallbacks(window);
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).set();
	}

	/**Class should not be instantiated */
	private Engine() {}
}
