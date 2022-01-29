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
			GameEntity entity0 = gameManager.createEntity();
			GameEntity entity1 = gameManager.createEntity();
			entity0.addComponent(new TestComponent("test1"));
			entity1.addComponent(new TestComponent("test2"));
			gameManager.addSystem(new TestSystem());

			while(!Window.shouldClose()) {
				OpenGL.clearFrame(0.5f, 0.5f, 1.0f);
				renderer.render();
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

	public static void main(String[] args) {
		new Application().run();
	}
}
