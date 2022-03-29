package hexagon.math.matrix;

import hexagon.math.vector.Float2;
import hexagon.math.vector.Float3;

/**
 * Utility class that contains static methods to create important matrices.
 * Used for geometry transformations.
 * 
 * @author Nico
 */
public final class Matrices {
	
	/**
	 * Creates a translation matrix.
	 * Any vector multiplied by this matrix will result in its translation of the given value.
	 * If the given translation is {@code null} it will be treated as (0, 0).
	 * 
	 * @param translation Vector representing this matrix's translation
	 * 
	 * @return A matrix that can apply the given translation if multiplied by a 4D vector
	 * 		or the identity matrix if the given translation is {@code null}
	 */
	public static Matrix4 translation(Float2 translation) {
		return translation != null ? new Matrix4(
			1.0f, 0.0f, 0.0f, translation.x(),
			0.0f, 1.0f, 0.0f, translation.y(),
			0.0f, 0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		) : Matrix4.IDENTITY;
	}

	/**
	 * Creates a translation matrix.
	 * Any vector multiplied by this matrix will result in its translation of the given value.
	 * If the given translation is {@code null} it will be treated as (0, 0, 0).
	 * 
	 * @param translation Vector representing this matrix's translation
	 * 
	 * @return A matrix that can apply the given translation if multiplied by a 4D vector
	 * 		or the identity matrix if the given translation is {@code null}
	 */
	public static Matrix4 translation(Float3 translation) {
		return translation != null ? new Matrix4(
			1.0f, 0.0f, 0.0f, translation.x(),
			0.0f, 1.0f, 0.0f, translation.y(),
			0.0f, 0.0f, 1.0f, translation.z(),
			0.0f, 0.0f, 0.0f, 1.0f
		) : Matrix4.IDENTITY;
	}

	/**
	 * Creates a rotation matrix.
	 * Any vector multiplied by this matrix will result in its rotation around the x axis by the given angle.
	 * 
	 * @param angle The rotation angle in degrees
	 * 
	 * @return A matrix that can apply the given rotation if multiplied by a 4D vector
	 */
	public static Matrix4 xRotationDegrees(float angle) {
		return xRotationRadians((float) Math.toRadians(angle));
	}

	/**
	 * Creates a rotation matrix.
	 * Any vector multiplied by this matrix will result in its rotation around the x axis by the given angle.
	 * 
	 * @param angle The rotation angle in radians
	 * 
	 * @return A matrix that can apply the given rotation if multiplied by a 4D vector
	 */
	public static Matrix4 xRotationRadians(float angle) {
		return new Matrix4(
			1.0f, 0.0f, 0.0f, 0.0f,
			0.0f, (float) Math.cos(-angle), (float) -Math.sin(-angle), 0.0f,
			0.0f, (float) Math.sin(-angle), (float) Math.cos(-angle), 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		);
	}

	/**
	 * Creates a rotation matrix.
	 * Any vector multiplied by this matrix will result in its rotation around the y axis by the given angle.
	 * 
	 * @param angle The rotation angle in degrees
	 * 
	 * @return A matrix that can apply the given rotation if multiplied by a 4D vector
	 */
	public static Matrix4 yRotationDegrees(float angle) {
		return yRotationRadians((float) Math.toRadians(angle));
	}

	/**
	 * Creates a rotation matrix.
	 * Any vector multiplied by this matrix will result in its rotation around the y axis by the given angle.
	 * 
	 * @param angle The rotation angle in radians
	 * 
	 * @return A matrix that can apply the given rotation if multiplied by a 4D vector
	 */
	public static Matrix4 yRotationRadians(float angle) {
		return new Matrix4(
			(float) Math.cos(-angle), 0.0f, (float) Math.sin(-angle), 0.0f,
			0.0f, 1.0f, 0.0f, 0.0f,
			(float) -Math.sin(-angle), 0.0f, (float) Math.cos(-angle), 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		);
	}

	/**
	 * Creates a rotation matrix.
	 * Any vector multiplied by this matrix will result in its rotation around the z axis by the given angle.
	 * 
	 * @param angle The rotation angle in degrees
	 * 
	 * @return A matrix that can apply the given rotation if multiplied by a 4D vector
	 */
	public static Matrix4 zRotationDegrees(float angle) {
		return zRotationRadians((float) Math.toRadians(angle));
	}

	/**
	 * Creates a rotation matrix.
	 * Any vector multiplied by this matrix will result in its rotation around the z axis by the given angle.
	 * 
	 * @param angle The rotation angle in radians
	 * 
	 * @return A matrix that can apply the given rotation if multiplied by a 4D vector
	 */
	public static Matrix4 zRotationRadians(float angle) {
		return new Matrix4(
			(float) Math.cos(-angle), (float) -Math.sin(-angle), 0.0f, 0.0f,
			(float) Math.sin(-angle), (float) Math.cos(-angle), 0.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		);
	}

	/**
	 * Creates a rotation matrix.
	 * Any vector multiplied by this matrix will result in its rotation with angles given by the given vector.
	 * If the given rotation is {@code null} it will be treated as (0, 0, 0).
	 * 
	 * @param rotation A vector that represents the rotation around the three axis in degrees
	 * 
	 * @return A matrix that can apply the given rotation if multiplied by a 4D vector
	 * 		or the identity matrix if the given rotation is {@code null}
	 */
	public static Matrix4 rotationDegrees(Float3 rotation) {
		return rotation != null ? xRotationDegrees(rotation.x())
			.multiply(yRotationDegrees(rotation.y()))
			.multiply(zRotationDegrees(rotation.z())) : Matrix4.IDENTITY;
	}

	/**
	 * Creates a rotation matrix.
	 * Any vector multiplied by this matrix will result in its rotation with angles given by the given vector.
	 * If the given rotation is {@code null} it will be treated as (0, 0, 0).
	 * 
	 * @param rotation A vector that represents the rotation around the three axis in radians
	 * 
	 * @return A matrix that can apply the given rotation if multiplied by a 4D vector
	 * 		or the identity matrix if the given rotation is {@code null}
	 */
	public static Matrix4 rotationRadians(Float3 rotation) {
		return rotation != null ? xRotationRadians(rotation.x())
			.multiply(yRotationRadians(rotation.y()))
			.multiply(zRotationRadians(rotation.z())) : Matrix4.IDENTITY;
	}

	/**
	 * Creates a scaling matrix.
	 * Any vector multiplied by this matrix will result in its scale by the given value.
	 * If the given scale is {@code null} it will be treated as (0, 0).
	 * 
	 * @param translation Vector representing this matrix's scale
	 * 
	 * @return A matrix that can apply the given scaling if multiplied by a 4D vector
	 * 		or the identity matrix if the given scale is {@code null}
	 */
	public static Matrix4 scaling(Float2 scale) {
		return scale != null ? new Matrix4(
			scale.x(), 0.0f, 0.0f, 0.0f,
			0.0f, scale.y(), 0.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		) : Matrix4.IDENTITY;
	}

	/**
	 * Creates a scaling matrix.
	 * Any vector multiplied by this matrix will result in its scale by the given value.
	 * If the given scale is {@code null} it will be treated as (0, 0, 0).
	 * 
	 * @param translation Vector representing this matrix's scale
	 * 
	 * @return A matrix that can apply the given scaling if multiplied by a 4D vector
	 * 		or the identity matrix if the given scale is {@code null}
	 */
	public static Matrix4 scaling(Float3 scale) {
		return scale != null ? new Matrix4(
			scale.x(), 0.0f, 0.0f, 0.0f,
			0.0f, scale.y(), 0.0f, 0.0f,
			0.0f, 0.0f, scale.z(), 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		) : Matrix4.IDENTITY;
	}

	/**Class should not be instantiated */
	private Matrices() {}
}
