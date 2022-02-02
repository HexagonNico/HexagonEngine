package hexagon.engine.core;

import hexagon.engine.core.components.SpriteComponent;
import hexagon.engine.core.components.Transform2D;
import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.lwjgl.OpenGL;

public final class TestGame extends Application {

	@Override
	protected void onInit() {
		OpenGL.alphaBlending(false);
		OpenGL.cullFace(true);
		OpenGL.depthTest(true);
		GameEntity entity = this.gameManager.createEntity();
		entity.addComponent(new Transform2D());
		GameEntity entity2 = this.gameManager.createEntity();
		entity2.addComponent(new Transform2D());
		entity2.addComponent(new SpriteComponent("/textures/building.png"));
		this.gameManager.removeEntity(entity2);
		GameEntity entity3 = this.gameManager.createEntity();
		entity3.addComponent(new Transform2D());
	}
	
	public static void main(String[] args) {
		new TestGame().run();
	}
}
