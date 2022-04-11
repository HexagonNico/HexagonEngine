package hexagon.math.geometry;

public record SizeInt(int width, int height) {

	public float ratio() {
		return (float) this.width() / this.height();
	}
}
