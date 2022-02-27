package hexagon.engine.ecs.components;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.FloatVector;

public abstract sealed class Transform<V extends FloatVector<V, ?>> extends Component permits Transform2D, Transform3D {
	
	private Transform<V> parent;

	private V position;
	private V rotation;
	private V scale;
	
	public Transform(GameEntity entity, V position, V rotation, V scale) {
		super(entity);
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public abstract Matrix4 matrix();

	public final void setParent(Transform<V> parent) {
		this.parent = parent;
	}

	public final V position() {
		return this.parent != null ? this.parent.position().plus(this.position) : this.localPosition();
	}

	public final V localPosition() {
		return this.position;
	}

	public final V rotation() {
		return this.parent != null ? this.parent.rotation().plus(this.rotation) : this.localRotation();
	}

	public final V localRotation() {
		return this.rotation;
	}

	public final V scale() {
		return this.parent != null ? this.parent.scale().plus(this.scale) : this.localScale();
	}

	public final V localScale() {
		return this.scale;
	}

	public final void setPosition(V position) {
		if(position != null) this.position = position;
	}

	public final void setRotation(V rotation) {
		if(rotation != null) this.rotation = rotation;
	}

	public final void setScale(V scale) {
		if(scale != null) this.scale = scale;
	}
}
