package hexagon.engine.core;

import hexagon.engine.core.components.SpriteComponent;
import hexagon.engine.core.components.Transform3D;
import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.core.ecs.GameManager;
import hexagon.engine.core.rendering.TestRenderingSystem;
import hexagon.engine.lwjgl.Engine;
import hexagon.engine.lwjgl.OpenGL;
import hexagon.engine.lwjgl.Window;
import hexagon.engine.math.vector.Float3;
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

			GameManager gameManager = new GameManager();
			GameEntity entity0 = gameManager.createEntity();
			GameEntity entity1 = gameManager.createEntity();
			entity0.addComponent(new Transform3D());
			entity0.addComponent(new SpriteComponent("/textures/test.png"));
			entity1.addComponent(new Transform3D(new Float3(-2.0f, 0.0f, 0.0f), new Float3(0, 0, 0), new Float3(1, 1, 1)));
			entity1.addComponent(new SpriteComponent("/textures/test.png"));
			gameManager.addSystem(new TestRenderingSystem());

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

	public static void main(String[] args) {
		new Application().run();
	}
}
