package hexagon.lwjgl.glfw;

import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

import hexagon.math.geometry.Size;

public final class WindowSize implements GLFWWindowSizeCallbackI {

	private static int windowWidth;
	private static int windowHeight;

	public static int width() {
		return windowWidth;
	}

	public static int height() {
		return windowHeight;
	}

	public static Size size() {
		return new Size(windowWidth, windowHeight);
	}

	public static float aspectRatio() {
		return size().ratio();
	}

	@Override
	public void invoke(long window, int width, int height) {
		windowWidth = width;
		windowHeight = height;
	}
}
