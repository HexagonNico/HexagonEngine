package hexagon.engine.ecs.components;

import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float3;
import hexagon.engine.utils.json.JsonObject;

/**
 * Component that holds a 3D transformation.
 * 
 * @author Nico
 */
public class Transform3D extends Transform2D {
	
	protected float pz = 0.0f;
	protected float rz = 0.0f;
	protected float sz = 1.0f;

	public Transform3D(GameEntity entity) {
		super(entity);
	}

	@Override
	protected void init(JsonObject jsonObject) {
		super.init(jsonObject);
		JsonObject positionJson = jsonObject.getObject("position").orElse(JsonObject.empty());
		JsonObject rotationJson = jsonObject.getObject("rotation").orElse(JsonObject.empty());
		JsonObject scaleJson = jsonObject.getObject("scale").orElse(JsonObject.empty());
		this.pz = positionJson.getFloat("z").orElse(0.0f);
		this.rz = rotationJson.getFloat("z").orElse(0.0f);
		this.sz = scaleJson.getFloat("z").orElse(1.0f);
	}

	// TODO - Static method for default matrix

	@Override
	public Matrix4 matrix() {
		return Matrices.transformation(this.position3D(), this.rotation3D(), this.scale3D());
	}

	public Float3 position3D() {
		return new Float3(super.px, super.py, this.pz);
	}

	public Float3 rotation3D() {
		return new Float3(super.rx, super.ry, this.rz);
	}

	public Float3 scale3D() {
		return new Float3(super.sx, super.sy, this.sz);
	}

	public void setPosition(float x, float y, float z) {
		super.setPosition(x, y);
		this.pz = z;
	}

	public void setRotation(float x, float y, float z) {
		super.setRotation(x, y);
		this.rz = z;
	}

	public void setScale(float x, float y, float z) {
		super.setScale(x, y);
		this.sz = z;
	}

	public void setPosition(Float3 vector) {
		this.setPosition(vector.x(), vector.y(), vector.z());
	}

	public void setRotation(Float3 vector) {
		this.setRotation(vector.x(), vector.y(), vector.z());
	}

	public void setScale(Float3 vector) {
		this.setScale(vector.x(), vector.y(), vector.z());
	}

	public void translate(float x, float y, float z) {
		super.translate(x, y);
		this.pz += z;
	}

	public void rotate(float x, float y, float z) {
		super.rotate(x, y);
		this.rz += z;
	}

	public void rescale(float x, float y, float z) {
		super.rescale(x, y);
		this.sz *= z;
	}

	public void translate(Float3 vector) {
		this.translate(vector.x(), vector.y(), vector.z());
	}

	public void rotate(Float3 vector) {
		this.rotate(vector.x(), vector.y(), vector.z());
	}

	public void rescale(Float3 vector) {
		this.rescale(vector.x(), vector.y(), vector.z());
	}
}
