package hexagon.core.components;

import hexagon.core.base.GameEntity;
import hexagon.math.matrix.Matrices;
import hexagon.math.matrix.Matrix4;
import hexagon.math.vector.Float3;
import hexagon.utils.json.JsonObject;

public final class Transform3D extends Transform {

	private Float3 position = Float3.ZERO;
	private Float3 rotation = Float3.ZERO;
	private Float3 scale = Float3.ONE;

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
	public Matrix4 matrix() {
		return Matrices.scaling(this.scale)
				.multiply(Matrices.rotationDegrees(this.rotation))
				.multiply(Matrices.translation(this.position).transposed());
	}

	public Float3 position() {
		return this.position;
	}

	public void setPosition(float x, float y, float z) {
		this.position = new Float3(x, y, z);
	}

	public void setPosition(Float3 position) {
		this.position = position != null ? position : Float3.ZERO;
	}

	public void translate(float x, float y, float z) {
		this.position = this.position.plus(x, y, z);
	}

	public void translate(Float3 translation) {
		this.position = this.position.plus(translation);
	}

	public Float3 rotation() {
		return this.rotation;
	}

	public void setRotation(float x, float y, float z) {
		this.rotation = new Float3(x, y, z);
	}

	public void setRotation(Float3 rotation) {
		this.rotation = rotation != null ? rotation : Float3.ZERO;
	}

	public void rotate(float x, float y, float z) {
		this.rotation = this.rotation.plus(x, y, z);
	}

	public void rotate(Float3 rotation) {
		this.rotation = this.rotation.plus(rotation);
	}

	public Float3 scale() {
		return this.scale;
	}

	public void setScale(float x, float y, float z) {
		this.scale = new Float3(x, y, z);
	}

	public void setScale(Float3 scale) {
		this.scale = scale != null ? scale : Float3.ZERO;
	}
}
