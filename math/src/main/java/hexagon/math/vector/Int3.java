package hexagon.math.vector;

public record Int3(int x, int y, int z) {
	
	public static final Int3 ZERO = new Int3(0, 0, 0);

	public Int3 plus(int x, int y, int z) {
		return new Int3(this.x() + x, this.y() + y, this.z() + z);
	}

	public Int3 plus(Int3 v) {
		return v != null ? this.plus(v.x(), v.y(), v.z()) : this;
	}

	public Float3 plus(float x, float y, float z) {
		return this.asFloat().plus(x, y, z);
	}

	public Float3 plus(Float3 v) {
		return v != null ? this.asFloat().plus(v) : this.asFloat();
	}

	public Int3 minus(int x, int y, int z) {
		return this.plus(-x, -y, -z);
	}

	public Int3 minus(Int3 v) {
		return v != null ? this.minus(v.x(), v.y(), v.z()) : this;
	}

	public Float3 minus(float x, float y, float z) {
		return this.asFloat().minus(x, y, z);
	}

	public Float3 minus(Float3 v) {
		return v != null ? this.asFloat().minus(v) : this.asFloat();
	}

	public Int3 negative() {
		return new Int3(-this.x(), -this.y(), -this.z());
	}

	public Int3 multiply(int k) {
		return new Int3(this.x() * k, this.y() * k, this.z() * k);
	}

	public Float3 multiply(float k) {
		return new Float3(this.x() * k, this.y() * k, this.z() * k);
	}

	public Int3 divide(int k) {
		return new Int3(this.x() / k, this.y() / k, this.z() / k);
	}

	public Float3 divide(float k) {
		return this.multiply(1.0f / k);
	}

	public int dotProduct(int x, int y, int z) {
		return this.x() * x + this.y() * y + this.z() * z;
	}

	public int dotProduct(Int3 v) {
		return v != null ? this.dotProduct(v.x(), v.y(), v.z()) : 0;
	}

	public float dotProduct(float x, float y, float z) {
		return this.asFloat().dotProduct(x, y, z);
	}

	public float dotProduct(Float3 v) {
		return v != null ? this.asFloat().dotProduct(v) : 0.0f;
	}

	public Float3 asFloat() {
		return new Float3(this.x(), this.y(), this.z());
	}
}
