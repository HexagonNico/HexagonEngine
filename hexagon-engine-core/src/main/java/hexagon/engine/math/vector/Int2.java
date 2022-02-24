package hexagon.engine.math.vector;

/**
 * Record that represents a 2D it vector with components [a, b]
 * 
 * @author Nico
 */
public record Int2(int a, int b) implements IntVector<Int2, Float2> {
	
	/**Shorthand for {@code new Int2(0, 0)} */
	public static final Int2 ZERO = new Int2(0, 0);
	/**Shorthand for {@code new Int2(1, 1)} */
	public static final Int2 ONE = new Int2(1, 1);
	/**Shorthand for {@code new Int2(0, 1)} */
	public static final Int2 UP = new Int2(0, 1);
	/**Shorthand for {@code new Int2(0, -1)} */
	public static final Int2 DOWN = new Int2(0, -1);
	/**Shorthand for {@code new Int2(-1, 0)} */
	public static final Int2 LEFT = new Int2(-1, 0);
	/**Shorthand for {@code new Int2(1, 0)} */
	public static final Int2 RIGHT = new Int2(1, 0);
	
	@Override
	public Int2 plus(Int2 vector) {
		return this.plus(vector.a(), vector.b());
	}

	/**
	 * Sums this vector and the vector [a, b]
	 * 
	 * @param a First component of the vector
	 * @param b Second component of the vector
	 * 
	 * @return The sum of this vector and the vector [a, b]
	 */
	public Int2 plus(int a, int b) {
		return new Int2(this.a() + a, this.b() + b);
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
		return new Float2(this.a() + x, this.b() + y);
	}

	@Override
	public Int2 negative() {
		return new Int2(-this.a(), -this.b());
	}

	/**
	 * Subtracts the vector [a, b] from this vector 
	 * 
	 * @param a First component of the vector
	 * @param b Second component of the vector
	 * 
	 * @return The difference between this vector and the vector [a, b]
	 */
	public Int2 minus(int a, int b) {
		return this.plus(-a, -b);
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

	/**
	 * Computes the dot product between this vector and the vector [a, b]
	 * 
	 * @param a First component of the vector
	 * @param b Second component of the vector
	 * 
	 * @return The dot product between this vector and the vector [a, b]
	 */
	public int dotProduct(int a, int b) {
		return this.a() * a + this.b() * b;
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
		return this.a() * x + this.b() * y;
	}
	
	@Override
	public int lengthSquared() {
		return this.dotProduct(this);
	}
}
