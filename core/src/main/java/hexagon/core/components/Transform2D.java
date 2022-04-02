package hexagon.core.components;

import hexagon.math.matrix.Matrices;
import hexagon.math.matrix.Matrix4;
import hexagon.math.vector.Float2;
import hexagon.utils.json.JsonObject;

public final class Transform2D extends Transform {

	private Float2 position = Float2.ZERO;
	private float rotation = 0.0f;
	private Float2 scale = Float2.ONE;

	@Override
	public void init(JsonObject jsonObject) {
		jsonObject.getObject("position").ifPresent(positionJson -> {
			float x = positionJson.getFloat("x", this.position.x());
			float y = positionJson.getFloat("y", this.position.y());
			this.position = new Float2(x, y);
		});
		this.rotation = jsonObject.getFloat("rotation", 0.0f);
		jsonObject.getObject("scale").ifPresent(scaleJson -> {
			float x = scaleJson.getFloat("x", this.scale.x());
			float y = scaleJson.getFloat("y", this.scale.y());
			this.scale = new Float2(x, y);
		});
	}

	@Override
	public Matrix4 matrix() {
		return Matrices.scaling(scale)
				.multiply(Matrices.zRotationDegrees(this.rotation))
				.multiply(Matrices.translation(this.position).transposed());
	}

	public Float2 position() {
		return this.position;
	}

	public void setPosition(float x, float y) {
		this.position = new Float2(x, y);
	}

	public void setPosition(Float2 position) {
		this.position = position != null ? position : Float2.ZERO;
	}

	public void translate(float x, float y) {
		this.position = this.position.plus(x, y);
	}

	public void translate(Float2 translation) {
		this.position = this.position.plus(translation);
	}

	public float rotation() {
		return this.rotation;
	}

	public void setRotation(float degrees) {
		this.rotation = degrees;
	}

	public void rotate(float degrees) {
		this.rotation += degrees;
	}

	public Float2 scale() {
		return this.scale;
	}

	public void setScale(float x, float y) {
		this.scale = new Float2(x, y);
	}

	public void setScale(Float2 scale) {
		this.scale = scale != null ? scale : Float2.ZERO;
	}
}
