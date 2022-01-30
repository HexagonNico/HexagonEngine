package hexagon.engine.core.components;

import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float3;

public final class Transform3D {
	
	public Float3 position;
	public Float3 rotation;
	public Float3 scale;

	public Transform3D() {
		this(Float3.ZERO, Float3.ZERO, Float3.ONE);
	}

	public Transform3D(Float3 position, Float3 rotation, Float3 scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public Matrix4 matrix() {
		return Matrices.transformation(this.position, this.rotation, this.scale);
	}
}
