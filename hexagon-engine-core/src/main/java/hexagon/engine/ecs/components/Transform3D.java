package hexagon.engine.ecs.components;

import java.util.List;

import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float3;
import hexagon.engine.utils.json.JsonObject;

/**
 * Component that holds geometric transformations, i. e., position, rotation and scale, in a 3D space.
 * 
 * @author Nico
 */
public final class Transform3D extends Transform<Float3> {

	/**
	 * Creates a transform component.
	 * 
	 * @param entity The entity that holds this component.
	 */
	public Transform3D(GameEntity entity) {
		super(entity, Float3.ZERO, Float3.ZERO, Float3.ONE);
	}

	@Override
	public void init(JsonObject jsonObject) {
		JsonObject positionJson = jsonObject.getObject("position").orElse(JsonObject.empty());
		JsonObject rotationJson = jsonObject.getObject("rotation").orElse(JsonObject.empty());
		JsonObject scaleJson = jsonObject.getObject("scale").orElse(JsonObject.empty());
		this.setPosition(positionJson.getFloat("x").orElse(0.0f), positionJson.getFloat("y").orElse(0.0f), positionJson.getFloat("z").orElse(0.0f));
		this.setRotation(rotationJson.getFloat("x").orElse(0.0f), rotationJson.getFloat("y").orElse(0.0f), rotationJson.getFloat("z").orElse(0.0f));
		this.setScale(scaleJson.getFloat("x").orElse(1.0f), scaleJson.getFloat("y").orElse(1.0f), scaleJson.getFloat("z").orElse(1.0f));
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

	@Override
	public Matrix4 matrix() {
		Matrix4 transformation = Matrices.transformation(this.position(), this.rotation(), this.scale());
		return super.parent != null ? transformation.multiply(super.parent.matrix()) : transformation;
	}

	/**
	 * Sets this objects position.
	 * This method always sets a position relative to the parent's position.
	 * If the value passed is null, the value is not set.
	 * 
	 * @param x Position X coordinate
	 * @param y Position Y coordinate
	 * @param z Position Z coordinate
	 */
	public void setPosition(float x, float y, float z) {
		super.setPosition(new Float3(x, y, z));
	}

	/**
	 * Sets this objects rotation.
	 * This method always sets a rotation relative to the parent's rotation.
	 * If the value passed is null, the value is not set.
	 * 
	 * @param x Rotation around X axis
	 * @param y Rotation around Y axis
	 * @param z Rotation around Z axis
	 */
	public void setRotation(float x, float y, float z) {
		super.setRotation(new Float3(x, y, z));
	}

	/**
	 * Sets this objects scale.
	 * This method always sets a scale relative to the parent's scale.
	 * If the value passed is null, the value is not set.
	 * 
	 * @param x Scale on X axis
	 * @param y Scale on Y axis
	 * @param z Scale on Z axis
	 */
	public void setScale(float x, float y, float z) {
		super.setScale(new Float3(x, y, z));
	}

	/**
	 * Applies a translation.
	 * Changes this object's position by adding the given vector.
	 * 
	 * @param x Translation on X axis
	 * @param y Translation on Y axis
	 * @param z Translation on Z axis
	 */
	public void translate(float x, float y, float z) {
		super.setPosition(super.localPosition().plus(x, y, z));
	}

	/**
	 * Applies a rotation.
	 * Changes this object's rotation by adding the given vector.
	 * 
	 * @param x Rotation around X axis
	 * @param y Rotation around Y axis
	 * @param z Rotation around Z axis
	 */
	public void rotate(float x, float y, float z) {
		super.setRotation(super.localRotation().plus(x, y, z));
	}
}
