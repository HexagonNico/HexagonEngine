package hexagon.engine.core;

import hexagon.engine.core.ecs.GameManager;
import hexagon.engine.lwjgl.Engine;
import hexagon.engine.lwjgl.OpenGL;
import hexagon.engine.lwjgl.Window;
import hexagon.engine.utils.Log;

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
			Log.error("Uncaught exception in main thread");
			any.printStackTrace();
		} finally {
			OpenGL.cleanUp();
			Window.destroy();
			Engine.terminate();
		}
	}
}
