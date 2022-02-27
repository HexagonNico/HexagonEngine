package hexagon.engine.ecs.components;

import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float2;
import hexagon.engine.math.vector.Float3;
import hexagon.engine.utils.json.JsonObject;

public final class Transform2D extends Transform<Float2> {

	public Transform2D(GameEntity entity) {
		super(entity, Float2.ZERO, Float2.ZERO, Float2.ONE);
	}

	@Override
	protected void init(JsonObject jsonObject) {
		JsonObject positionJson = jsonObject.getObject("position").orElse(JsonObject.empty());
		JsonObject rotationJson = jsonObject.getObject("rotation").orElse(JsonObject.empty());
		JsonObject scaleJson = jsonObject.getObject("scale").orElse(JsonObject.empty());
		this.setPosition(positionJson.getFloat("x").orElse(0.0f), positionJson.getFloat("y").orElse(0.0f));
		this.setRotation(rotationJson.getFloat("x").orElse(0.0f), rotationJson.getFloat("y").orElse(0.0f));
		this.setScale(scaleJson.getFloat("x").orElse(1.0f), scaleJson.getFloat("y").orElse(1.0f));
	}

	@Override
	public Matrix4 matrix() {
		return Matrices.transformation(
			new Float3(super.position().x(), super.position().y(), 0.0f),
			new Float3(super.rotation().x(), super.rotation().y(), 0.0f),
			new Float3(super.scale().x(), super.scale().y(), 1.0f)
		);
	}

	public void setPosition(float x, float y) {
		super.setPosition(new Float2(x, y));
	}

	public void setRotation(float x, float y) {
		super.setRotation(new Float2(x, y));
	}

	public void setScale(float x, float y) {
		super.setScale(new Float2(x, y));
	}

	public void translate(float x, float y) {
		super.setPosition(super.localPosition().plus(x, y));
	}

	public void rotate(float x, float y) {
		super.setRotation(super.localRotation().plus(x, y));
	}

	public void translate(Float2 vector) {
		this.translate(vector.x(), vector.y());
	}

	public void rotate(Float2 vector) {
		this.rotate(vector.x(), vector.y());
	}
}
