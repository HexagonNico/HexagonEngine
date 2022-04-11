package hexagon.math.geometry;

public record SizeFloat(float width, float height) {

	public float ratio() {
		return this.width() / this.height();
	}
}
