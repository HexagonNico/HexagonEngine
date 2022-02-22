package hexagon.engine.math.vector;

public record Float3(float x, float y, float z) implements FloatVector<Float3, Int3> {

	public static final Float3 ZERO = new Float3(0.0f, 0.0f, 0.0f);
	public static final Float3 ONE = new Float3(1.0f, 1.0f, 1.0f);
	public static final Float3 UP = new Float3(0.0f, 1.0f, 0.0f);
	public static final Float3 DOWN = new Float3(0.0f, -1.0f, 0.0f);
	public static final Float3 LEFT = new Float3(-1.0f, 0.0f, 0.0f);
	public static final Float3 RIGHT = new Float3(1.0f, 0.0f, 0.0f);
	public static final Float3 FORWARD = new Float3(0.0f, 0.0f, 1.0f);
	public static final Float3 BACK = new Float3(0.0f, 0.0f, -1.0f);

	@Override
	public Float3 plus(Float3 vector) {
		return this.plus(vector.x(), vector.y(), vector.z());
	}

	public Float3 plus(float x, float y, float z) {
		return new Float3(this.x() + x, this.y() + y, this.z() + z);
	}

	@Override
	public Float3 negative() {
		return new Float3(-this.x(), -this.y(), -this.z());
	}

	public Float3 minus(float x, float y, float z) {
		return this.plus(-x, -y, -z);
	}

	@Override
	public Float3 times(float k) {
		return new Float3(this.x() * k, this.y() * k, this.z() * k);
	}

	@Override
	public float dotProduct(Float3 vector) {
		return this.dotProduct(vector.x(), vector.y(), vector.z());
	}

	public float dotProduct(float x, float y, float z) {
		return this.x() * x + this.y() * y + this.z() * z;
	}

	// TODO - Cross product

	@Override
	public float lengthSquared() {
		return this.dotProduct(this);
	}

	@Override
	public Int3 castToInt() {
		return new Int3((int) this.x(), (int) this.y(), (int) this.z());
	}
}
