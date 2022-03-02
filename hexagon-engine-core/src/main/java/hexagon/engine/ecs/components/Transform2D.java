package hexagon.engine.ecs.components;

import java.util.List;

import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float2;
import hexagon.engine.math.vector.Float3;
import hexagon.engine.utils.json.JsonObject;

/**
 * Component that holds geometric transformations, i. e., position, rotation and scale, in a 2D space.
 * 
 * @author Nico
 */
public final class Transform2D extends Transform<Float2> {

	/**
	 * Creates a transform component.
	 * 
	 * @param entity The entity that holds this component.
	 */
	public Transform2D(GameEntity entity) {
		super(entity, Float2.ZERO, Float2.ZERO, Float2.ONE);
	}

	@Override
	public void init(JsonObject jsonObject) {
		JsonObject positionJson = jsonObject.getObject("position").orElse(JsonObject.empty());
		JsonObject rotationJson = jsonObject.getObject("rotation").orElse(JsonObject.empty());
		JsonObject scaleJson = jsonObject.getObject("scale").orElse(JsonObject.empty());
		this.setPosition(positionJson.getFloat("x").orElse(0.0f), positionJson.getFloat("y").orElse(0.0f));
		this.setRotation(rotationJson.getFloat("x").orElse(0.0f), rotationJson.getFloat("y").orElse(0.0f));
		this.setScale(scaleJson.getFloat("x").orElse(1.0f), scaleJson.getFloat("y").orElse(1.0f));
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

	@Override
	public Matrix4 matrix() {
		return Matrices.transformation(
			new Float3(super.position().x(), super.position().y(), 0.0f),
			new Float3(super.rotation().x(), super.rotation().y(), 0.0f),
			new Float3(super.scale().x(), super.scale().y(), 1.0f)
		);
	}

	/**
	 * Sets this objects position.
	 * This method always sets a position relative to the parent's position.
	 * If the value passed is null, the value is not set.
	 * 
	 * @param x Position X coordinate
	 * @param y Position Y coordinate
	 */
	public void setPosition(float x, float y) {
		super.setPosition(new Float2(x, y));
	}

	/**
	 * Sets this objects rotation.
	 * This method always sets a rotation relative to the parent's rotation.
	 * If the value passed is null, the value is not set.
	 * 
	 * @param x Rotation around X axis
	 * @param y Rotation around Y axis
	 */
	public void setRotation(float x, float y) {
		super.setRotation(new Float2(x, y));
	}

	/**
	 * Sets this objects scale.
	 * This method always sets a scale relative to the parent's scale.
	 * If the value passed is null, the value is not set.
	 * 
	 * @param x Scale on X axis
	 * @param y Scale on Y axis
	 */
	public void setScale(float x, float y) {
		super.setScale(new Float2(x, y));
	}

	/**
	 * Applies a translation.
	 * Changes this object's position by adding the given vector.
	 * 
	 * @param x Translation on X axis
	 * @param y Translation on Y axis
	 */
	public void translate(float x, float y) {
		super.translate(new Float2(x, y));
	}

	/**
	 * Applies a rotation.
	 * Changes this object's rotation by adding the given vector.
	 * 
	 * @param x Rotation around X axis
	 * @param y Rotation around Y axis
	 */
	public void rotate(float x, float y) {
		super.rotate(new Float2(x, y));
	}
}
