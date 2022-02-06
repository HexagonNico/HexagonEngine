package hexagon.engine.core;

import hexagon.engine.core.ecs.GameManager;
import hexagon.engine.lwjgl.Log;
import hexagon.engine.lwjgl.glfw.Engine;
import hexagon.engine.lwjgl.opengl.OpenGL;

public abstract class Application {
	
	private long window;
	protected GameManager gameManager;

	protected Application() {
		try {
			Engine.init();
			Engine.windowResizable(false);
			Engine.windowVisible(false);
			this.window = Engine.createWindow("Hello", 800, 450);
		} catch (Exception any) {
			Log.error("Uncaught exception in main");
			any.printStackTrace();
			Engine.terminate(this.window);
		}
	}

	protected abstract void onInit();

	protected final void run() {
		try {
			Engine.showWindow(this.window);
			this.gameManager = new GameManager();
			this.onInit();

			while(Engine.running(this.window)) {
				OpenGL.clearFrame(0.5f, 0.5f, 1.0f);
				gameManager.update();
				Engine.update(this.window);
			}
		} catch(Exception any) {
			Log.error("Uncaught exception in main");
			any.printStackTrace();
		} finally {
			OpenGL.cleanUp();
			Engine.terminate(this.window);
		}
	}
}
