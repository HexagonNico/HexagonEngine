package hexagon.example;

import hexagon.core.GameEntity;
import hexagon.core.components.KinematicBody2D;
import hexagon.core.components.Script;
import hexagon.lwjgl.glfw.Keyboard;
import hexagon.math.vector.Float2;
import hexagon.utils.json.JsonObject;

public class MovementScript extends Script {

	private KinematicBody2D kinematicBody;
	private float speed = 5.0f;

	@Override
	public void init(JsonObject jsonObject) {
		this.speed = jsonObject.getFloat("speed", this.speed);
	}

	@Override
	public void start(GameEntity entity) {
		this.kinematicBody = entity.getComponent(KinematicBody2D.class);
	}

	@Override
	public void update(GameEntity entity, float deltaTime) {
		int x = Keyboard.getAxis(Keyboard.KEY_D, Keyboard.KEY_A);
		int y = Keyboard.getAxis(Keyboard.KEY_W, Keyboard.KEY_S);
		this.kinematicBody.setVelocity(new Float2(x, y).multiply(this.speed));
	}
}
