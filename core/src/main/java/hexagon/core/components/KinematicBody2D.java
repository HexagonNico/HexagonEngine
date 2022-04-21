package hexagon.core.components;

import hexagon.math.vector.Float2;
import hexagon.utils.json.JsonObject;

public class KinematicBody2D extends PhysicsBody2D {

	private Float2 velocity = Float2.ZERO;

	@Override
	public void init(JsonObject jsonObject) {
		super.init(jsonObject);
		jsonObject.getObject("velocity").ifPresent(velocityJson -> {
			float x = velocityJson.getFloat("x", this.velocity.x());
			float y = velocityJson.getFloat("y", this.velocity.y());
			this.velocity = new Float2(x, y);
		});
	}

	public Float2 velocity() {
		return this.velocity;
	}

	public void setVelocity(Float2 velocity) {
		this.velocity = velocity != null ? velocity : Float2.ZERO;
	}
}
