package hexagon.engine.ecs.components;

import java.util.List;
import java.util.Optional;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float3;
import hexagon.engine.utils.json.JsonObject;

public class Transform3D extends Component {

	private Transform3D parent = null;

	private Float3 position = Float3.ZERO;
	private Float3 rotation = Float3.ZERO;
	private Float3 scale = Float3.ONE;

	/**
	 * Creates a transform component.
	 * 
	 * @param entity The entity that holds this component.
	 */
	public Transform3D(GameEntity entity) {
		super(entity);
	}

	@Override
	public void init(JsonObject jsonObject) {
		jsonObject.getObject("position").ifPresent(positionJson -> {
			float x = positionJson.getFloat("x", this.position.x());
			float y = positionJson.getFloat("y", this.position.y());
			float z = positionJson.getFloat("z", this.position.z());
			this.position = new Float3(x, y, z);
		});
		jsonObject.getObject("rotation").ifPresent(rotationJson -> {
			float x = rotationJson.getFloat("x", this.rotation.x());
			float y = rotationJson.getFloat("y", this.rotation.y());
			float z = rotationJson.getFloat("z", this.rotation.z());
			this.rotation = new Float3(x, y, z);
		});
		jsonObject.getObject("scale").ifPresent(scaleJson -> {
			float x = scaleJson.getFloat("x", this.scale.x());
			float y = scaleJson.getFloat("y", this.scale.y());
			float z = scaleJson.getFloat("z", this.scale.z());
			this.scale = new Float3(x, y, z);
		});
	}

	@Override
	public void init(List<GameEntity> loadedEntities, JsonObject jsonObject) {
		super.init(loadedEntities, jsonObject);
		jsonObject.getInt("parent").ifPresent(parentId -> {
			loadedEntities.get(parentId).getComponent(Transform3D.class).ifPresent(transform -> {
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
	public final Matrix4 matrix() {
		Matrix4 transformation = Matrices.transformation(this.position(), this.rotation(), this.scale());
		return this.parent != null ? transformation.multiply(this.parent.matrix()) : transformation;
	}

	/**
	 * Sets this transform's parent.
	 * All the transformations of a transform depend from its parent (if it has one).
	 * Set the parent to null to make the transform not have a parent.
	 * 
	 * @param parent The transform's parent (or null to remove the parent).
	 */
	public final void setParent(Transform3D parent) {
		// TODO - Keep world position when setting a parent to null
		this.parent = parent;
	}

	public final Optional<Transform3D> getParent() {
		return Optional.ofNullable(this.parent);
	}

	/**
	 * Gets the transform's position.
	 * Position is relative to the parent if this transform has one, global otherwise.
	 * 
	 * @return Position of the transform
	 */
	public final Float3 position() {
		return this.position;
	}

	/**
	 * Gets the transform's rotation.
	 * Rotation is relative to the parent if this transform has one, global otherwise.
	 * 
	 * @return Rotation of the transform
	 */
	public final Float3 rotation() {
		return this.rotation;
	}

	/**
	 * Gets the transform's scale.
	 * Scale is relative to the parent if this transform has one, global otherwise.
	 * 
	 * @return Scale of the transform
	 */
	public final Float3 scale() {
		return this.scale;
	}

	/**
	 * Sets this objects position.
	 * This method always sets a position relative to the parent's position.
	 * If the value passed is null, the value is not set.
	 * 
	 * @param position Position value
	 */
	public final void setPosition(Float3 position) {
		if(position != null) this.position = position;
	}

	/**
	 * Sets this objects position.
	 * This method always sets a position relative to the parent's position.
	 * 
	 * @param x Position X coordinate
	 * @param y Position Y coordinate
	 * @param z Position Z coordinate
	 */
	public final void setPosition(float x, float y, float z) {
		this.setPosition(new Float3(x, y, z));
	}

	/**
	 * Sets this objects rotation.
	 * This method always sets a rotation relative to the parent's rotation.
	 * If the value passed is null, the value is not set.
	 * 
	 * @param rotation Rotation value
	 */
	public final void setRotation(Float3 rotation) {
		if(rotation != null) this.rotation = rotation;
	}

	/**
	 * Sets this objects rotation.
	 * This method always sets a rotation relative to the parent's rotation.
	 * 
	 * @param x Rotation around X axis
	 * @param y Rotation around Y axis
	 * @param z Rotation around Z axis
	 */
	public final void setRotation(float x, float y, float z) {
		this.setRotation(new Float3(x, y, z));
	}

	/**
	 * Sets this objects scale.
	 * This method always sets a scale relative to the parent's scale.
	 * If the value passed is null, the value is not set.
	 * 
	 * @param scale Scale value
	 */
	public final void setScale(Float3 scale) {
		if(scale != null) this.scale = scale;
	}

	/**
	 * Sets this objects scale.
	 * This method always sets a scale relative to the parent's scale.
	 * 
	 * @param x Scale on X axis
	 * @param y Scale on Y axis
	 * @param z Scale on Z axis
	 */
	public final void setScale(float x, float y, float z) {
		this.setScale(new Float3(x, y, z));
	}

	/**
	 * Applies a translation.
	 * Changes this object's position by adding the given vector.
	 * The value is not changed if the given value is null.
	 * 
	 * @param translation Vector translation
	 */
	public final void translate(Float3 translation) {
		if(translation != null) this.position = this.position.plus(translation);
	}

	/**
	 * Applies a translation.
	 * Changes this object's position by adding the given vector.
	 * 
	 * @param x Translation on X axis
	 * @param y Translation on Y axis
	 * @param z Translation on Z axis
	 */
	public final void translate(float x, float y, float z) {
		this.position = this.position.plus(x, y, z);
	}

	/**
	 * Applies a rotation.
	 * Changes this object's rotation by adding the given vector.
	 * The value is not changed if the given value is null.
	 * 
	 * @param rotation Vector rotation
	 */
	public final void rotate(Float3 rotation) {
		if(rotation != null) this.rotation = this.position.plus(rotation);
	}

	/**
	 * Applies a rotation.
	 * Changes this object's rotation by adding the given vector.
	 * 
	 * @param x Rotation around X axis
	 * @param y Rotation around Y axis
	 * @param z Rotation around Z axis
	 */
	public final void rotate(float x, float y, float z) {
		this.rotation = this.rotation.plus(x, y, z);
	}
}
