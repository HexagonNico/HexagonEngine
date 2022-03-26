package hexagon.core.base;

import java.util.ArrayList;

import hexagon.core.components.SpriteComponent;
import hexagon.core.components.Transform2D;

public final class GameState {
	
	private static GameState currentState = new GameState();

	public static void update() {
		currentState.entities.forEach(GameEntity::update);
	}

	private ArrayList<GameEntity> entities = new ArrayList<>();

	public GameState() {
		// TODO - Load from json instead of this
		GameEntity entity = this.createEntity();
		Transform2D transform2d = new Transform2D(entity);
		transform2d.setPosition(0.3f, 0.3f);
		entity.addComponent(transform2d);
		entity.addComponent(new SpriteComponent(entity));
	}

	public GameEntity createEntity() {
		GameEntity entity = new GameEntity();
		this.entities.add(entity);
		return entity;
	}
}
