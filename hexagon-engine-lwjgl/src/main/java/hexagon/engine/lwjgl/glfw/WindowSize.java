package hexagon.engine.lwjgl.glfw;

import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

/**
 * Window resize callback.
 * Invoked every time the window is resized to get its size.
 * 
 * @author Nico
 */
public final class WindowSize implements GLFWWindowSizeCallbackI {

	/**Singleton instance */
	private static WindowSize instance;

	/**
	 * Get the width of the window.
	 * 
	 * @return Width of the window.
	 */
	public static int width() {
		return instance != null ? instance.width : 0;
	}

	/**
	 * Get the height of the window.
	 * 
	 * @return Height of the window.
	 */
	public static int height() {
		return instance != null ? instance.height : 0;
	}

	/**
	 * Get the aspect ratio of the window.
	 * 
	 * @return Width / height.
	 */
	public static float aspectRatio() {
		return instance != null ? (float) instance.width / instance.height : 0;
	}

	/**Holds window width */
	private int width;
	/**Holds window height */
	private int height;

	/**
	 * Creates window size callback.
	 * 
	 * @param width Initial window width.
	 * @param height Initial window size.
	 */
	protected WindowSize(int width, int height) {
		if(instance != null) {
			throw new IllegalStateException("Window size callback already exists");
		} else {
			this.width = width;
			this.height = height;
			instance = this;
		}
	}

	@Override
	public void invoke(long window, int width, int height) {
		this.width = width;
		this.height = height;
	}
}
