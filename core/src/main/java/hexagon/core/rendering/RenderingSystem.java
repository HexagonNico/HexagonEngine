package hexagon.core.rendering;

import java.util.ArrayList;

import hexagon.lwjgl.opengl.OpenGL;

public final class RenderingSystem {

	private static ArrayList<Runnable> renderers = new ArrayList<>();

	public static void addRenderingProcess(Runnable runnable) {
		renderers.add(runnable);
	}

	public static synchronized void renderingProcess() {
		OpenGL.clearFrame(0.8f, 0.8f, 0.8f); // TODO - Set color
		renderers.forEach(Runnable::run);
	}

	private RenderingSystem() {}
}
