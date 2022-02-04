package hexagon.engine.core.systems;

import java.util.ArrayList;
import java.util.HashMap;

import hexagon.engine.core.components.SpriteComponent;
import hexagon.engine.core.ecs.GameSystem;
import hexagon.engine.lwjgl.opengl.DrawCalls;
import hexagon.engine.lwjgl.opengl.Shader;
import hexagon.engine.lwjgl.opengl.ShaderProgram;
import hexagon.engine.lwjgl.opengl.Texture;
import hexagon.engine.lwjgl.opengl.VertexObject;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.vector.Float3;

public final class SpriteRenderer extends GameSystem<SpriteComponent> {

	/**Vertex object storing a quad model */
	private final VertexObject model;
	/**Shader program used for this renderer */
	private final ShaderProgram shader;
	/**Map that stores entities in batches to render them more efficiently */
	private final HashMap<Texture, ArrayList<SpriteComponent>> renderBatch;

	public SpriteRenderer() {
		super(SpriteComponent.class);
		this.model = VertexObject.with()
			.attribute(0, new float[] {-0.5f,0.5f, -0.5f,-0.5f, 0.5f,-0.5f, 0.5f,0.5f}, 2)
			.indices(new int[] {0,1,3, 3,1,2})
			.create();
		this.shader = ShaderProgram.with()
			.shader(Shader.vertex("/shaders/vertex/sprite_shader.glsl"))
			.shader(Shader.fragment("/shaders/fragment/texture_shader.glsl"))
			.attribute(0, "vertex")
			.attribute(1, "uv")
			.create();
		this.renderBatch = new HashMap<>();
	}

	@Override
	protected void beforeAll() {}

	@Override
	protected void process(SpriteComponent sprite) {
		if(this.renderBatch.containsKey(sprite.texture)) {
			this.renderBatch.get(sprite.texture).add(sprite);
		} else {
			ArrayList<SpriteComponent> batch = new ArrayList<>();
			batch.add(sprite);
			this.renderBatch.put(sprite.texture, batch);
		}
	}

	@Override
	protected void afterAll() {
		this.model.activate(() -> {
			ShaderProgram.start(this.shader);
			// TODO - 2D camera
			this.shader.load("projection_matrix", Matrices.projection(70.0f, 0.1f, 1000.0f).asList());
			this.shader.load("view_matrix", Matrices.view(new Float3(0, 0, 5), 0, 0).asList());
			this.renderBatch.forEach((texture, sprites) -> {
				texture.bind();
				sprites.forEach(sprite -> {
					this.shader.load("transformation_matrix", sprite.transformationMatrix().asList());
					this.shader.load("uv", sprite.uv.x(), sprite.uv.y());
					this.shader.load("size", sprite.size.x(), sprite.size.y());
					DrawCalls.drawElements(6);
				});
			});
			ShaderProgram.stop();
		});
		this.renderBatch.clear();
	}
}
