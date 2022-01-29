package hexagon.engine.core.rendering;

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
				.attribute(0, "vertex")
				.create();
		this.texture = Texture.getOrLoad("/textures/test.png");
		this.texture.bind();
	}

	public void render() {
		ShaderProgram.start(this.shaderProgram);
		this.shaderProgram.load("transformation_matrix", Matrices.transformation(new Float3(0.0f, 0.0f, 0.0f), new Float3(0.0f, 0.0f, 0.0f), new Float3(1.0f, 1.0f, 1.0f)));
		this.shaderProgram.load("projection_matrix", Matrices.projection(70.0f, 0.1f, 100.0f));
		this.shaderProgram.load("view_matrix", Matrices.view(new Float3(0.0f, 0.0f, 2.0f), 0.0f, 0.0f));
		this.quadModel.drawTriangles(6);
		ShaderProgram.stop();
	}
}
