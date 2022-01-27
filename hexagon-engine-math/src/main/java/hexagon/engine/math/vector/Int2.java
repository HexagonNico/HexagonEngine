package hexagon.engine.math.vector;

public record Int2(int a, int b) implements IntVector<Int2, Float2> {
	
	@Override
	public Int2 plus(Int2 vector) {
		return this.plus(vector.a(), vector.b());
	}

	public Int2 plus(int a, int b) {
		return new Int2(this.a() + a, this.b() + b);
	}

	public Float2 plus(float x, float y) {
		return new Float2(this.a() + x, this.b() + y);
	}

	@Override
	public Int2 negative() {
		return new Int2(-this.a(), -this.b());
	}

	public Int2 minus(int a, int b) {
		return this.plus(-a, -b);
	}

	public Float2 minus(float x, float y) {
		return this.plus(-x, -y);
	}

	@Override
	public Float2 asFloat() {
		return new Float2(this.a(), this.b());
	}

	@Override
	public Int2 times(int k) {
		return new Int2(this.a() * k, this.b() * k);
	}

	@Override
	public Int2 dividedBy(int k) {
		return new Int2(this.a() / k, this.b() / k);
	}

	@Override
	public int dotProduct(Int2 vector) {
		return this.dotProduct(vector.a(), vector.b());
	}

	public int dotProduct(int a, int b) {
		return this.a() * a + this.b() * b;
	}

	public float dotProduct(float x, float y) {
		return this.a() * x + this.b() * y;
	}
	
	@Override
	public int lengthSquared() {
		return this.dotProduct(this);
	}
}
