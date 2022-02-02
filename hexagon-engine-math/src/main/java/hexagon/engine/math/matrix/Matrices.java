package hexagon.engine.math.matrix;

import hexagon.engine.math.vector.Float3;

public final class Matrices {

	public static Matrix4 transformation(Float3 position, Float3 rotation, Float3 scale) {
		return scaling(scale)
			.multiply(rotation(rotation))
			.multiply(translation(position).transposed());
	}

	public static Matrix4 translation(Float3 translation) {
		return new Matrix4(
			1.0f, 0.0f, 0.0f, translation.x(),
			0.0f, 1.0f, 0.0f, translation.y(),
			0.0f, 0.0f, 1.0f, translation.z(),
			0.0f, 0.0f, 0.0f, 1.0f
		);
	}

	public static Matrix4 xRotation(float angle) {
		return new Matrix4(
			1.0f, 0.0f, 0.0f, 0.0f,
			0.0f, (float) Math.cos(-angle), (float) -Math.sin(-angle), 0.0f,
			0.0f, (float) Math.sin(-angle), (float) Math.cos(-angle), 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		);
	}

	public static Matrix4 yRotation(float angle) {
		return new Matrix4(
			(float) Math.cos(-angle), 0.0f, (float) Math.sin(-angle), 0.0f,
			0.0f, 1.0f, 0.0f, 0.0f,
			(float) -Math.sin(-angle), 0.0f, (float) Math.cos(-angle), 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		);
	}

	public static Matrix4 zRotation(float angle) {
		return new Matrix4(
			(float) Math.cos(-angle), (float) -Math.sin(-angle), 0.0f, 0.0f,
			(float) Math.sin(-angle), (float) Math.cos(-angle), 0.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		);
	}

	public static Matrix4 rotation(Float3 rotation) {
		return xRotation((float) Math.toRadians(rotation.x()))
			.multiply(yRotation((float) Math.toRadians(rotation.y())))
			.multiply(zRotation((float) Math.toRadians(rotation.z())));
	}

	public static Matrix4 scaling(Float3 scale) {
		return new Matrix4(
			scale.x(), 0.0f, 0.0f, 0.0f,
			0.0f, scale.y(), 0.0f, 0.0f,
			0.0f, 0.0f, scale.z(), 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		);
	}

	public static Matrix4 projection(float fov, float near, float far) {
		float m00 = 1.0f / (float) Math.tan(Math.toRadians(fov / 2.0f));
		float m11 = m00 * (800 / 450); // TODO - Aspect ratio
		float m22 = -(far + near) / (far - near);
		float m32 = -(2 * far * near) / (far - near);
		return new Matrix4(
			m00, 0.0f, 0.0f, 0.0f,
			0.0f, m11, 0.0f, 0.0f,
			0.0f, 0.0f, m22, -1.0f,
			0.0f, 0.0f, m32, 0.0f
		);
	}

	public static Matrix4 view(Float3 cameraPosition, float pitch, float yaw) {
		return translation(cameraPosition.negative()).transposed()
			.multiply(xRotation((float) Math.toRadians(pitch)))
			.multiply(yRotation((float) Math.toRadians(yaw)));
	}
}
