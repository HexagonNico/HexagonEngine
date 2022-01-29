package hexagon.engine.core.rendering;

import java.util.ArrayList;
import java.util.HashMap;

import hexagon.engine.core.components.SpriteComponent;
import hexagon.engine.core.components.Transform3D;
import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.core.ecs.GameSystem;
import hexagon.engine.lwjgl.DrawCalls;
import hexagon.engine.lwjgl.VertexObject;
import hexagon.engine.lwjgl.shader.Shader;
import hexagon.engine.lwjgl.shader.ShaderProgram;
import hexagon.engine.lwjgl.texture.Texture;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.vector.Float3;

public final class TestRenderingSystem extends GameSystem {
	
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
	private final ShaderProgram shader;

	private final HashMap<Texture, ArrayList<Transform3D>> batch;

	public TestRenderingSystem() {
		super(Transform3D.class, SpriteComponent.class);
		this.cubeModel = VertexObject.with()
			.attribute(0, VERTICES, 3)
			.indices(INDICES)
			.create();
		this.shader = ShaderProgram.with()
			.shader(Shader.vertex("/shaders/test_vertex.glsl"))
			.shader(Shader.fragment("/shaders/test_fragment.glsl"))
			.attribute(0, "vertex")
			.create();
		ShaderProgram.start(this.shader);
		this.shader.load("projection_matrix", Matrices.projection(70.0f, 0.1f, 100.0f));
		this.shader.load("view_matrix", Matrices.view(new Float3(0.0f, 0.0f, 5.0f), 0.0f, 0.0f));
		ShaderProgram.stop();
		this.batch = new HashMap<>();
	}

	@Override
	protected void beforeAll() {}

	@Override
	protected void process(GameEntity entity) {
		Texture texture = entity.getComponent(SpriteComponent.class).texture;
		Transform3D transform = entity.getComponent(Transform3D.class);
		if(this.batch.containsKey(texture)) {
			this.batch.get(texture).add(transform);
		} else {
			ArrayList<Transform3D> list = new ArrayList<>();
			list.add(transform);
			this.batch.put(texture, list);
		}
	}

	@Override
	protected void afterAll() {
		ShaderProgram.start(this.shader);
		this.cubeModel.activate(() -> {
			this.batch.forEach((texture, list) -> {
				texture.bind();
				list.forEach(transform -> {
					transform.rotation = transform.rotation.plus(0.0f, 0.1f, 0.0f);
					this.shader.load("transformation_matrix", transform.matrix());
					DrawCalls.drawElements(INDICES.length);
				});
			});
		});
		ShaderProgram.stop();
		this.batch.clear();
	}
}
