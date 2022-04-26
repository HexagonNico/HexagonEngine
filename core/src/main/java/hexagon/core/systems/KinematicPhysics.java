package hexagon.core.systems;

import java.util.HashMap;

import hexagon.core.GameEntity;
import hexagon.core.components.KinematicBody2D;
import hexagon.core.components.PhysicsBody2D;
import hexagon.core.components.Transform2D;
import hexagon.core.components.TriggerArea;
import hexagon.math.geometry.Rectangle;
import hexagon.math.geometry.Size;

public class KinematicPhysics extends GameSystem<KinematicBody2D> {

	public KinematicPhysics() {
		super(KinematicBody2D.class);
	}

	@Override
	public void process(GameEntity entity, KinematicBody2D kinematicBody, float deltaTime) {
		entity.findComponent(Transform2D.class).ifPresent(transform -> {
			HashMap<GameEntity, TriggerArea> triggersToCheck = getNonCollidingTriggers(entity, transform, kinematicBody);
			transform.translate(kinematicBody.velocity().multiply(deltaTime));
			resolveCollisions(entity, transform, kinematicBody);
			checkTriggers(triggersToCheck, entity, kinematicBody, transform);
		});
	}

	private static void resolveCollisions(GameEntity entity, Transform2D transform, KinematicBody2D kinematicBody) {
		entity.state.getComponents(PhysicsBody2D.class).forEach((otherEntity, otherCollider) -> {
			otherEntity.findComponent(Transform2D.class).ifPresent(otherTransform -> {
				Rectangle shape1 = kinematicBody.collisionShape().translated(transform.position());
				Rectangle shape2 = otherCollider.collisionShape().translated(otherTransform.position());
				if(shape1.intersects(shape2)) {
					Size intersection = shape1.intersection(shape2).size();
					if(intersection.width() > intersection.height()) {
						if(shape1.top() > shape2.top()) {
							transform.translate(0.0f, intersection.height());
						} else {
							transform.translate(0.0f, -intersection.height());
						}
					} else {
						if(shape1.left() < shape2.left()) {
							transform.translate(-intersection.width(), 0.0f);
						} else {
							transform.translate(intersection.width(), 0.0f);
						}
					}
				}
			});
		});
	}

	private static HashMap<GameEntity, TriggerArea> getNonCollidingTriggers(GameEntity entity, Transform2D transform, KinematicBody2D kinematicBody) {
		HashMap<GameEntity, TriggerArea> triggers = new HashMap<>();
		entity.state.getComponents(TriggerArea.class).forEach((otherEntity, trigger) -> {
			otherEntity.findComponent(Transform2D.class).ifPresent(otherTransform -> {
				Rectangle shape1 = kinematicBody.collisionShape().translated(transform.position());
				Rectangle shape2 = trigger.shape().translated(otherTransform.position());
				if(!shape1.intersects(shape2)) {
					triggers.put(otherEntity, trigger);
				}
			});
		});
		return triggers;
	}

	private static void checkTriggers(HashMap<GameEntity, TriggerArea> triggers, GameEntity entity, KinematicBody2D kinematicBody, Transform2D transform) {
		triggers.forEach((otherEntity, trigger) -> {
			otherEntity.findComponent(Transform2D.class).ifPresent(otherTransform -> {
				Rectangle shape1 = kinematicBody.collisionShape().translated(transform.position());
				Rectangle shape2 = trigger.shape().translated(otherTransform.position());
				if(shape1.intersects(shape2)) {
					trigger.onBodyEntered(otherEntity, entity);
				}
			});
		});
	}
}
