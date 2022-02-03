package hexagon.engine.core;

import hexagon.engine.core.components.SpriteComponent;
import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.core.rendering.SpriteRenderer;
import hexagon.engine.lwjgl.OpenGL;

public final class TestGame extends Application {

	@Override
	protected void onInit() {
		OpenGL.alphaBlending(true);
		OpenGL.cullFace(true);
		OpenGL.depthTest(true);
		GameEntity entity = this.gameManager.createEntity();
		entity.addComponent(new SpriteComponent("/textures/windmill.png"));
		this.gameManager.addSystem(new SpriteRenderer());
	}
	
	public static void main(String[] args) {
		new TestGame().run();
	}
}
