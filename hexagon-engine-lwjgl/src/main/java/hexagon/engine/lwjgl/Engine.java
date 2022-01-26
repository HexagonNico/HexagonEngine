package hexagon.engine.lwjgl;

import java.io.PrintStream;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

public class Engine {
	
	public static void errorCallback(PrintStream stream) {
		GLFWErrorCallback.createPrint(stream).set();
	}

	public static void init() {
		if(!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
	}

	public static void configure(boolean visible, boolean resizable) {
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, visible ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
	}

	public static void createCapabilities() {
		GL.createCapabilities();
	}

	public static void terminate() {
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).set();
	}
}
