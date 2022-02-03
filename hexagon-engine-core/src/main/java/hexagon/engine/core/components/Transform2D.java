package hexagon.engine.core.components;

import hexagon.engine.core.ecs.Component;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float2;
import hexagon.engine.math.vector.Float3;

public class Transform2D extends Component {
	
	public Float2 position;
	public Float2 rotation;
	public Float2 scale;

	public Transform2D() {
		this(Float2.ZERO, Float2.ZERO, Float2.ONE);
	}

	public Transform2D(Float2 position, Float2 rotation, Float2 scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public Matrix4 transformationMatrix() {
		return Matrices.transformation(
			new Float3(this.position.x(), this.position.y(), 0.0f),
			new Float3(this.rotation.x(), this.rotation.y(), 0.0f),
			new Float3(this.scale.x(), this.scale.y(), 1.0f)
		);
	}
}
