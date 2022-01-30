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
		JSONObject positionJson = jsonObject.getJSONObject("position");
		JSONObject rotationJson = jsonObject.getJSONObject("rotation");
		JSONObject scaleJson = jsonObject.getJSONObject("scale");
		this.position = new Float3(positionJson.getFloat("x"), positionJson.getFloat("y"), positionJson.getFloat("z"));
		this.rotation = new Float3(rotationJson.getFloat("x"), rotationJson.getFloat("y"), rotationJson.getFloat("z"));
		this.scale = new Float3(scaleJson.getFloat("x"), scaleJson.getFloat("y"), scaleJson.getFloat("z"));
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
