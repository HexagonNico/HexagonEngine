package hexagon.core;

import hexagon.core.base.GameState;
import hexagon.core.rendering.RenderingSystem;
import hexagon.lwjgl.glfw.Engine;
import hexagon.lwjgl.opengl.OpenGL;
import hexagon.utils.Log;

public class ApplicationLauncher {
	
	private long window;

	protected final Runnable init(String title, int width, int height) {
		try {
			Engine.init();
			this.window = Engine.createWindow(title, width, height);
			return this::runApplication;
		} catch (Exception any) {
			Log.error("Exception in initialization");
			any.printStackTrace();
			return this::applicationError;
		}
	}

	private void runApplication() {
		try {
			Engine.showWindow(this.window);
			GameState.loadState("/test_state.json");
			Log.info("Now running...");
			while(Engine.isRunning(this.window)) {
				Engine.update(this.window);
				GameState.update();
				RenderingSystem.renderingProcess();
			}
		} catch (Exception any) {
			Log.error("Uncaught exception in main");
			any.printStackTrace();
		} finally {
			OpenGL.cleanUp();
			Engine.terminate(this.window);
		}
	}

	private void applicationError() {
		System.out.println(":(");
	}

	public static void main(String[] args) {
		new ApplicationLauncher().init("test", 320, 180).run();
	}
}
