package hexagon.engine.ecs.components;

import java.util.Optional;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float3;
import hexagon.engine.utils.json.JsonObject;

public class Camera extends Component {

	private static Camera main;

	public static Optional<Camera> main() {
		return Optional.ofNullable(main);
	}

	public Float3 position = Float3.ZERO;
	public float pitch = 0.0f;
	public float yaw = 0.0f;

	public float fov = 70.0f;
	public float nearPlane = 0.1f;
	public float farPlane = 1000.0f;

	public Camera(GameEntity entity) {
		super(entity);
		if(main == null)
			main = this;
	}

	@Override
	protected void init(JsonObject jsonObject) {
		JsonObject positionJson = jsonObject.getObject("position").orElse(JsonObject.empty());
		float x = positionJson.getFloat("x").orElse(this.position.x());
		float y = positionJson.getFloat("y").orElse(this.position.y());
		float z = positionJson.getFloat("z").orElse(this.position.z());
		this.position = new Float3(x, y, z);
		this.pitch = jsonObject.getFloat("pitch").orElse(this.pitch);
		this.yaw = jsonObject.getFloat("yaw").orElse(this.yaw);
		this.fov = jsonObject.getFloat("fov").orElse(this.fov);
		this.nearPlane = jsonObject.getFloat("nearPlane").orElse(this.nearPlane);
		this.farPlane = jsonObject.getFloat("farPlane").orElse(this.farPlane);
	}

	public Matrix4 projectionMatrix() {
		return Matrices.projection(this.fov, this.nearPlane, this.farPlane);
	}

	public Matrix4 viewMatrix() {
		return Matrices.view(this.position, this.pitch, this.yaw);
	}

	public void setMain() {
		main = this;
	}
}
