package hexagon.engine.math.vector;

public record Float2(float x, float y) implements FloatVector<Float2, Int2> {
	
	@Override
	public Float2 plus(Float2 vector) {
		return this.plus(vector.x(), vector.y());
	}

	public Float2 plus(float x, float y) {
		return new Float2(this.x() + x, this.y() + y);
	}

	@Override
	public Float2 negative() {
		return new Float2(-this.x(), -this.y());
	}

	public Float2 minus(float x, float y) {
		return this.plus(-x, -y);
	}

	@Override
	public Float2 times(float k) {
		return new Float2(this.x() * k, this.y() * k);
	}

	@Override
	public float dotProduct(Float2 vector) {
		return this.dotProduct(vector.x(), vector.y());
	}

	public float dotProduct(float x, float y) {
		return this.x() * x + this.y() * y;
	}

	@Override
	public float lengthSquared() {
		return this.dotProduct(this);
	}

	@Override
	public Int2 castToInt() {
		return new Int2((int) this.x(), (int) this.y());
	}
}
