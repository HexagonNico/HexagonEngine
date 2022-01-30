package hexagon.engine.core.components;

import org.json.JSONObject;

import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float3;

public final class Transform3D {
	
	public Float3 position;
	public Float3 rotation;
	public Float3 scale;

	public Transform3D(JSONObject jsonObject) {
		JSONObject positionJson = jsonObject.optJSONObject("position", new JSONObject());
		JSONObject rotationJson = jsonObject.optJSONObject("rotation", new JSONObject());
		JSONObject scaleJson = jsonObject.optJSONObject("scale", new JSONObject());
		this.position = new Float3(positionJson.optFloat("x", 0.0f), positionJson.optFloat("y", 0.0f), positionJson.optFloat("z", 0.0f));
		this.rotation = new Float3(rotationJson.optFloat("x", 0.0f), rotationJson.optFloat("y", 0.0f), rotationJson.optFloat("z", 0.0f));
		this.scale = new Float3(scaleJson.optFloat("x", 1.0f), scaleJson.optFloat("y", 1.0f), scaleJson.optFloat("z", 1.0f));
	}

	public Transform3D() {
		this(Float3.ZERO, Float3.ZERO, Float3.ONE);
	}

	public Transform3D(Float3 position, Float3 rotation, Float3 scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public Matrix4 matrix() {
		return Matrices.transformation(this.position, this.rotation, this.scale);
	}
}
