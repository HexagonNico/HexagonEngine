package hexagon.engine.ecs.components;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float2;
import hexagon.engine.math.vector.Float3;
import hexagon.engine.utils.json.JsonObject;

/**
 * Component that holds a 2D transformation.
 * 
 * @author Nico
 */
public class Transform2D extends Component {
	
	protected float px = 0.0f;
	protected float py = 0.0f;

	protected float rx = 0.0f;
	protected float ry = 0.0f;

	protected float sx = 1.0f;
	protected float sy = 1.0f;

	public Transform2D(GameEntity entity) {
		super(entity);
	}

	@Override
	protected void init(JsonObject jsonObject) {
		JsonObject positionJson = jsonObject.getObject("position").orElse(JsonObject.empty());
		this.px = positionJson.getFloat("x").orElse(0.0f);
		this.py = positionJson.getFloat("y").orElse(0.0f);
		JsonObject rotationJson = jsonObject.getObject("rotation").orElse(JsonObject.empty());
		this.rx = rotationJson.getFloat("x").orElse(0.0f);
		this.ry = rotationJson.getFloat("y").orElse(0.0f);
		JsonObject scaleJson = jsonObject.getObject("scale").orElse(JsonObject.empty());
		this.sx = scaleJson.getFloat("x").orElse(1.0f);
		this.sy = scaleJson.getFloat("y").orElse(1.0f);
	}

	// TODO - Static method for default matrix

	/**
	 * Computes the transformation matrix using position, rotation and scale in this component.
	 * 
	 * @return The transformation matrix.
	 */
	public Matrix4 matrix() {
		return Matrices.transformation(
			new Float3(this.px, this.py, 0.0f),
			new Float3(this.rx, this.ry, 0.0f),
			new Float3(this.sx, this.sy, 1.0f)
		);
	}

	public Float2 position2D() {
		return new Float2(this.px, this.py);
	}

	public Float2 rotation2D() {
		return new Float2(this.rx, this.ry);
	}

	public Float2 scale2D() {
		return new Float2(this.sx, this.sy);
	}

	public void setPosition(float x, float y) {
		this.px = x;
		this.py = y;
	}

	public void setRotation(float x, float y) {
		this.rx = x;
		this.ry = y;
	}

	public void setScale(float x, float y) {
		this.sx = x;
		this.sy = y;
	}

	public void setPosition(Float2 vector) {
		this.setPosition(vector.x(), vector.y());
	}

	public void setRotation(Float2 vector) {
		this.setRotation(vector.x(), vector.y());
	}

	public void setScale(Float2 vector) {
		this.setScale(vector.x(), vector.y());
	}

	public void translate(float x, float y) {
		this.px += x;
		this.py += y;
	}

	public void rotate(float x, float y) {
		this.rx += x;
		this.ry += y;
	}

	public void rescale(float x, float y) {
		this.sx *= x;
		this.sy *= y;
	}

	public void translate(Float2 vector) {
		this.translate(vector.x(), vector.y());
	}

	public void rotate(Float2 vector) {
		this.rotate(vector.x(), vector.y());
	}

	public void rescale(Float2 vector) {
		this.rescale(vector.x(), vector.y());
	}
}
