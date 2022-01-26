package hexagon.engine.core.rendering;

import hexagon.engine.lwjgl.VertexObject;

public final class TestRenderer {
	
	public static final float[] VERTICES = {
			// Left bottom triangle
			-0.5f, 0.5f,
			-0.5f, -0.5f,
			0.5f, -0.5f,
			// Right top triangle
			0.5f, -0.5f,
			0.5f, 0.5f,
			-0.5f, 0.5f
	};

	private final VertexObject quadModel;

	public TestRenderer() {
		this.quadModel = VertexObject.with().attribute(0, VERTICES, 2).create();
	}

	public void render() {
		this.quadModel.drawTriangles(6);
	}
}
