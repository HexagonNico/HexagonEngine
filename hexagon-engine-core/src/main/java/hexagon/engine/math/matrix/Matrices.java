package hexagon.engine.math.matrix;

import hexagon.engine.math.vector.Float3;

/**
 * Utility class that contains useful methods for matrices
 * 
 * @author Nico
 */
public final class Matrices {

	/**
	 * Creates a transformation matrix.
	 * 
	 * @param translation Translation vector
	 * @param rotation Rotation vector
	 * @param scale Scale vector
	 * 
	 * @return The transformation matrix
	 */
	public static Matrix4 transformation(Float3 translation, Float3 rotation, Float3 scale) {
		return scaling(scale)
			.multiply(rotation(rotation))
			.multiply(translation(translation).transposed());
	}

	/**
	 * Creates a translation matrix.
	 * 
	 * @param translation Translation vector
	 * 
	 * @return The translation matrix
	 */
	public static Matrix4 translation(Float3 translation) {
		return new Matrix4(
			1.0f, 0.0f, 0.0f, translation.x(),
			0.0f, 1.0f, 0.0f, translation.y(),
			0.0f, 0.0f, 1.0f, translation.z(),
			0.0f, 0.0f, 0.0f, 1.0f
		);
	}

	/**
	 * Creates a rotation matrix for the x axis.
	 * 
	 * @param angle Rotation angle in radians
	 * 
	 * @return The rotation matrix
	 */
	public static Matrix4 xRotation(float angle) {
		return new Matrix4(
			1.0f, 0.0f, 0.0f, 0.0f,
			0.0f, (float) Math.cos(-angle), (float) -Math.sin(-angle), 0.0f,
			0.0f, (float) Math.sin(-angle), (float) Math.cos(-angle), 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		);
	}

	/**
	 * Creates a rotation matrix for the y axis.
	 * 
	 * @param angle Rotation angle in radians
	 * 
	 * @return The rotation matrix
	 */
	public static Matrix4 yRotation(float angle) {
		return new Matrix4(
			(float) Math.cos(-angle), 0.0f, (float) Math.sin(-angle), 0.0f,
			0.0f, 1.0f, 0.0f, 0.0f,
			(float) -Math.sin(-angle), 0.0f, (float) Math.cos(-angle), 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		);
	}

	/**
	 * Creates a rotation matrix for the z axis.
	 * 
	 * @param angle Rotation angle in radians
	 * 
	 * @return The rotation matrix
	 */
	public static Matrix4 zRotation(float angle) {
		return new Matrix4(
			(float) Math.cos(-angle), (float) -Math.sin(-angle), 0.0f, 0.0f,
			(float) Math.sin(-angle), (float) Math.cos(-angle), 0.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		);
	}

	/**
	 * Creates a rotation matrix.
	 * 
	 * @param rotation Rotation vector
	 * 
	 * @return The rotation matrix
	 */
	public static Matrix4 rotation(Float3 rotation) {
		return xRotation((float) Math.toRadians(rotation.x()))
			.multiply(yRotation((float) Math.toRadians(rotation.y())))
			.multiply(zRotation((float) Math.toRadians(rotation.z())));
	}

	/**
	 * Creates a scaling matrix.
	 * 
	 * @param scale Scale vector
	 * 
	 * @return The scaling matrix
	 */
	public static Matrix4 scaling(Float3 scale) {
		return new Matrix4(
			scale.x(), 0.0f, 0.0f, 0.0f,
			0.0f, scale.y(), 0.0f, 0.0f,
			0.0f, 0.0f, scale.z(), 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		);
	}

	/**
	 * Creates a perspective projection matrix.
	 * 
	 * @param fov Field of view
	 * @param near Near plane distance
	 * @param far Far plane distance
	 * 
	 * @return The projection matrix
	 */
	public static Matrix4 perspectiveProjection(float fov, float near, float far, float aspectRatio) {
		float m00 = 1.0f / (float) Math.tan(Math.toRadians(fov / 2.0f));
		float m11 = m00 * aspectRatio;
		float m22 = -(far + near) / (far - near);
		float m32 = -(2 * far * near) / (far - near);
		return new Matrix4(
			m00, 0.0f, 0.0f, 0.0f,
			0.0f, m11, 0.0f, 0.0f,
			0.0f, 0.0f, m22, -1.0f,
			0.0f, 0.0f, m32, 0.0f
		);
	}

	public static Matrix4 orthographicProjection(float left, float right, float top, float bottom, float far, float near) {
		float m00 = 2.0f / (right - left);
		float m11 = 2.0f / (top - bottom);
		float m22 = -2.0f / (far - near);
		float m03 = -(right + left) / (right - left);
		float m13 = -(top + bottom) / (top - bottom);
		float m23 = -(far + near) / (far - near);
		return new Matrix4(
			m00, 0.0f, 0.0f, m03,
			0.0f, m11, 0.0f, m13,
			0.0f, 0.0f, m22, m23,
			0.0f, 0.0f, 0.0f, 1.0f
		);
	}

	/**
	 * Creates a camera view matrix.
	 * 
	 * @param cameraPosition Position of the camera
	 * @param pitch Camera rotation
	 * @param yaw Camera rotation
	 * 
	 * @return The view matrix
	 */
	public static Matrix4 view(Float3 cameraPosition, float pitch, float yaw) {
		return translation(cameraPosition.negative()).transposed()
				.multiply(xRotation((float) Math.toRadians(pitch)))
				.multiply(yRotation((float) Math.toRadians(yaw)));
	}

	public static Matrix4 view(Float3 cameraPosition, Float3 cameraRotation) {
		return translation(cameraPosition.negative()).transposed()
				.multiply(rotation(cameraRotation));
	}

	public static Matrix4 view(Float3 cameraPosition) {
		return translation(cameraPosition.negative()).transposed();
	}

	/**Class should not be instantiated */
	private Matrices() {}
}
