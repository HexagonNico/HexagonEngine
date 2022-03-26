package hexagon.math.vector;

public record Float3(float x, float y, float z) {
	
	public static final Float3 ZERO = new Float3(0.0f, 0.0f, 0.0f);

	public Float3 plus(float x, float y, float z) {
		return new Float3(this.x() + x, this.y() + y, this.z() + z);
	}

	public Float3 plus(Float3 v) {
		return this.plus(v.x(), v.y(), v.z());
	}

	public Float3 plus(Int3 v) {
		return this.plus(v.asFloat());
	}

	public Float3 minus(float x, float y, float z) {
		return this.plus(-x, -y, -z);
	}

	public Float3 minus(Float3 v) {
		return this.minus(v.x(), v.y(), v.z());
	}

	public Float3 minus(Int3 v) {
		return this.minus(v.asFloat());
	}

	public Float3 multiply(float k) {
		return new Float3(this.x() * k, this.y() * k, this.z() * k);
	}

	public Float3 divide(float k) {
		return this.multiply(1.0f / k);
	}

	public float dotProduct(float x, float y, float z) {
		return this.x() * x + this.y() * y + this.z() * z;
	}

	public float dotProduct(Float3 v) {
		return this.dotProduct(v.x(), v.y(), v.z());
	}

	public float dotProduct(Int3 v) {
		return this.dotProduct(v.asFloat());
	}

	public Int3 castToInt() {
		return new Int3((int) this.x(), (int) this.y(), (int) this.z());
	}
}
