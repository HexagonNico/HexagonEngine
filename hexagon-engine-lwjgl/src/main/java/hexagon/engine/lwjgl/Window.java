package hexagon.engine.lwjgl;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

public class Window {
	
	private static final Window instance = new Window();

	private final long window;

	private Window() {
		this.window = GLFW.glfwCreateWindow(800, 300, "Hello!", MemoryUtil.NULL, MemoryUtil.NULL);
		GLFW.glfwMakeContextCurrent(this.window);
	}

	public static void makeVisible() {
		GLFW.glfwShowWindow(instance.window);
	}

	public static boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(instance.window);
	}

	public static void update() {
		GLFW.glfwSwapBuffers(instance.window);
		GLFW.glfwPollEvents();
	}

	public static void destroy() {
		Callbacks.glfwFreeCallbacks(instance.window);
		GLFW.glfwDestroyWindow(instance.window);
	}
}
