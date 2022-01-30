package hexagon.engine.core;

import hexagon.engine.core.components.SpriteComponent;
import hexagon.engine.core.components.Transform2D;
import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.core.ecs.GameManager;
import hexagon.engine.core.rendering.SpriteRenderer;
import hexagon.engine.lwjgl.Engine;
import hexagon.engine.lwjgl.OpenGL;
import hexagon.engine.lwjgl.Window;
import hexagon.engine.math.vector.Float2;
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
			entity0.addComponent(new Transform2D(Float2.ZERO, Float2.ZERO, new Float2(1.0f, 2.0f)));
			entity0.addComponent(new SpriteComponent("/textures/windmill.png"));
			gameManager.addSystem(new SpriteRenderer());

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
