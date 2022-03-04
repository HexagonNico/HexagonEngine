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
	private float nearPlane = 0.1f;
	private float farPlane = 100.0f;

	public OrthographicCamera(GameEntity entity) {
		super(entity);
	}

	@Override
	public void init(JsonObject jsonObject) {
		JsonObject positionJson = jsonObject.getObject("position").orElse(JsonObject.empty());
		this.position = new Float2(positionJson.getFloat("x").orElse(this.position.x()), positionJson.getFloat("y").orElse(this.position.y()));
		this.nearPlane = jsonObject.getFloat("nearPlane").orElse(this.nearPlane);
		this.farPlane = jsonObject.getFloat("farPlane").orElse(this.farPlane);
		if(jsonObject.getBoolean("main").orElse(false)) this.setMain();
	}

	@Override
	public Matrix4 projectionMatrix() {
		return Matrices.orthographicProjection(
				-WindowSize.width() / 2.0f, WindowSize.width() / 2.0f,
				WindowSize.height() / 2.0f, WindowSize.height() / 2.0f,
				this.farPlane, this.nearPlane
		);
	}

	@Override
	public Matrix4 viewMatrix() {
		return Matrices.view(new Float3(this.position.x(), this.position.y(), 10.0f));
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
