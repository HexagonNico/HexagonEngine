package hexagon.core.rendering;

import hexagon.math.matrix.Matrices;
import hexagon.math.matrix.Matrix4;
import hexagon.math.vector.Float3;

public class Camera {

	private static Camera main = new Camera();

	public static Camera main() {
		return main;
	}

	private Float3 position = Float3.ZERO;
	private Float3 rotation = Float3.ZERO;

	private float fov = 70.0f;
	private float nearPlane = 0.1f;
	private float farPlane = 1000.0f;

	public Matrix4 projection() {
		float m00 = 1.0f / (float) Math.tan(Math.toRadians(fov / 2.0f));
		float m11 = m00 * (16.0f / 9.0f); // TODO - Aspect ratio
		float m22 = -(this.farPlane + this.nearPlane) / (this.farPlane - this.nearPlane);
		float m32 = -(2 * this.farPlane * this.nearPlane) / (this.farPlane - this.nearPlane);
		return new Matrix4(
			m00, 0.0f, 0.0f, 0.0f,
			0.0f, m11, 0.0f, 0.0f,
			0.0f, 0.0f, m22, -1.0f,
			0.0f, 0.0f, m32, 0.0f
		);
	}

	public Matrix4 view() {
		return Matrices.translation(this.position.negative()).transposed()
			.multiply(Matrices.rotationDegrees(this.rotation));
	}

	public Float3 position() {
		return this.position;
	}

	public void setMain() {
		main = this;
	}

	public void setPosition(Float3 position) {
		this.position = position != null ? position : Float3.ZERO;
	}

	public void setPosition(float x, float y, float z) {
		this.setPosition(new Float3(x, y, z));
	}

	public void move(Float3 translation) {
		this.position = this.position.plus(translation);
	}

	public void move(float x, float y, float z) {
		this.position = this.position.plus(x, y, z);
	}

	public float pitch() {
		return this.rotation.x();
	}

	public float yaw() {
		return this.rotation.y();
	}

	public float roll() {
		return this.rotation.z();
	}

	public Float3 rotation() {
		return this.rotation;
	}

	public void setPitch(float pitch) {
		this.rotation = new Float3(pitch, this.yaw(), this.roll());
	}

	public void setYaw(float yaw) {
		this.rotation = new Float3(this.pitch(), yaw, this.roll());
	}

	public void setRoll(float roll) {
		this.rotation = new Float3(this.pitch(), this.yaw(), roll);
	}

	public void setRotation(Float3 rotation) {
		this.rotation = rotation;
	}

	public void setRotation(float x, float y, float z) {
		this.setRotation(new Float3(x, y, z));
	}

	public void rotate(Float3 rotation) {
		this.rotation = this.rotation.plus(rotation);
	}

	public void rotate(float x, float y, float z) {
		this.rotation = this.rotation.plus(x, y, z);
	}

	public void rotatePitch(float pitch) {
		this.setPitch(this.pitch() + pitch);
	}

	public void rotateYaw(float yaw) {
		this.setYaw(this.yaw() + yaw);
	}

	public void rotateRoll(float roll) {
		this.setRoll(this.roll() + roll);
	}

	public float fov() {
		return this.fov;
	}

	public void setFov(float fov) {
		this.fov = fov;
	}

	public float nearPlane() {
		return this.nearPlane;
	}

	public void setNearPlane(float nearPlane) {
		this.nearPlane = nearPlane;
	}

	public float farPlane() {
		return this.farPlane;
	}

	public void setFarPlane(float farPlane) {
		this.farPlane = farPlane;
	}

	public void setProjection(float fov, float nearPlane, float farPlane) {
		this.fov = fov;
		this.nearPlane = nearPlane;
		this.farPlane = farPlane;
	}
}
