package hexagon.engine.core;

import hexagon.engine.core.ecs.GameManager;
import hexagon.engine.lwjgl.Log;
import hexagon.engine.lwjgl.glfw.Engine;
import hexagon.engine.lwjgl.glfw.Window;
import hexagon.engine.lwjgl.opengl.OpenGL;

public abstract class Application {
	
	protected GameManager gameManager;

	protected abstract void onInit();

	protected final void run() {
		try {
			Engine.errorCallback(System.err);
			Engine.init();
			Engine.configure(false, false);
			Window.makeVisible();
			Engine.createCapabilities();
			
			this.gameManager = new GameManager();
			this.onInit();

			while(!Window.shouldClose()) {
				OpenGL.clearFrame(0.5f, 0.5f, 1.0f);
				gameManager.update();
				Window.update();
			}
		} catch(Exception any) {
			Log.error("Uncaught exception in main");
			any.printStackTrace();
		} finally {
			OpenGL.cleanUp();
			Window.destroy();
			Engine.terminate();
		}
	}
}
