package hexagon.engine.core.components;

import org.json.JSONObject;

import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float2;
import hexagon.engine.math.vector.Float3;

public final class Transform2D {
	
	public Float2 position;
	public Float2 rotation;
	public Float2 scale;

	public Transform2D(JSONObject jsonObject) {
		JSONObject positionJson = jsonObject.optJSONObject("position", new JSONObject());
		JSONObject rotationJson = jsonObject.optJSONObject("rotation", new JSONObject());
		JSONObject scaleJson = jsonObject.optJSONObject("scale", new JSONObject());
		this.position = new Float2(positionJson.optFloat("x", 0.0f), positionJson.optFloat("y", 0.0f));
		this.rotation = new Float2(rotationJson.optFloat("x", 0.0f), rotationJson.optFloat("y", 0.0f));
		this.scale = new Float2(scaleJson.optFloat("x", 1.0f), scaleJson.optFloat("y", 1.0f));
	}

	public Transform2D() {
		this(Float2.ZERO, Float2.ZERO, Float2.ONE);
	}

	public Transform2D(Float2 position, Float2 rotation, Float2 scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public Matrix4 matrix() {
		return Matrices.transformation(
			new Float3(this.position.x(), this.position.y(), 0.0f),
			new Float3(this.rotation.x(), this.rotation.y(), 0.0f),
			new Float3(this.scale.x(), this.scale.y(), 1.0f)
		);
	}
}
