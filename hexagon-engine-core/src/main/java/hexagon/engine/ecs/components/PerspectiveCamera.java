package hexagon.engine.ecs.components;

import hexagon.engine.ecs.GameEntity;
import hexagon.engine.glfw.WindowSize;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float3;
import hexagon.engine.utils.json.JsonObject;

public final class PerspectiveCamera extends Camera {
	
	private Float3 position = Float3.ZERO;
	private Float3 rotation = Float3.ZERO;

	private float fov = 70.0f;
	private float zNear = 0.1f;
	private float zFar = 1000.0f;

	public PerspectiveCamera(GameEntity entity) {
		super(entity);
	}

	@Override
	public void init(JsonObject jsonObject) {
		JsonObject positionJson = jsonObject.getObject("position").orElse(JsonObject.empty());
		JsonObject rotationJson = jsonObject.getObject("rotation").orElse(JsonObject.empty());
		this.position = new Float3(positionJson.getFloat("x").orElse(this.position.x()), positionJson.getFloat("y").orElse(this.position.y()), positionJson.getFloat("z").orElse(this.position.z()));
		this.rotation = new Float3(rotationJson.getFloat("pitch").orElse(this.rotation.x()), rotationJson.getFloat("yaw").orElse(this.rotation.y()), rotationJson.getFloat("roll").orElse(this.rotation.z()));
		this.fov = jsonObject.getFloat("fov").orElse(this.fov);
		this.zNear = jsonObject.getFloat("zNear").orElse(this.zNear);
		this.zFar = jsonObject.getFloat("zFar").orElse(this.zFar);
		if(jsonObject.getBoolean("main").orElse(false)) this.setMain();
	}

	@Override
	public Matrix4 projectionMatrix() {
		return Matrices.perspectiveProjection(this.fov, this.zNear, this.zFar, WindowSize.aspectRatio());
	}

	@Override
	public Matrix4 viewMatrix() {
		return Matrices.view(this.position, this.rotation);
	}

	public void setPosition(Float3 position) {
		if(position != null) this.position = position;
	}

	public void setPosition(float x, float y, float z) {
		this.setPosition(new Float3(x, y, z));
	}

	public void setRotation(Float3 rotation) {
		if(rotation != null) this.rotation = rotation;
	}

	public Float3 getPosition() {
		return this.position;
	}

	public void move(Float3 translation) {
		if(translation != null) this.position = this.position.plus(translation);
	}

	public void move(float x, float y, float z) {
		this.move(new Float3(x, y, z));
	}

	public void setRotation(float pitch, float yaw, float roll) {
		this.setRotation(new Float3(pitch, yaw, roll));
	}

	public void setPitch(float pitch) {
		this.rotation = new Float3(pitch, this.rotation.y(), this.rotation.z());
	}

	public void setYaw(float yaw) {
		this.rotation = new Float3(this.rotation.x(), yaw, this.rotation.z());
	}

	public void setRoll(float roll) {
		this.rotation = new Float3(this.rotation.x(), this.rotation.y(), roll);
	}

	public Float3 getRotation() {
		return this.rotation;
	}

	public float getPitch() {
		return this.rotation.x();
	}

	public float getYaw() {
		return this.rotation.y();
	}

	public float getRoll() {
		return this.rotation.z();
	}

	public void rotate(Float3 rotation) {
		if(rotation != null) this.rotation = this.rotation.plus(rotation);
	}

	public void rotate(float x, float y, float z) {
		this.rotate(new Float3(x, y, z));
	}

	public void setProjection(float fov, float nearPlane, float farPlane) {
		this.fov = fov;
		this.zNear = nearPlane;
		this.zFar = farPlane;
	}

	public void setFov(float fov) {
		this.fov = fov;
	}

	public void setNearPlane(float nearPlane) {
		this.zNear = nearPlane;
	}

	public void setFarPlane(float farPlane) {
		this.zFar = farPlane;
	}

	public float getFov() {
		return this.fov;
	}

	public float getNearPlane() {
		return this.zNear;
	}

	public float getFarPlane() {
		return this.zFar;
	}
}
