package hexagon.core.systems;

import hexagon.core.GameEntity;
import hexagon.core.components.KinematicBody2D;
import hexagon.core.components.Transform2D;

public class KinematicPhysics extends GameSystem<KinematicBody2D> {

	public KinematicPhysics() {
		super(KinematicBody2D.class);
	}

	@Override
	public void process(GameEntity entity, KinematicBody2D kinematicBody, float deltaTime) {
		entity.findComponent(Transform2D.class).ifPresent(transform -> {
			transform.translate(kinematicBody.velocity().multiply(deltaTime));
		});
	}
}
