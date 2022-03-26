package hexagon.core;

import java.util.ArrayList;

import hexagon.lwjgl.opengl.OpenGL;

public final class RenderingSystem {

	private static ArrayList<Runnable> renderingProcesses = new ArrayList<>();

	public static void addRenderingProcess(Runnable runnable) {
		renderingProcesses.add(runnable);
	}

	public static void runRenderingProcess() {
		OpenGL.clearFrame(0.8f, 0.8f, 0.8f); // TODO - Set color
		renderingProcesses.forEach(Runnable::run);
	}

	private RenderingSystem() {}
}
