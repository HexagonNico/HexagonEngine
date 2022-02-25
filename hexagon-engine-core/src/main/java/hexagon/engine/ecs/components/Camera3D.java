package hexagon.engine.ecs.components;

import java.util.Optional;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float3;
import hexagon.engine.utils.json.JsonObject;

public class Camera3D extends Component {

	private static Camera3D main;

	public static Optional<Camera3D> main() {
		return Optional.ofNullable(main);
	}

	public Float3 position;
	public float pitch;
	public float yaw;

	public float fov;
	public float nearPlane;
	public float farPlane;

	public Camera3D(GameEntity entity) {
		super(entity);
	}

	@Override
	protected void init(JsonObject jsonObject) {
		JsonObject positionJson = jsonObject.getObject("position").orElse(JsonObject.empty());
		float x = positionJson.getFloat("x").orElse(0.0f);
		float y = positionJson.getFloat("y").orElse(0.0f);
		float z = positionJson.getFloat("z").orElse(0.0f);
		this.position = new Float3(x, y, z);
		this.pitch = jsonObject.getFloat("pitch").orElse(0.0f);
		this.yaw = jsonObject.getFloat("yaw").orElse(0.0f);
		this.fov = jsonObject.getFloat("fov").orElse(70.0f);
		this.nearPlane = jsonObject.getFloat("nearPlane").orElse(0.1f);
		this.farPlane = jsonObject.getFloat("farPlane").orElse(1000.0f);
		boolean isMain = jsonObject.getBoolean("main").orElse(false);
		if(main == null || isMain) main = this;
	}

	public Matrix4 projectionMatrix() {
		return Matrices.projection(this.fov, this.nearPlane, this.farPlane);
	}

	public Matrix4 viewMatrix() {
		return Matrices.view(this.position, this.pitch, this.yaw);
	}
}
