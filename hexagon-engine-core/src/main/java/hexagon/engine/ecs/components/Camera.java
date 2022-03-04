package hexagon.engine.ecs.components;

import java.util.Optional;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.matrix.Matrix4;

public abstract class Camera extends Component {

	private static Camera main;

	public static Optional<Camera> main() {
		return Optional.ofNullable(main);
	}

	public Camera(GameEntity entity) {
		super(entity);
		if(main == null)
			main = this;
	}

	public abstract Matrix4 projectionMatrix();

	public abstract Matrix4 viewMatrix();

	public final void setMain() {
		main = this;
	}
}
