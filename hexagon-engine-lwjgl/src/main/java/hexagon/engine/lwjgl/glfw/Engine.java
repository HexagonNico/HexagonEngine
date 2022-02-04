package hexagon.engine.lwjgl.glfw;

import java.io.PrintStream;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import hexagon.engine.lwjgl.Log;

/**
 * Engine class used to initialize the engine.
 * 
 * @author Nico
 */
public class Engine {
	
	/**
	 * Setup an error callback.
	 * 
	 * @param stream Print stream for errors (e.g. {@code System.err})
	 */
	public static void errorCallback(PrintStream stream) {
		GLFWErrorCallback.createPrint(stream).set();
	}

	/**
	 * Initializes GLFW.
	 * Most GLFW functions won't work before calling this.
	 * 
	 * @throws IllegalStateException If GLFW could not be initialized
	 */
	public static void init() {
		Log.info("Initializing");
		if(!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
	}

	/**
	 * Configures GLFW.
	 * 
	 * @param visible Whether the window should be visible or stay hidden after creation.
	 * @param resizable Whether the window should be resizable.
	 */
	public static void configure(boolean visible, boolean resizable) {
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, visible ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
	}

	/**
	 * <p>
	 * 	This line is critical for LWJGL's interoperation with GLFW's OpenGL context, or any context that is managed externally.
	 * 	LWJGL detects the context that is current in the current thread, creates the GLCapabilities instance and makes the OpenGL bindings available for use.
	 * </p>
	 * Called after the initialization phase before the game loop.
	 */
	public static void createCapabilities() {
		GL.createCapabilities();
		Log.info("Created capabilities");
	}

	/**
	 * <p>
	 * 	Terminate GLFW and free the error callback.
	 * </p>
	 * Called as last line in the engine.
	 */
	public static void terminate() {
		Log.info("Terminating");
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).set();
	}
}
