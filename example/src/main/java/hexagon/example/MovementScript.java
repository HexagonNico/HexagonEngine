package hexagon.example;

import hexagon.core.GameEntity;
import hexagon.core.components.Script;
import hexagon.core.components.Transform2D;
import hexagon.lwjgl.glfw.Keyboard;
import hexagon.utils.json.JsonObject;

public class MovementScript extends Script {

	private Transform2D transform;
	private float speed = 5.0f;

	@Override
	public void init(JsonObject jsonObject) {
		this.speed = jsonObject.getFloat("speed", this.speed);
	}

	@Override
	public void start(GameEntity entity) {
		this.transform = entity.getComponent(Transform2D.class);
	}

	@Override
	public void update(GameEntity entity) {
		int x = Keyboard.getAxis(Keyboard.KEY_D, Keyboard.KEY_A);
		int y = Keyboard.getAxis(Keyboard.KEY_W, Keyboard.KEY_S);
		this.transform.translate(x * speed * 0.02f, y * speed * 0.02f);
	}
}
