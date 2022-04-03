package hexagon.core.components;

import hexagon.math.matrix.Matrices;
import hexagon.math.matrix.Matrix4;
import hexagon.math.vector.Float2;
import hexagon.utils.json.JsonObject;

/**
 * Component that holds transformations in a 2D space.
 * 
 * @author Nico
 */
public final class Transform2D extends Transform {

	/**Position in a 2D space */
	private Float2 position = Float2.ZERO;
	/**Rotation angle in degrees */
	private float rotation = 0.0f;
	/**Scaling on the two axis */
	private Float2 scale = Float2.ONE;

	@Override
	public void init(JsonObject jsonObject) {
		jsonObject.getObject("position").ifPresent(positionJson -> {
			float x = positionJson.getFloat("x", this.position.x());
			float y = positionJson.getFloat("y", this.position.y());
			this.position = new Float2(x, y);
		});
		this.rotation = jsonObject.getFloat("rotation", 0.0f);
		jsonObject.getObject("scale").ifPresent(scaleJson -> {
			float x = scaleJson.getFloat("x", this.scale.x());
			float y = scaleJson.getFloat("y", this.scale.y());
			this.scale = new Float2(x, y);
		});
	}

	@Override
	public Matrix4 matrix() {
		return Matrices.scaling(scale)
				.multiply(Matrices.zRotationDegrees(this.rotation))
				.multiply(Matrices.translation(this.position).transposed());
	}

	/**
	 * Get position.
	 * 
	 * @return This transform's position
	 */
	public Float2 position() {
		return this.position;
	}

	/**
	 * Set position.
	 * 
	 * @param x Position x
	 * @param y Position y
	 */
	public void setPosition(float x, float y) {
		this.position = new Float2(x, y);
	}

	/**
	 * Set position.
	 * If the given position is {@code null} it will be treated as (0, 0)
	 * 
	 * @param position New position
	 */
	public void setPosition(Float2 position) {
		this.position = position != null ? position : Float2.ZERO;
	}

	/**
	 * Moves the position
	 * 
	 * @param x Translation x
	 * @param y Translation y
	 */
	public void translate(float x, float y) {
		this.position = this.position.plus(x, y);
	}

	/**
	 * Moves the position
	 * 
	 * @param translation Translation vector
	 */
	public void translate(Float2 translation) {
		this.position = this.position.plus(translation);
	}

	/**
	 * Get rotation.
	 * 
	 * @return Rotation angle in degrees
	 */
	public float rotation() {
		return this.rotation;
	}

	/**
	 * Set rotation.
	 * 
	 * @param degrees Rotation angle in degrees
	 */
	public void setRotation(float degrees) {
		this.rotation = degrees;
	}

	/**
	 * Rotates object around its center.
	 * 
	 * @param degrees Rotation angle in degrees
	 */
	public void rotate(float degrees) {
		this.rotation += degrees;
	}

	/**
	 * Get scale.
	 * 
	 * @return Object's scale
	 */
	public Float2 scale() {
		return this.scale;
	}

	/**
	 * Set scale.
	 * 
	 * @param x Scale x
	 * @param y Scale y
	 */
	public void setScale(float x, float y) {
		this.scale = new Float2(x, y);
	}

	/**
	 * Set scale.
	 * If the given scale is {@code null} it will be treated as (0, 0).
	 * 
	 * @param scale Scale vector
	 */
	public void setScale(Float2 scale) {
		this.scale = scale != null ? scale : Float2.ZERO;
	}
}
