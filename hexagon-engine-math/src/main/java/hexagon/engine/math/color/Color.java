package hexagon.engine.math.color;

import hexagon.engine.math.MathExtra;

public record Color(float r, float g, float b, float a) {
	
	public Color(float r, float g, float b, float a) {
		this.r = MathExtra.clamp(r, 0.0f, 1.0f);
		this.g = MathExtra.clamp(g, 0.0f, 1.0f);
		this.b = MathExtra.clamp(b, 0.0f, 1.0f);
		this.a = MathExtra.clamp(a, 0.0f, 1.0f);
	}

	public Color(float r, float g, float b) {
		this(r, g, b, 1.0f);
	}

	public Color(int r, int g, int b, int a) {
		this(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
	}

	public Color(int r, int g, int b) {
		this(r, g, b, 255);
	}
}
