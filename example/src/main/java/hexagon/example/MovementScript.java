package hexagon.example;

import hexagon.core.GameEntity;
import hexagon.core.components.Script;
import hexagon.core.components.Transform2D;
import hexagon.lwjgl.glfw.Keyboard;
import hexagon.utils.json.JsonObject;
import hexagon.utils.reflection.SerializeField;

public class MovementScript extends Script {

	private Transform2D transform;
	@SerializeField // TODO - Fix this class
	private float speed = 5.0f;

	@Override
	public void init(JsonObject jsonObject) {
		this.speed = jsonObject.getFloat("speed", this.speed);
	}

	@Override
	public void start(GameEntity entity) {
		this.transform = entity.getComponent(Transform2D.class);
		System.out.println(this.speed);
	}

	@Override
	public void update(GameEntity entity, float deltaTime) {
		int x = Keyboard.getAxis(Keyboard.KEY_D, Keyboard.KEY_A);
		int y = Keyboard.getAxis(Keyboard.KEY_W, Keyboard.KEY_S);
		this.transform.translate(x * this.speed * deltaTime, y * this.speed * deltaTime);
	}
}
