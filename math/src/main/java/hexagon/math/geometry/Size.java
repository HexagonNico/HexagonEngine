package hexagon.math.geometry;

public record Size(float width, float height) {

	public float ratio() {
		return this.width() / this.height();
	}
}
