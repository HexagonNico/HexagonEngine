package hexagon.engine.core.systems;

import java.util.HashSet;

import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.core.ecs.GameManager;

public final class LightSystem {

	// TODO - Fix this

	public final HashSet<GameEntity> lightEntities;

	public LightSystem(GameManager gameManager) {
		this.lightEntities = new HashSet<>();
	}

	protected void beforeAll() {}

	protected void process(GameEntity entity) {
		this.lightEntities.add(entity);
	}

	protected void afterAll() {}
}
