package hexagon.engine.ecs.systems;

import java.util.ArrayList;
import java.util.HashMap;

import hexagon.engine.ecs.GameSystem;
import hexagon.engine.ecs.components.Camera;
import hexagon.engine.ecs.components.SpriteComponent;
import hexagon.engine.opengl.DrawCalls;
import hexagon.engine.opengl.ShaderProgram;
import hexagon.engine.opengl.Texture;
import hexagon.engine.opengl.VertexObject;

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
			.vertexShader("/shaders/vertex/sprite_shader.glsl")
			.fragmentShader("/shaders/fragment/texture_shader.glsl")
			.attribute(0, "vertex")
			.attribute(1, "uv")
			.create();
		this.renderBatch = new HashMap<>();
	}

	@Override
	protected void beforeAll() {
		this.renderBatch.clear();
	}

	@Override
	protected void process(SpriteComponent sprite) {
		if(this.renderBatch.containsKey(sprite.getTexture())) {
			this.renderBatch.get(sprite.getTexture()).add(sprite);
		} else {
			ArrayList<SpriteComponent> batch = new ArrayList<>();
			batch.add(sprite);
			this.renderBatch.put(sprite.getTexture(), batch);
		}
	}

	@Override
	protected void afterAll() {
		this.model.activate(() -> {
			ShaderProgram.start(this.shader);
			Camera.main().ifPresent(camera -> {
				this.shader.load("projection_matrix", camera.projectionMatrix());
				this.shader.load("view_matrix", camera.viewMatrix());
			});
			this.renderBatch.forEach((texture, sprites) -> {
				texture.bind();
				sprites.forEach(sprite -> {
					this.shader.load("transformation_matrix", sprite.transformationMatrix());
					this.shader.load("uv", sprite.getUv());
					this.shader.load("size", sprite.getSpriteSize());
					DrawCalls.drawElements(6);
				});
			});
			ShaderProgram.stop();
		});
	}
}
