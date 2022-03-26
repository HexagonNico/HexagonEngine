package hexagon.math.vector;

public record Int2(int x, int y) {
	
	public static final Int2 ZERO = new Int2(0, 0);

	public Int2 plus(int x, int y) {
		return new Int2(this.x() + x, this.y() + y);
	}

	public Int2 plus(Int2 v) {
		return this.plus(v.x(), v.y());
	}

	public Float2 plus(float x, float y) {
		return this.asFloat().plus(x, y);
	}

	public Float2 plus(Float2 v) {
		return this.asFloat().plus(v);
	}

	public Int2 minus(int x, int y) {
		return this.plus(-x, -y);
	}

	public Int2 minus(Int2 v) {
		return this.minus(v.x(), v.y());
	}

	public Float2 minus(float x, float y) {
		return this.asFloat().minus(x, y);
	}

	public Float2 minus(Float2 v) {
		return this.asFloat().minus(v);
	}

	public Int2 multiply(int k) {
		return new Int2(this.x() * k, this.y() * k);
	}

	public Float2 multiply(float k) {
		return new Float2(this.x() * k, this.y() * k);
	}

	public Int2 divide(int k) {
		return new Int2(this.x() / k, this.y() / k);
	}

	public Float2 divide(float k) {
		return this.multiply(1.0f / k);
	}

	public int dotProduct(int x, int y) {
		return this.x() * x + this.y() * y;
	}

	public int dotProduct(Int2 v) {
		return this.dotProduct(v.x(), v.y());
	}

	public float dotProduct(float x, float y) {
		return this.asFloat().dotProduct(x, y);
	}

	public float dotProduct(Float2 v) {
		return this.asFloat().dotProduct(v);
	}

	public Float2 asFloat() {
		return new Float2(this.x(), this.y());
	}
}
