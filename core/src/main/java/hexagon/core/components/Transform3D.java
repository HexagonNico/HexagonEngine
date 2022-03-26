package hexagon.core.components;

import hexagon.math.matrix.Matrices;
import hexagon.math.matrix.Matrix4;
import hexagon.math.vector.Float3;

public final class Transform3D extends Transform {

	private Float3 position;
	private Float3 rotation;
	private Float3 scale;

	public Transform3D() {
		this.position = Float3.ZERO;
		this.rotation = Float3.ZERO;
		this.scale = new Float3(1.0f, 1.0f, 1.0f);
	}

	@Override
	public Matrix4 matrix() {
		return Matrices.scaling(this.scale)
				.multiply(Matrices.rotationDegrees(this.rotation))
				.multiply(Matrices.translation(this.position).transposed());
	}

	public Float3 position() {
		return this.position;
	}

	public void setPosition(float x, float y, float z) {
		this.position = new Float3(x, y, z);
	}

	public void setPosition(Float3 position) {
		this.position = position != null ? position : Float3.ZERO;
	}

	public void translate(float x, float y, float z) {
		this.position = this.position.plus(x, y, z);
	}

	public void translate(Float3 v) {
		if(v != null) this.translate(v.x(), v.y(), v.z());
	}

	public Float3 rotation() {
		return this.rotation;
	}

	public void setRotation(float x, float y, float z) {
		this.rotation = new Float3(x, y, z);
	}

	public void setRotation(Float3 rotation) {
		this.rotation = rotation != null ? rotation : Float3.ZERO;
	}

	public void rotate(float x, float y, float z) {
		this.rotation = this.rotation.plus(x, y, z);
	}

	public void rotate(Float3 v) {
		if(v != null) this.rotate(v.x(), v.y(), v.z());
	}

	public Float3 scale() {
		return this.scale;
	}

	public void setScale(float x, float y, float z) {
		this.scale = new Float3(x, y, z);
	}

	public void setScale(Float3 scale) {
		this.scale = scale != null ? scale : Float3.ZERO;
	}
}
