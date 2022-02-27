package hexagon.engine.ecs.components;

import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float3;
import hexagon.engine.utils.json.JsonObject;

public final class Transform3D extends Transform<Float3> {

	public Transform3D(GameEntity entity) {
		super(entity, Float3.ZERO, Float3.ZERO, Float3.ONE);
	}

	@Override
	protected void init(JsonObject jsonObject) {
		JsonObject positionJson = jsonObject.getObject("position").orElse(JsonObject.empty());
		JsonObject rotationJson = jsonObject.getObject("rotation").orElse(JsonObject.empty());
		JsonObject scaleJson = jsonObject.getObject("scale").orElse(JsonObject.empty());
		this.setPosition(positionJson.getFloat("x").orElse(0.0f), positionJson.getFloat("y").orElse(0.0f), positionJson.getFloat("z").orElse(0.0f));
		this.setRotation(rotationJson.getFloat("x").orElse(0.0f), rotationJson.getFloat("y").orElse(0.0f), rotationJson.getFloat("z").orElse(0.0f));
		this.setScale(scaleJson.getFloat("x").orElse(1.0f), scaleJson.getFloat("y").orElse(1.0f), scaleJson.getFloat("z").orElse(1.0f));
	}

	@Override
	public Matrix4 matrix() {
		return Matrices.transformation(this.position(), this.rotation(), this.scale());
	}

	public void setPosition(float x, float y, float z) {
		super.setPosition(new Float3(x, y, z));
	}

	public void setRotation(float x, float y, float z) {
		super.setRotation(new Float3(x, y, z));
	}

	public void setScale(float x, float y, float z) {
		super.setScale(new Float3(x, y, z));
	}

	public void translate(float x, float y, float z) {
		super.setPosition(super.localPosition().plus(x, y, z));
	}

	public void rotate(float x, float y, float z) {
		super.setRotation(super.localRotation().plus(x, y, z));
	}

	public void translate(Float3 vector) {
		this.translate(vector.x(), vector.y(), vector.z());
	}

	public void rotate(Float3 vector) {
		this.rotate(vector.x(), vector.y(), vector.z());
	}
}
