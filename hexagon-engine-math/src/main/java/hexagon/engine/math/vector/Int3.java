package hexagon.engine.math.vector;

public record Int3(int a, int b, int c) implements IntVector<Int3, Float3> {

	@Override
	public Int3 plus(Int3 vector) {
		return this.plus(vector.a(), vector.b(), vector.c());
	}

	public Int3 plus(int a, int b, int c) {
		return new Int3(this.a() + a, this.b() + b, this.c() + c);
	}

	public Float3 plus(float x, float y, float z) {
		return new Float3(this.a() + x, this.b() + y, this.c() + z);
	}

	@Override
	public Int3 negative() {
		return new Int3(-this.a(), -this.b(), -this.c());
	}

	public Int3 minus(int a, int b, int c) {
		return this.plus(-a, -b, -c);
	}

	public Float3 minus(float x, float y, float z) {
		return this.plus(-x, -y, -z);
	}

	@Override
	public Float3 asFloat() {
		return new Float3(this.a(), this.b(), this.c());
	}

	@Override
	public Int3 times(int k) {
		return new Int3(this.a() * k, this.b() * k, this.c() * k);
	}

	@Override
	public Int3 dividedBy(int k) {
		return new Int3(this.a() / k, this.b() / k, this.c() / k);
	}

	@Override
	public int dotProduct(Int3 vector) {
		return this.dotProduct(vector.a(), vector.b(), vector.c());
	}

	public int dotProduct(int a, int b, int c) {
		return this.a() * a + this.b() * b + this.c() * c;
	}

	public float dotProduct(float x, float y, float z) {
		return this.a() * x + this.b() * y + this.c() * z;
	}

	// TODO - Cross product
	
	@Override
	public int lengthSquared() {
		return this.dotProduct(this);
	}
}
