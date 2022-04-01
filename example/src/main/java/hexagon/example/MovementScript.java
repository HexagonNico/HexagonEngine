package hexagon.example;

import hexagon.core.base.GameEntity;
import hexagon.core.components.Script;
import hexagon.core.components.Transform2D;
import hexagon.lwjgl.glfw.Keyboard;
import hexagon.utils.json.JsonObject;

public class MovementScript extends Script {

	private Transform2D transform;
	private float speed = 5.0f;

	public MovementScript(JsonObject jsonObject) {

	}

	@Override
	public void update(GameEntity entity) {
		if(this.transform == null) this.transform = entity.getComponent(Transform2D.class);
		int x = Keyboard.getAxis(Keyboard.KEY_A, Keyboard.KEY_D);
		int y = Keyboard.getAxis(Keyboard.KEY_S, Keyboard.KEY_W);
		this.transform.translate(x * speed * 0.02f, y * speed * 0.02f);
	}
}
