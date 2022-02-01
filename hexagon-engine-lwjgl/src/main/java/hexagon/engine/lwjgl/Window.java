package hexagon.engine.lwjgl;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

/**
 * Window class used to manage the GLFW window.
 * 
 * @author Nico
 */
public final class Window {
	
	/**Singleton instance */
	private static final Window instance = new Window();

	/**Window handle */
	private final long window;

	/**
	 * Creates the window.
	 */
	private Window() {
		this.window = GLFW.glfwCreateWindow(800, 450, "Hello!", MemoryUtil.NULL, MemoryUtil.NULL);
		GLFW.glfwMakeContextCurrent(this.window);
		GLFW.glfwSetKeyCallback(window, new Keyboard());
	}

	// TODO - Center the window
	// TODO - Enable V-Sync

	/**
	 * Makes the window visible.
	 */
	public static void makeVisible() {
		GLFW.glfwShowWindow(instance.window);
	}

	/**
	 * Check if the window was closed.
	 * 
	 * @return True if the window was close, false otherwise.
	 */
	public static boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(instance.window);
	}

	/**
	 * <p>
	 * 	Swaps the color buffers and polls for window events.
	 * </p>
	 * Called at the end of the main loop.
	 */
	public static void update() {
		GLFW.glfwSwapBuffers(instance.window);
		GLFW.glfwPollEvents();
	}

	/**
	 * Free the window callbacks and destroy the window.
	 */
	public static void destroy() {
		Callbacks.glfwFreeCallbacks(instance.window);
		GLFW.glfwDestroyWindow(instance.window);
	}
}
