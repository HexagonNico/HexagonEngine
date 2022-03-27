package hexagon.math.vector;

public record Float2(float x, float y) {
	
	public static final Float2 ZERO = new Float2(0.0f, 0.0f);
	public static final Float2 ONE = new Float2(1.0f, 1.0f);

	public Float2 plus(float x, float y) {
		return new Float2(this.x() + x, this.y() + y);
	}

	public Float2 plus(Float2 v) {
		return v != null ? this.plus(v.x(), v.y()) : this;
	}

	public Float2 plus(Int2 v) {
		return v != null ? this.plus(v.asFloat()) : this;
	}

	public Float2 minus(float x, float y) {
		return this.plus(-x, -y);
	}

	public Float2 minus(Float2 v) {
		return v != null ? this.minus(v.x(), v.y()) : this;
	}

	public Float2 minus(Int2 v) {
		return v != null ? this.minus(v.asFloat()) : this;
	}

	public Float2 negative() {
		return new Float2(-this.x(), -this.y());
	}

	public Float2 multiply(float k) {
		return new Float2(this.x() * k, this.y() * k);
	}

	public Float2 divide(float k) {
		return this.multiply(1.0f / k);
	}

	public float dotProduct(float x, float y) {
		return this.x() * x + this.y() * y;
	}

	public float dotProduct(Float2 v) {
		return v != null ? this.dotProduct(v.x(), v.y()) : 0.0f;
	}

	public float dotProduct(Int2 v) {
		return v != null ? this.dotProduct(v.asFloat()) : 0.0f;
	}

	public Int2 castToInt() {
		return new Int2((int) this.x(), (int) this.y());
	}
}
