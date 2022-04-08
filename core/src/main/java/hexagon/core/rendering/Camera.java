package hexagon.core.rendering;

import hexagon.lwjgl.glfw.WindowSize;
import hexagon.math.matrix.Matrices;
import hexagon.math.matrix.Matrix4;
import hexagon.math.vector.Float3;

/**
 * Class that represents the scene's camera.
 * Holds the camera's transformation and the screen projection.
 * 
 * @author Nico
 */
public final class Camera {

	/**Current main camera */
	private static Camera main = new Camera();

	/**
	 * Gets the current main camera.
	 * 
	 * @return Current main camera
	 */
	public static Camera main() {
		return main;
	}

	/**Camera position */
	private Float3 position = Float3.ZERO;
	/**Camera rotation */
	private Float3 rotation = Float3.ZERO;

	/**Field of view */
	private float fov = 70.0f;
	/**Near plane distance */
	private float nearPlane = 0.1f;
	/**Far plane distance */
	private float farPlane = 1000.0f;

	/**
	 * Computes the projection matrix.
	 * Used in rendering.
	 * 
	 * @return The projection matrix
	 */
	public Matrix4 projection() {
		float m00 = 1.0f / (float) Math.tan(Math.toRadians(fov / 2.0f));
		float m11 = m00 * WindowSize.aspectRatio();
		float m22 = -(this.farPlane + this.nearPlane) / (this.farPlane - this.nearPlane);
		float m32 = -(2 * this.farPlane * this.nearPlane) / (this.farPlane - this.nearPlane);
		return new Matrix4(
			m00, 0.0f, 0.0f, 0.0f,
			0.0f, m11, 0.0f, 0.0f,
			0.0f, 0.0f, m22, -1.0f,
			0.0f, 0.0f, m32, 0.0f
		);
	}

	/**
	 * Computes the view matrix.
	 * Used in rendering.
	 * 
	 * @return A matrix containing the camera's transformations
	 */
	public Matrix4 view() {
		return Matrices.translation(this.position.negative()).transposed()
			.multiply(Matrices.rotationDegrees(this.rotation));
	}

	/**
	 * Get camera position.
	 * 
	 * @return The camera's position
	 */
	public Float3 position() {
		return this.position;
	}

	/**
	 * Sets this camera as the main one.
	 * The main camera is the one used in the rendering system.
	 */
	public void setMain() {
		main = this;
	}

	/**
	 * Set position.
	 * If the given position is {@code null} it will be treated as (0, 0, 0)
	 * 
	 * @param position New position
	 */
	public void setPosition(Float3 position) {
		this.position = position != null ? position : Float3.ZERO;
	}

	/**
	 * Set position.
	 * 
	 * @param x Position x
	 * @param y Position y
	 * @param z Position z
	 */
	public void setPosition(float x, float y, float z) {
		this.position = new Float3(x, y, z);
	}

	/**
	 * Moves the camera
	 * 
	 * @param translation Translation vector
	 */
	public void move(Float3 translation) {
		this.position = this.position.plus(translation);
	}

	/**
	 * Moves the camera
	 * 
	 * @param x Translation x
	 * @param y Translation y
	 * @param z Translation z
	 */
	public void move(float x, float y, float z) {
		this.position = this.position.plus(x, y, z);
	}

	/**
	 * Get pitch.
	 * 
	 * @return Camera's rotation around the x axis
	 */
	public float pitch() {
		return this.rotation.x();
	}

	/**
	 * Get yaw.
	 * 
	 * @return Camera's rotation around the y axis
	 */
	public float yaw() {
		return this.rotation.y();
	}

	/**
	 * Get roll.
	 * 
	 * @return Camera's rotation around the z axis
	 */
	public float roll() {
		return this.rotation.z();
	}

	/**
	 * Get camera rotation
	 * 
	 * @return A vector containing the rotation around the 3 axis
	 */
	public Float3 rotation() {
		return this.rotation;
	}

	/**
	 * Set rotation around x axis
	 * 
	 * @param pitch Camera's pitch
	 */
	public void setPitch(float pitch) {
		this.rotation = new Float3(pitch, this.yaw(), this.roll());
	}

	/**
	 * Set rotation around y axis
	 * 
	 * @param yaw Camera's yaw
	 */
	public void setYaw(float yaw) {
		this.rotation = new Float3(this.pitch(), yaw, this.roll());
	}

	/**
	 * Set rotation around z axis
	 * 
	 * @param pitch Camera's roll
	 */
	public void setRoll(float roll) {
		this.rotation = new Float3(this.pitch(), this.yaw(), roll);
	}

	/**
	 * Set camera rotation
	 * 
	 * @param rotation A vector containing the rotation around the 3 axis
	 */
	public void setRotation(Float3 rotation) {
		this.rotation = rotation;
	}

	/**
	 * Set camera rotation.
	 * 
	 * @param pitch Rotation around x axis
	 * @param yaw Rotation around y axis
	 * @param roll Rotation around z axis
	 */
	public void setRotation(float pitch, float yaw, float roll) {
		this.setRotation(new Float3(pitch, yaw, roll));
	}

	/**
	 * Rotates the camera.
	 * 
	 * @param rotation Camera rotation
	 */
	public void rotate(Float3 rotation) {
		this.rotation = this.rotation.plus(rotation);
	}

	/**
	 * Rotates the camera
	 * 
	 * @param x Rotation around x axis
	 * @param y Rotation around y axis
	 * @param z Rotation around z axis
	 */
	public void rotate(float x, float y, float z) {
		this.rotation = this.rotation.plus(x, y, z);
	}

	/**
	 * Changes camera pitch
	 * 
	 * @param pitch Rotation around x axis
	 */
	public void rotatePitch(float pitch) {
		this.setPitch(this.pitch() + pitch);
	}

	/**
	 * Changes camera yaw
	 * 
	 * @param yaw Rotation around y axis
	 */
	public void rotateYaw(float yaw) {
		this.setYaw(this.yaw() + yaw);
	}

	/**
	 * Changes camera roll
	 * 
	 * @param roll Rotation around z axis
	 */
	public void rotateRoll(float roll) {
		this.setRoll(this.roll() + roll);
	}

	/**
	 * Get field of view.
	 * 
	 * @return Field of view angle
	 */
	public float fov() {
		return this.fov;
	}

	/**
	 * Sets field of view.
	 * 
	 * @param fov Field of view angle
	 */
	public void setFov(float fov) {
		this.fov = fov;
	}

	/**
	 * Gets near plane.
	 * 
	 * @return Near plane distance
	 */
	public float nearPlane() {
		return this.nearPlane;
	}

	/**
	 * Sets near plane distance.
	 * 
	 * @param nearPlane Near plane distance
	 */
	public void setNearPlane(float nearPlane) {
		this.nearPlane = nearPlane;
	}

	/**
	 * Gets far plane.
	 * 
	 * @return Far plane distance
	 */
	public float farPlane() {
		return this.farPlane;
	}

	/**
	 * Sets far plane distance.
	 * 
	 * @param farPlane far plane distance
	 */
	public void setFarPlane(float farPlane) {
		this.farPlane = farPlane;
	}

	/**
	 * Sets camera projection.
	 * 
	 * @param fov Field of view angle
	 * @param nearPlane Near plane distance
	 * @param farPlane Far plane distance
	 */
	public void setProjection(float fov, float nearPlane, float farPlane) {
		this.fov = fov;
		this.nearPlane = nearPlane;
		this.farPlane = farPlane;
	}
}
