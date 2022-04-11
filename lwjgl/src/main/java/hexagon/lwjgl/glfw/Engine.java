package hexagon.lwjgl.glfw;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.MemoryUtil;

import hexagon.utils.Log;

public class Engine {
	
	public static void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		if(!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		Log.info("GLFW initialized");
	}

	// TODO - Window hints

	public static long createWindow(String title, int width, int height) {
		long window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
		if(window == MemoryUtil.NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		GLFW.glfwSetKeyCallback(window, new Keyboard());
		GLFW.glfwSetWindowSizeCallback(window, new WindowSize(width, height));
		Log.info("Created window");
		return window;
	}

	// TODO - Center the window

	public static void showWindow(long window) {
		GLFW.glfwMakeContextCurrent(window);
		// TODO - glfwSwapInterval(1);
		GLFW.glfwShowWindow(window);
	}

	public static boolean isRunning(long window) {
		return !GLFW.glfwWindowShouldClose(window);
	}

	public static void update(long window) {
		GLFW.glfwSwapBuffers(window);
		GLFW.glfwPollEvents();
	}

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
