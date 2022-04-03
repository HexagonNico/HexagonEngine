package hexagon.core.components;

import hexagon.math.matrix.Matrices;
import hexagon.math.matrix.Matrix4;
import hexagon.math.vector.Float3;
import hexagon.utils.json.JsonObject;

/**
 * Component that holds transformations in a 3D space.
 * 
 * @author Nico
 */
public final class Transform3D extends Transform {

	/**Position in a 3D space */
	private Float3 position = Float3.ZERO;
	/**Rotation around the 3 axis */
	private Float3 rotation = Float3.ZERO;
	/**Object's scale */
	private Float3 scale = Float3.ONE;

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
	public Matrix4 matrix() {
		return Matrices.scaling(this.scale)
				.multiply(Matrices.rotationDegrees(this.rotation))
				.multiply(Matrices.translation(this.position).transposed());
	}

	/**
	 * Get position.
	 * 
	 * @return This transform's position
	 */
	public Float3 position() {
		return this.position;
	}

	/**
	 * Set position.
	 * 
	 * @param x Position x
	 * @param y Position y
	 * @param z Position z
	 */
	public void setPosition(float x, float y, float z) {
		this.position = new Float3(x, y, z);
	}

	/**
	 * Set position.
	 * If the given position is {@code null} it will be treated as (0, 0, 0)
	 * 
	 * @param position New position
	 */
	public void setPosition(Float3 position) {
		this.position = position != null ? position : Float3.ZERO;
	}

	/**
	 * Moves the position
	 * 
	 * @param x Translation x
	 * @param y Translation y
	 * @param z Translation z
	 */
	public void translate(float x, float y, float z) {
		this.position = this.position.plus(x, y, z);
	}

	/**
	 * Moves the position
	 * 
	 * @param translation Translation vector
	 */
	public void translate(Float3 translation) {
		this.position = this.position.plus(translation);
	}

	/**
	 * Get rotation.
	 * 
	 * @return A vector containing the rotation around the 3 axis
	 */
	public Float3 rotation() {
		return this.rotation;
	}

	/**
	 * Set rotation.
	 * 
	 * @param x Rotation around x axis
	 * @param y Rotation around y axis
	 * @param z Rotation around z axis
	 */
	public void setRotation(float x, float y, float z) {
		this.rotation = new Float3(x, y, z);
	}

	/**
	 * Set rotation.
	 * 
	 * @param rotation A vector containing the rotation around the 3 axis
	 */
	public void setRotation(Float3 rotation) {
		this.rotation = rotation != null ? rotation : Float3.ZERO;
	}

	/**
	 * Rotates object around its center.
	 * 
	 * @param x Rotation around x axis
	 * @param y Rotation around y axis
	 * @param z Rotation around z axis
	 */
	public void rotate(float x, float y, float z) {
		this.rotation = this.rotation.plus(x, y, z);
	}

	/**
	 * Rotates object around its center.
	 * 
	 * @param rotation A vector containing the rotation around the 3 axis
	 */
	public void rotate(Float3 rotation) {
		this.rotation = this.rotation.plus(rotation);
	}

	/**
	 * Get scale.
	 * 
	 * @return Object's scale
	 */
	public Float3 scale() {
		return this.scale;
	}

	/**
	 * Set scale.
	 * 
	 * @param x Scale x
	 * @param y Scale y
	 * @param z Scale z
	 */
	public void setScale(float x, float y, float z) {
		this.scale = new Float3(x, y, z);
	}

	/**
	 * Set scale.
	 * If the given scale is {@code null} it will be treated as (0, 0, 0).
	 * 
	 * @param scale Scale vector
	 */
	public void setScale(Float3 scale) {
		this.scale = scale != null ? scale : Float3.ZERO;
	}
}
