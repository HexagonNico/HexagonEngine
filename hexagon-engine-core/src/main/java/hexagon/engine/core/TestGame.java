package hexagon.engine.core;

import hexagon.engine.core.ecs.SceneLoader;
import hexagon.engine.core.systems.SpriteRenderer;
import hexagon.engine.lwjgl.OpenGL;

public final class TestGame extends Application {

	@Override
	protected void onInit() {
		OpenGL.alphaBlending(true);
		OpenGL.cullFace(true);
		OpenGL.depthTest(true);
		SceneLoader.loadScene("/scenes/test.json", this.gameManager);
		this.gameManager.addSystem(new SpriteRenderer());
	}
	
	public static void main(String[] args) {
		new TestGame().run();
	}
}
