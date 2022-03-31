package hexagon.example;

import hexagon.core.base.GameEntity;
import hexagon.core.base.Script;
import hexagon.core.components.Transform2D;
import hexagon.lwjgl.glfw.Keyboard;

public class MovementScript extends Script {

	private Transform2D transform;
	private float speed = 5.0f;

	public MovementScript(GameEntity entity) {
		super(entity);
		this.transform = entity.getComponent(Transform2D.class);
	}

	@Override
	public void update() {
		int x = Keyboard.getAxis(Keyboard.KEY_A, Keyboard.KEY_D);
		int y = Keyboard.getAxis(Keyboard.KEY_S, Keyboard.KEY_W);
		this.transform.translate(x * speed * Script.PERIOD, y * speed * Script.PERIOD);
	}
}
