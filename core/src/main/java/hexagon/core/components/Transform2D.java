package hexagon.core.components;

import hexagon.core.base.GameEntity;
import hexagon.math.matrix.Matrices;
import hexagon.math.matrix.Matrix4;
import hexagon.math.vector.Float2;

public final class Transform2D extends Transform {

	private Float2 position;
	private float rotation;
	private Float2 scale;

	public Transform2D(GameEntity entity) {
		super(entity);
		this.position = Float2.ZERO;
		this.rotation = 0.0f;
		this.scale = new Float2(1.0f, 1.0f);
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

	public void translate(Float2 v) {
		if(v != null) this.translate(v.x(), v.y());
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
