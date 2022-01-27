package hexagon.engine.core.rendering;

import hexagon.engine.lwjgl.OpenGL;
import hexagon.engine.lwjgl.VertexObject;
import hexagon.engine.lwjgl.shader.Shader;
import hexagon.engine.lwjgl.shader.ShaderProgram;
import hexagon.engine.lwjgl.texture.Texture;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.vector.Float3;

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
	private final Texture texture;

	public TestRenderer() {
		this.quadModel = VertexObject.with()
				.attribute(0, VERTICES, 2)
				.create();
		this.shaderProgram = ShaderProgram.with()
				.shader(Shader.vertex("/shaders/test_vertex.glsl"))
				.shader(Shader.fragment("/shaders/test_fragment.glsl"))
				.create();
		this.texture = Texture.getOrLoad("/textures/test.png");
		OpenGL.bindTexture(this.texture.id);
	}

	public void render() {
		OpenGL.startShader(this.shaderProgram.id);
		this.shaderProgram.load("transformation_matrix", Matrices.transformation(new Float3(-0.25f, 0.0f, 0.0f), new Float3(0.0f, 0.0f, 0.0f), new Float3(1.5f, 1.5f, 1.5f)));
		this.quadModel.drawTriangles(6);
		OpenGL.stopShader();
	}
}
