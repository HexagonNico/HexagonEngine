package hexagon.engine.core;

import hexagon.engine.core.ecs.GameManager;
import hexagon.engine.lwjgl.Log;
import hexagon.engine.lwjgl.glfw.Engine;
import hexagon.engine.lwjgl.opengl.OpenGL;
import hexagon.engine.utils.json.JsonObject;

public abstract class Application {
	
	private long window;
	protected final GameManager gameManager = new GameManager();

	protected Application() {
		try {
			Engine.init();
			JsonObject settingsJson = JsonObject.fromFileOrEmpty("/settings.json");
			JsonObject windowJson = settingsJson.getObject("window").orElse(JsonObject.empty());
			Engine.windowVisible(windowJson.getBoolean("visible").orElse(false));
			Engine.windowResizable(windowJson.getBoolean("resizable").orElse(false));
			this.window = Engine.createWindow(
				windowJson.getString("title").orElse("untitled"),
				windowJson.getInt("width").orElse(300),
				windowJson.getInt("height").orElse(300)
			);
		} catch (Exception any) {
			Log.error("Error in initialization");
			any.printStackTrace();
		}
	}

	protected abstract void onInit();

	protected abstract void onUpdate();

	protected final void run() {
		try {
			Engine.showWindow(this.window);
			this.onInit();

			while(Engine.running(this.window)) {
				this.onUpdate();
				this.gameManager.update();
				Engine.update(this.window);
			}
		} catch (Exception any) {
			Log.error("Uncaught exception in main");
			any.printStackTrace();
		} finally {
			OpenGL.cleanUp();
			Engine.terminate(this.window);
		}
	}
}
