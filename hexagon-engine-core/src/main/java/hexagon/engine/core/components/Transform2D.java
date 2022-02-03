package hexagon.engine.core.components;

import hexagon.engine.core.ecs.Component;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float2;
import hexagon.engine.math.vector.Float3;
import hexagon.engine.utils.json.JsonObject;

public class Transform2D extends Component {
	
	public Float2 position;
	public Float2 rotation;
	public Float2 scale;

	public Transform2D(JsonObject jsonObject) {
		JsonObject positionJson = jsonObject.getObject("position").orElse(JsonObject.empty());
		JsonObject rotationJson = jsonObject.getObject("rotation").orElse(JsonObject.empty());
		JsonObject scaleJson = jsonObject.getObject("scale").orElse(JsonObject.empty());
		float x = positionJson.getFloat("x").orElse(0.0f);
		float y = positionJson.getFloat("y").orElse(0.0f);
		this.position = new Float2(x, y);
		x = rotationJson.getFloat("x").orElse(0.0f);
		y = rotationJson.getFloat("y").orElse(0.0f);
		this.rotation = new Float2(x, y);
		x = scaleJson.getFloat("x").orElse(1.0f);
		y = scaleJson.getFloat("y").orElse(1.0f);
		this.scale = new Float2(x, y);
	}

	public Transform2D() {
		this(Float2.ZERO, Float2.ZERO, Float2.ONE);
	}

	public Transform2D(Float2 position, Float2 rotation, Float2 scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public Matrix4 transformationMatrix() {
		return Matrices.transformation(
			new Float3(this.position.x(), this.position.y(), 0.0f),
			new Float3(this.rotation.x(), this.rotation.y(), 0.0f),
			new Float3(this.scale.x(), this.scale.y(), 1.0f)
		);
	}
}
