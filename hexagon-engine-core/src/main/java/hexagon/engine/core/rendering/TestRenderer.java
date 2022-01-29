package hexagon.engine.core.rendering;

import hexagon.engine.lwjgl.VertexObject;
import hexagon.engine.lwjgl.shader.Shader;
import hexagon.engine.lwjgl.shader.ShaderProgram;
import hexagon.engine.lwjgl.texture.Texture;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.vector.Float3;

public final class TestRenderer {
	
	public static final float[] VERTICES = {
		-0.5f,0.5f,0,
		-0.5f,-0.5f,0,
		0.5f,-0.5f,0,
		0.5f,0.5f,0,

		-0.5f,0.5f,1,
		-0.5f,-0.5f,1,
		0.5f,-0.5f,1,
		0.5f,0.5f,1,

		0.5f,0.5f,0,
		0.5f,-0.5f,0,
		0.5f,-0.5f,1,
		0.5f,0.5f,1,

		-0.5f,0.5f,0,
		-0.5f,-0.5f,0,
		-0.5f,-0.5f,1,
		-0.5f,0.5f,1,
		
		-0.5f,0.5f,1,
		-0.5f,0.5f,0,
		0.5f,0.5f,0,
		0.5f,0.5f,1,
		
		-0.5f,-0.5f,1,
		-0.5f,-0.5f,0,
		0.5f,-0.5f,0,
		0.5f,-0.5f,1
	};

	public static final int[] INDICES = new int[] {
		0,1,3,
		3,1,2,
		4,5,7,
		7,5,6,
		8,9,11,
		11,9,10,
		12,13,15,
		15,13,14,
		16,17,19,
		19,17,18,
		20,21,23,
		23,21,22
	};

	private final VertexObject cubeModel;
	private final ShaderProgram shaderProgram;
	private final Texture texture;

	private Float3 rotation = new Float3(0.0f, 0.0f, 0.0f);

	public TestRenderer() {
		this.cubeModel = VertexObject.with()
				.attribute(0, VERTICES, 3)
				.indices(INDICES)
				.create();
		this.shaderProgram = ShaderProgram.with()
				.shader(Shader.vertex("/shaders/test_vertex.glsl"))
				.shader(Shader.fragment("/shaders/test_fragment.glsl"))
				.attribute(0, "vertex")
				.create();
		this.texture = Texture.getOrLoad("/textures/test.png");
		this.texture.bind();
		ShaderProgram.start(this.shaderProgram);
		this.shaderProgram.load("projection_matrix", Matrices.projection(70.0f, 0.1f, 100.0f));
		this.shaderProgram.load("view_matrix", Matrices.view(new Float3(0.0f, 0.0f, 2.0f), 0.0f, 0.0f));
		ShaderProgram.stop();
	}

	public void render() {
		ShaderProgram.start(this.shaderProgram);
		this.rotation = this.rotation.plus(0.0f, 0.1f, 0.0f);
		this.shaderProgram.load("transformation_matrix", Matrices.transformation(new Float3(0.0f, 0.0f, 0.0f), rotation, new Float3(1.0f, 1.0f, 1.0f)));
		this.cubeModel.drawElements(INDICES.length);
		ShaderProgram.stop();
	}
}
