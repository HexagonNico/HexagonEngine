package hexagon.engine.core.systems;

import hexagon.engine.core.components.Camera3D;
import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.core.ecs.GameSystem;
import hexagon.engine.lwjgl.Keyboard;
import hexagon.engine.math.vector.Float3;

public final class CameraMovement extends GameSystem {

	public CameraMovement() {
		super(Camera3D.class);
	}

	@Override
	protected void beforeAll() {}

	@Override
	protected void process(GameEntity entity) {
		Camera3D camera = entity.getComponent(Camera3D.class);
		// TODO - Camera moves backwards?
		camera.position = camera.position.plus(Float3.BACK.times(Keyboard.getAxis(Keyboard.Key.W, Keyboard.Key.S) * 0.1f));
		camera.position = camera.position.plus(Float3.RIGHT.times(Keyboard.getAxis(Keyboard.Key.D, Keyboard.Key.A) * 0.1f));
		camera.position = camera.position.plus(Float3.UP.times(Keyboard.getAxis(Keyboard.Key.SPACE, Keyboard.Key.LEFT_SHIFT) * 0.1f));
		camera.pitch += Keyboard.getAxis(Keyboard.Key.UP, Keyboard.Key.DOWN) * 0.1f;
		camera.yaw += Keyboard.getAxis(Keyboard.Key.RIGHT, Keyboard.Key.LEFT) * 0.1f;
	}

	@Override
	protected void afterAll() {}
}
