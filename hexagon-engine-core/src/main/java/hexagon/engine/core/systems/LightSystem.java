package hexagon.engine.core.systems;

import java.util.HashSet;

import hexagon.engine.core.components.LightComponent;
import hexagon.engine.core.components.Transform3D;
import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.core.ecs.GameManager;
import hexagon.engine.core.ecs.GameSystem;

public final class LightSystem extends GameSystem {

	public final HashSet<GameEntity> lightEntities;

	public LightSystem(GameManager gameManager) {
		super(gameManager, LightComponent.class, Transform3D.class);
		this.lightEntities = new HashSet<>();
	}

	@Override
	protected void beforeAll() {}

	@Override
	protected void process(GameEntity entity) {
		this.lightEntities.add(entity);
	}

	@Override
	protected void afterAll() {}
}
