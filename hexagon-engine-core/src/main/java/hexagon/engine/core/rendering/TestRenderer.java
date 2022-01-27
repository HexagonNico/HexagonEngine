package hexagon.engine.core.rendering;

import hexagon.engine.lwjgl.OpenGL;
import hexagon.engine.lwjgl.VertexObject;
import hexagon.engine.lwjgl.shader.Shader;
import hexagon.engine.lwjgl.shader.ShaderProgram;

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
	private final ShaderProgram shaderProgram;

	public TestRenderer() {
		this.quadModel = VertexObject.with()
				.attribute(0, VERTICES, 2)
				.create();
		this.shaderProgram = ShaderProgram.with()
				.shader(Shader.vertex("/shaders/test_vertex.glsl"))
				.shader(Shader.fragment("/shaders/test_fragment.glsl"))
				.create();
	}

	public void render() {
		OpenGL.startShader(this.shaderProgram.id);
		this.quadModel.drawTriangles(6);
		OpenGL.stopShader();
	}
}
