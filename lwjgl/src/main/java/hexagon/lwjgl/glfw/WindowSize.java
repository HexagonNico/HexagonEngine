package hexagon.lwjgl.glfw;

import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

import hexagon.math.geometry.SizeInt;

public final class WindowSize implements GLFWWindowSizeCallbackI {

	private static WindowSize singleton;

	public static int width() {
		return singleton.width;
	}

	public static int height() {
		return singleton.height;
	}

	public static SizeInt size() {
		return new SizeInt(width(), height());
	}

	public static float aspectRatio() {
		return size().ratio();
	}

	private int width;
	private int height;

	protected WindowSize(int width, int height) {
		if(singleton != null) {
			throw new IllegalStateException("WindowSize is a singleton class");
		} else {
			singleton = this;
			this.width = width;
			this.height = height;
		}
	}

	@Override
	public void invoke(long window, int width, int height) {
		this.width = width;
		this.height = height;
	}
}
