package hexagon.core.rendering;

import java.util.ArrayList;

import hexagon.lwjgl.opengl.OpenGL;

/**
 * Class used internally to run rendering systems on the main thread.
 * 
 * @author Nico
 */
public final class RenderingSystem {

	/**Rendering processing for the different rendering systems */
	private static ArrayList<Runnable> renderers = new ArrayList<>();

	/**
	 * Adds a process that will be executed during the rendering phase.
	 * 
	 * @param runnable Action to execute
	 */
	public static void addRenderingProcess(Runnable runnable) {
		renderers.add(runnable);
	}

	/**
	 * Runs the rendering process.
	 * Called from the main application method.
	 */
	public static synchronized void renderingProcess() {
		OpenGL.clearFrame(0.8f, 0.8f, 0.8f); // TODO - Set color
		renderers.forEach(Runnable::run);
	}

	/**Class should not be instantiated */
	private RenderingSystem() {}
}
