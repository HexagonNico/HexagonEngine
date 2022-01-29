package hexagon.engine.core;

import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.core.ecs.GameManager;
import hexagon.engine.core.ecs.TestComponent;
import hexagon.engine.core.rendering.TestRenderer;
import hexagon.engine.lwjgl.Engine;
import hexagon.engine.lwjgl.OpenGL;
import hexagon.engine.lwjgl.Window;
import hexagon.engine.utils.Log;

public class Application {
	
	protected final void run() {
		try {
			Engine.errorCallback(System.err);
			Engine.init();
			Engine.configure(false, false);
			Window.makeVisible();
			Engine.createCapabilities();

			OpenGL.depthTest(true);

			TestRenderer renderer = new TestRenderer();
			GameManager gameManager = new GameManager();
			GameEntity testEntity = gameManager.createEntity();
			GameEntity testEntity2 = gameManager.createEntity();
			gameManager.addComponent(testEntity, new TestComponent("pog"));
			gameManager.addComponent(testEntity2, new TestComponent("pog2"));
			gameManager.addComponent(testEntity2, new TestComponent("pog?"));

			while(!Window.shouldClose()) {
				OpenGL.clearFrame(0.5f, 0.5f, 1.0f);
				renderer.render();
				gameManager.test();
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

	public static void main(String[] args) {
		new Application().run();
	}
}
