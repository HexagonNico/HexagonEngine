package hexagon.engine.ecs.components;

import hexagon.engine.ecs.GameEntity;
import hexagon.engine.glfw.WindowSize;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float2;
import hexagon.engine.math.vector.Float3;
import hexagon.engine.utils.json.JsonObject;

public final class OrthographicCamera extends Camera {

	private Float2 position = Float2.ZERO;
	// TODO - Check what these guys do
	private float nearPlane = 0.1f;
	private float farPlane = 100.0f;

	public OrthographicCamera(GameEntity entity) {
		super(entity);
	}

	@Override
	public void init(JsonObject jsonObject) {
		jsonObject.getObject("position").ifPresent(positionJson -> {
			float x = positionJson.getFloat("x", this.position.x());
			float y = positionJson.getFloat("y", this.position.y());
			this.position = new Float2(x, y);
		});
		this.nearPlane = jsonObject.getFloat("nearPlane", this.nearPlane);
		this.farPlane = jsonObject.getFloat("farPlane", this.farPlane);
		jsonObject.ifBoolean("main", () -> this.setMain());
	}

	@Override
	public Matrix4 projectionMatrix() {
		// TODO - Orthographic size
		return Matrices.orthographicProjection(
				-WindowSize.width() / 2.0f, WindowSize.width() / 2.0f,
				WindowSize.height() / 2.0f, -WindowSize.height() / 2.0f,
				this.farPlane, this.nearPlane
		).multiply(Matrices.scaling(new Float3(100.0f, 100.0f, 1.0f)));
	}

	@Override
	public Matrix4 viewMatrix() {
		return Matrices.view(new Float3(this.position.x(), this.position.y(), 0.0f));
	}

	public Float2 getPosition() {
		return this.position;
	}

	public void setPosition(Float2 position) {
		if(position != null) this.position = position;
	}

	public void setPosition(float x, float y) {
		this.setPosition(new Float2(x, y));
	}

	public void move(Float2 translation) {
		if(translation != null) this.position = this.position.plus(translation);
	}

	public void move(float x, float y) {
		this.move(new Float2(x, y));
	}

	public void setNearPlane(float nearPlane) {
		this.nearPlane = nearPlane;
	}

	public float getNearPlane() {
		return this.nearPlane;
	}

	public void setFarPlane(float farPlane) {
		this.farPlane = farPlane;
	}

	public float getFarPlane() {
		return this.farPlane;
	}
}
