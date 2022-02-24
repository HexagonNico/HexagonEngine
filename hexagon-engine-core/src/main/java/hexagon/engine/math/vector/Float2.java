package hexagon.engine.math.vector;

/**
 * Record that represents a 2D float vector with components [x, y]
 * 
 * @author Nico
 */
public record Float2(float x, float y) implements FloatVector<Float2, Int2> {
	
	/**Shorthand for {@code new Float2(0.0f, 0.0f)} */
	public static final Float2 ZERO = new Float2(0.0f, 0.0f);
	/**Shorthand for {@code new Float2(1.0f, 1.0f)} */
	public static final Float2 ONE = new Float2(1.0f, 1.0f);
	/**Shorthand for {@code new Float2(0.0f, 1.0f)} */
	public static final Float2 UP = new Float2(0.0f, 1.0f);
	/**Shorthand for {@code new Float2(0.0f, -1.0f)} */
	public static final Float2 DOWN = new Float2(0.0f, -1.0f);
	/**Shorthand for {@code new Float2(-1.0f, 0.0f)} */
	public static final Float2 LEFT = new Float2(-1.0f, 0.0f);
	/**Shorthand for {@code new Float2(1.0f, 0.0f)} */
	public static final Float2 RIGHT = new Float2(1.0f, 0.0f);

	@Override
	public Float2 plus(Float2 vector) {
		return this.plus(vector.x(), vector.y());
	}

	/**
	 * Sums this vector and the vector [x, y]
	 * 
	 * @param x First component of the vector
	 * @param y Second component of the vector
	 * 
	 * @return The sum of this vector and the vector [x, y]
	 */
	public Float2 plus(float x, float y) {
		return new Float2(this.x() + x, this.y() + y);
	}

	@Override
	public Float2 negative() {
		return new Float2(-this.x(), -this.y());
	}

	/**
	 * Subtracts the vector [x, y] from this vector 
	 * 
	 * @param x First component of the vector
	 * @param y Second component of the vector
	 * 
	 * @return The difference between this vector and the vector [x, y]
	 */
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

	/**
	 * Computes the dot product between this vector and the vector [x, y]
	 * 
	 * @param x First component of the vector
	 * @param y Second component of the vector
	 * 
	 * @return The dot product between this vector and the vector [x, y]
	 */
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
