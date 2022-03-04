package hexagon.engine.ecs.components;

import java.util.List;
import java.util.Optional;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float2;
import hexagon.engine.utils.json.JsonObject;

public final class Transform2D extends Component {

	private Transform2D parent = null;

	// TODO - Rotation may need more than one axis

	private Float2 position = Float2.ZERO;
	private float rotation = 0.0f;
	private Float2 scale = Float2.ONE;

	/**
	 * Creates a transform component.
	 * 
	 * @param entity The entity that holds this component.
	 */
	public Transform2D(GameEntity entity) {
		super(entity);
	}

	@Override
	public void init(JsonObject jsonObject) {
		jsonObject.getObject("position").ifPresent(positionJson -> {
			float x = positionJson.getFloat("x", this.position.x());
			float y = positionJson.getFloat("y", this.position.y());
			this.position = new Float2(x, y);
		});
		this.rotation = jsonObject.getFloat("rotation", this.rotation);
		jsonObject.getObject("scale").ifPresent(scaleJson -> {
			float x = scaleJson.getFloat("x", this.scale.x());
			float y = scaleJson.getFloat("y", this.scale.y());
			this.scale = new Float2(x, y);
		});
	}

	@Override
	public void init(List<GameEntity> loadedEntities, JsonObject jsonObject) {
		super.init(loadedEntities, jsonObject);
		jsonObject.getInt("parent").ifPresent(parentId -> {
			loadedEntities.get(parentId).getComponent(Transform2D.class).ifPresent(transform -> {
				this.setParent(transform);
			});
		});
	}

	/**
	 * Computes the transformation matrix with all the values from this transform.
	 * Can be used in rendering.
	 * 
	 * @return The computed matrix
	 */
	public Matrix4 matrix() {
		Matrix4 transformation = Matrices.transformation(this.position, this.rotation, this.scale);
		return this.parent != null ? transformation.multiply(this.parent.matrix()) : transformation;
	}

	/**
	 * Sets this transform's parent.
	 * All the transformations of a transform depend from its parent (if it has one).
	 * Set the parent to null to make the transform not have a parent.
	 * 
	 * @param parent The transform's parent (or null to remove the parent).
	 */
	public final void setParent(Transform2D parent) {
		// TODO - Keep world position when setting a parent to null
		this.parent = parent;
	}

	public final Optional<Transform2D> getParent() {
		return Optional.ofNullable(this.parent);
	}

	/**
	 * Gets the transform's position.
	 * Position is relative to the parent if this transform has one, global otherwise.
	 * 
	 * @return Position of the transform
	 */
	public final Float2 position() {
		return this.position;
	}

	/**
	 * Gets the transform's rotation.
	 * Rotation is relative to the parent if this transform has one, global otherwise.
	 * 
	 * @return Rotation of the transform
	 */
	public final float rotation() {
		return this.rotation;
	}

	/**
	 * Gets the transform's scale.
	 * Scale is relative to the parent if this transform has one, global otherwise.
	 * 
	 * @return Scale of the transform
	 */
	public final Float2 scale() {
		return this.scale;
	}

	/**
	 * Sets this objects position.
	 * This method always sets a position relative to the parent's position.
	 * If the value passed is null, the value is not set.
	 * 
	 * @param position Position value
	 */
	public final void setPosition(Float2 position) {
		if(position != null) this.position = position;
	}

	/**
	 * Sets this objects position.
	 * This method always sets a position relative to the parent's position.
	 * 
	 * @param x Position X coordinate
	 * @param y Position Y coordinate
	 */
	public final void setPosition(float x, float y) {
		this.setPosition(new Float2(x, y));
	}

	/**
	 * Sets this objects rotation.
	 * This method always sets a rotation relative to the parent's rotation.
	 * 
	 * @param rotation Rotation value
	 */
	public final void setRotation(float rotation) {
		this.rotation = rotation;
	}

	/**
	 * Sets this objects scale.
	 * This method always sets a scale relative to the parent's scale.
	 * If the value passed is null, the value is not set.
	 * 
	 * @param scale Scale value
	 */
	public final void setScale(Float2 scale) {
		if(scale != null) this.scale = scale;
	}

	/**
	 * Sets this objects scale.
	 * This method always sets a scale relative to the parent's scale.
	 * 
	 * @param x Scale on X axis
	 * @param y Scale on Y axis
	 */
	public final void setScale(float x, float y) {
		this.setScale(new Float2(x, y));
	}

	/**
	 * Applies a translation.
	 * Changes this object's position by adding the given vector.
	 * The value is not changed if the given value is null.
	 * 
	 * @param translation Vector translation
	 */
	public final void translate(Float2 translation) {
		if(translation != null) this.position = this.position.plus(translation);
	}

	/**
	 * Applies a translation.
	 * Changes this object's position by adding the given vector.
	 * 
	 * @param x Translation on X axis
	 * @param y Translation on Y axis
	 */
	public final void translate(float x, float y) {
		this.position = this.position.plus(x, y);
	}

	/**
	 * Applies a rotation.
	 * Changes this object's rotation by adding the given vector.
	 * 
	 * @param rotation Vector rotation
	 */
	public final void rotate(float rotation) {
		this.rotation += rotation;
	}
}
