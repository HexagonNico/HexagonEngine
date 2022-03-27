package hexagon.math.vector;

public record Int4(int x, int y, int z, int w) {
	
	public static final Int4 ZERO = new Int4(0, 0, 0, 0);

	public Int4 plus(int x, int y, int z, int w) {
		return new Int4(this.x() + x, this.y() + y, this.z() + z, this.w() + w);
	}

	public Int4 plus(Int4 v) {
		return v != null ? this.plus(v.x(), v.y(), v.z(), v.w()) : this;
	}

	public Float4 plus(float x, float y, float z, float w) {
		return this.asFloat().plus(x, y, z, w);
	}

	public Float4 plus(Float4 v) {
		return v != null ? this.asFloat().plus(v) : this.asFloat();
	}

	public Int4 minus(int x, int y, int z, int w) {
		return this.plus(-x, -y, -z, -w);
	}

	public Int4 minus(Int4 v) {
		return v != null ? this.minus(v.x(), v.y(), v.z(), v.w()) : this;
	}

	public Float4 minus(float x, float y, float z, float w) {
		return this.asFloat().minus(x, y, z, w);
	}

	public Float4 minus(Float4 v) {
		return v != null ? this.asFloat().minus(v) : this.asFloat();
	}

	public Int4 negative() {
		return new Int4(-this.x(), -this.y(), -this.z(), -this.w());
	}

	public Int4 multiply(int k) {
		return new Int4(this.x() * k, this.y() * k, this.z() * k, this.w() * k);
	}

	public Float4 multiply(float k) {
		return new Float4(this.x() * k, this.y() * k, this.z() * k, this.w() * k);
	}

	public Int4 divide(int k) {
		return new Int4(this.x() / k, this.y() / k, this.z() / k, this.w() / k);
	}

	public Float4 divide(float k) {
		return this.multiply(1.0f / k);
	}

	public int dotProduct(int x, int y, int z, int w) {
		return this.x() * x + this.y() * y + this.z() * z + this.w() * w;
	}

	public int dotProduct(Int4 v) {
		return v != null ? this.dotProduct(v.x(), v.y(), v.z(), v.w()) : 0;
	}

	public float dotProduct(float x, float y, float z, float w) {
		return this.asFloat().dotProduct(x, y, z, w);
	}

	public float dotProduct(Float4 v) {
		return v != null ? this.asFloat().dotProduct(v) : 0.0f;
	}

	public Float4 asFloat() {
		return new Float4(this.x(), this.y(), this.z(), this.w());
	}
}
