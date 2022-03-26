package hexagon.math.vector;

public record Float4(float x, float y, float z, float w) {
	
	public static final Float4 ZERO = new Float4(0.0f, 0.0f, 0.0f, 0.0f);

	public Float4 plus(float x, float y, float z, float w) {
		return new Float4(this.x() + x, this.y() + y, this.z() + z, this.w() + w);
	}

	public Float4 plus(Float4 v) {
		return v != null ? this.plus(v.x(), v.y(), v.z(), v.w()) : this;
	}

	public Float4 plus(Int4 v) {
		return v != null ? this.plus(v.asFloat()) : this;
	}

	public Float4 minus(float x, float y, float z, float w) {
		return this.plus(-x, -y, -z, -w);
	}

	public Float4 minus(Float4 v) {
		return v != null ? this.minus(v.x(), v.y(), v.z(), v.w()) : this;
	}

	public Float4 minus(Int4 v) {
		return v != null ? this.minus(v.asFloat()) : this;
	}

	public Float4 multiply(float k) {
		return new Float4(this.x() * k, this.y() * k, this.z() * k, this.w() * k);
	}

	public Float4 divide(float k) {
		return this.multiply(1.0f / k);
	}

	public float dotProduct(float x, float y, float z, float w) {
		return this.x() * x + this.y() * y + this.z() * z + this.w() * w;
	}

	public float dotProduct(Float4 v) {
		return v != null ? this.dotProduct(v.x(), v.y(), v.z(), v.w()) : 0.0f;
	}

	public float dotProduct(Int4 v) {
		return v != null ? this.dotProduct(v.asFloat()) : 0.0f;
	}

	public Int4 castToInt() {
		return new Int4((int) this.x(), (int) this.y(), (int) this.z(), (int) this.w());
	}
}
