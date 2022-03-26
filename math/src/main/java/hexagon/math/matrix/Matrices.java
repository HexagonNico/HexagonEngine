package hexagon.math.matrix;

import hexagon.math.vector.Float2;
import hexagon.math.vector.Float3;

public final class Matrices {
	
	public static Matrix4 translation(Float2 translation) {
		return translation != null ? new Matrix4(
			1.0f, 0.0f, 0.0f, translation.x(),
			0.0f, 1.0f, 0.0f, translation.y(),
			0.0f, 0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		) : Matrix4.IDENTITY;
	}

	public static Matrix4 translation(Float3 translation) {
		return translation != null ? new Matrix4(
			1.0f, 0.0f, 0.0f, translation.x(),
			0.0f, 1.0f, 0.0f, translation.y(),
			0.0f, 0.0f, 1.0f, translation.z(),
			0.0f, 0.0f, 0.0f, 1.0f
		) : Matrix4.IDENTITY;
	}

	public static Matrix4 xRotationDegrees(float angle) {
		return xRotationRadians((float) Math.toRadians(angle));
	}

	public static Matrix4 xRotationRadians(float angle) {
		return new Matrix4(
			1.0f, 0.0f, 0.0f, 0.0f,
			0.0f, (float) Math.cos(-angle), (float) -Math.sin(-angle), 0.0f,
			0.0f, (float) Math.sin(-angle), (float) Math.cos(-angle), 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		);
	}

	public static Matrix4 yRotationDegrees(float angle) {
		return yRotationRadians((float) Math.toRadians(angle));
	}

	public static Matrix4 yRotationRadians(float angle) {
		return new Matrix4(
			(float) Math.cos(-angle), 0.0f, (float) Math.sin(-angle), 0.0f,
			0.0f, 1.0f, 0.0f, 0.0f,
			(float) -Math.sin(-angle), 0.0f, (float) Math.cos(-angle), 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		);
	}

	public static Matrix4 zRotationDegrees(float angle) {
		return zRotationRadians((float) Math.toRadians(angle));
	}

	public static Matrix4 zRotationRadians(float angle) {
		return new Matrix4(
			(float) Math.cos(-angle), (float) -Math.sin(-angle), 0.0f, 0.0f,
			(float) Math.sin(-angle), (float) Math.cos(-angle), 0.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		);
	}

	public static Matrix4 rotationDegrees(Float3 rotation) {
		return rotation != null ? xRotationDegrees(rotation.x())
			.multiply(yRotationDegrees(rotation.y()))
			.multiply(zRotationDegrees(rotation.z())) : Matrix4.IDENTITY;
	}

	public static Matrix4 rotationRadians(Float3 rotation) {
		return rotation != null ? xRotationRadians(rotation.x())
			.multiply(yRotationRadians(rotation.y()))
			.multiply(zRotationRadians(rotation.z())) : Matrix4.IDENTITY;
	}

	public static Matrix4 scaling(Float2 scale) {
		return scale != null ? new Matrix4(
			scale.x(), 0.0f, 0.0f, 0.0f,
			0.0f, scale.y(), 0.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		) : Matrix4.IDENTITY;
	}

	public static Matrix4 scaling(Float3 scale) {
		return scale != null ? new Matrix4(
			scale.x(), 0.0f, 0.0f, 0.0f,
			0.0f, scale.y(), 0.0f, 0.0f,
			0.0f, 0.0f, scale.z(), 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		) : Matrix4.IDENTITY;
	}

	private Matrices() {}
}
