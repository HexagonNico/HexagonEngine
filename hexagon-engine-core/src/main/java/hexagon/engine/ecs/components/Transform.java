package hexagon.engine.ecs.components;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float3;
import hexagon.engine.math.vector.FloatVector;

/**
 * Component class that stores transformations in a 2D or 3D space,
 * i. e., position, rotation and scale.
 * 
 * @author Nico
 */
public abstract sealed class Transform<V extends FloatVector<V, ?>> extends Component permits Transform2D, Transform3D {
	
	/**The transform's parent */
	private Transform<V> parent;

	/**Position in space */
	private V position;
	/**Rotation around center */
	private V rotation;
	/**Scale of object */
	private V scale;
	
	/**
	 * Creates the transform with the default values to avoid null values.
	 * 
	 * @param entity The entity that holds this component
	 * @param position Default position
	 * @param rotation Default rotation
	 * @param scale Default scale
	 */
	public Transform(GameEntity entity, V position, V rotation, V scale) {
		super(entity);
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public static Matrix4 originMatrix() {
		return Matrices.transformation(Float3.ZERO, Float3.ZERO, Float3.ONE);
	}

	/**
	 * Computes the transformation matrix with all the values from this transform.
	 * Can be used in rendering.
	 * 
	 * @return The computed matrix
	 */
	public abstract Matrix4 matrix();

	/**
	 * Sets this transform's parent.
	 * All the transformations of a transform depend from its parent (if it has one).
	 * Set the parent to null to make the transform not have a parent.
	 * 
	 * @param parent The transform's parent (or null to remove the parent).
	 */
	public final void setParent(Transform<V> parent) {
		this.parent = parent;
	}

	/**
	 * Will return the object's position in global space.
	 * If this transform has a parent, its global position will depend on that.
	 * 
	 * @return The object's position in the world.
	 */
	public final V position() {
		return this.parent != null ? this.parent.position().plus(this.position) : this.localPosition();
	}

	/**
	 * Get the object's position relative to its parent.
	 * If the object does not have a parent this is the same as global position.
	 * 
	 * @return The object's position relative to its parent
	 */
	public final V localPosition() {
		return this.position;
	}

	/**
	 * Will return the object's rotation in global space.
	 * If this transform has a parent, its global rotation will depend on that.
	 * 
	 * @return The object's rotation in world space.
	 */
	public final V rotation() {
		return this.parent != null ? this.parent.rotation().plus(this.rotation) : this.localRotation();
	}

	/**
	 * Get the object's rotation relative to its parent.
	 * If the object does not have a parent this is the same as global rotation.
	 * 
	 * @return The object's rotation relative to its parent
	 */
	public final V localRotation() {
		return this.rotation;
	}

	/**
	 * Will return the object's scale in global space.
	 * If this transform has a parent, its global scale will depend on that.
	 * 
	 * @return The object's scale in world space.
	 */
	public final V scale() {
		// TODO - Fix this: should be multiplicative, not additive
		return this.parent != null ? this.parent.scale().plus(this.scale) : this.localScale();
	}

	/**
	 * Get the object's scale relative to its parent.
	 * If the object does not have a parent this is the same as global scale.
	 * 
	 * @return The object's scale relative to its parent
	 */
	public final V localScale() {
		return this.scale;
	}

	/**
	 * Sets this objects position.
	 * This method always sets a position relative to the parent's position.
	 * If the value passed is null, the value is not set.
	 * 
	 * @param position New local position value
	 */
	public final void setPosition(V position) {
		if(position != null) this.position = position;
	}

	/**
	 * Sets this objects rotation.
	 * This method always sets a rotation relative to the parent's rotation.
	 * If the value passed is null, the value is not set.
	 * 
	 * @param rotation New local rotation value
	 */
	public final void setRotation(V rotation) {
		if(rotation != null) this.rotation = rotation;
	}

	/**
	 * Sets this objects scale.
	 * This method always sets a scale relative to the parent's scale.
	 * If the value passed is null, the value is not set.
	 * 
	 * @param scale New local scale value
	 */
	public final void setScale(V scale) {
		if(scale != null) this.scale = scale;
	}

	/**
	 * Applies a translation.
	 * Changes this object's position by adding the given vector.
	 * 
	 * @param vector The translation
	 */
	public void translate(V vector) {
		this.setPosition(this.localPosition().plus(vector));
	}

	/**
	 * Applies a rotation.
	 * Changes this object's rotation by adding the given vector.
	 * 
	 * @param vector The rotation
	 */
	public void rotate(V vector) {
		this.setRotation(this.localRotation().plus(vector));
	}
}
