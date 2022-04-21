package hexagon.math.geometry;

public record Size(float width, float height) {

	public static final Size ZERO = new Size(0.0f, 0.0f);
	public static final Size ONE = new Size(1.0f, 1.0f);

	public Size(float width, float height) {
		this.width = Math.max(0.0f, width);
		this.height = Math.max(0.0f, height);
	}

	public float ratio() {
		return this.width() / this.height();
	}
}
