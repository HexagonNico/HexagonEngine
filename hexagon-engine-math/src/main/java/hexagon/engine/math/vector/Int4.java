package hexagon.engine.math.vector;

public record Int4(int a, int b, int c, int d) implements IntVector<Int4, Float4> {
	
	@Override
	public Int4 plus(Int4 vector) {
		return this.plus(vector.a(), vector.b(), vector.c(), vector.d());
	}

	public Int4 plus(int a, int b, int c, int d) {
		return new Int4(this.a() + a, this.b() + b, this.c() + c, this.d() + d);
	}

	public Float4 plus(float x, float y, float z, float w) {
		return new Float4(this.a() + x, this.b() + y, this.c() + z, this.d() + w);
	}

	@Override
	public Int4 negative() {
		return new Int4(-this.a(), -this.b(), -this.c(), -this.d());
	}

	public Int4 minus(int a, int b, int c, int d) {
		return this.plus(-a, -b, -c, -d);
	}

	public Float4 minus(float x, float y, float z, float w) {
		return this.plus(-x, -y, -z, -w);
	}

	@Override
	public Float4 asFloat() {
		return new Float4(this.a(), this.b(), this.c(), this.d());
	}

	@Override
	public Int4 times(int k) {
		return new Int4(this.a() * k, this.b() * k, this.c() * k, this.d() * k);
	}

	@Override
	public Int4 dividedBy(int k) {
		return new Int4(this.a() / k, this.b() / k, this.c() / k, this.d() / k);
	}

	@Override
	public int dotProduct(Int4 vector) {
		return this.dotProduct(vector.a(), vector.b(), vector.c(), vector.d());
	}

	public int dotProduct(int a, int b, int c, int d) {
		return this.a() * a + this.b() * b + this.c() * c + this.d() * d;
	}

	public float dotProduct(float x, float y, float z, float w) {
		return this.a() * x + this.b() * y + this.c() * z + this.d() * w;
	}
	
	@Override
	public int lengthSquared() {
		return this.dotProduct(this);
	}
}
