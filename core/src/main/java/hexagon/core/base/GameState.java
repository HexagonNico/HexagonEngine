package hexagon.core.base;

import java.util.ArrayList;

import hexagon.core.components.SpriteComponent;
import hexagon.core.components.Transform2D;
import hexagon.core.scripts.SpriteRenderer;

public final class GameState {
	
	private static GameState currentState = new GameState();

	public static void update() {
		currentState.entities.forEach(GameEntity::update);
	}

	private ArrayList<GameEntity> entities = new ArrayList<>();

	public GameState() {
		// TODO - Load from json instead of this
		GameEntity entity = this.createEntity();
		entity.addComponent(new Transform2D());
		entity.addComponent(new SpriteComponent());
		entity.addScript(new SpriteRenderer(entity));
	}

	public GameEntity createEntity() {
		GameEntity entity = new GameEntity();
		this.entities.add(entity);
		return entity;
	}
}
