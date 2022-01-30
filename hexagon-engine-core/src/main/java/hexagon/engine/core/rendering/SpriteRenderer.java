package hexagon.engine.core.rendering;

import java.util.ArrayList;
import java.util.HashMap;

import hexagon.engine.core.components.SpriteComponent;
import hexagon.engine.core.components.Transform2D;
import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.core.ecs.GameSystem;
import hexagon.engine.lwjgl.DrawCalls;
import hexagon.engine.lwjgl.VertexObject;
import hexagon.engine.lwjgl.shader.Shader;
import hexagon.engine.lwjgl.shader.ShaderProgram;
import hexagon.engine.lwjgl.texture.Texture;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.vector.Float3;

public final class SpriteRenderer extends GameSystem {

	private final VertexObject model;
	private final ShaderProgram shader;
	private final HashMap<Texture, ArrayList<GameEntity>> renderBatch;

	public SpriteRenderer() {
		super(Transform2D.class, SpriteComponent.class);
		// TODO - uv
		this.model = VertexObject.with()
			.attribute(0, new float[] {-0.5f,0.5f, -0.5f,-0.5f, 0.5f,-0.5f, 0.5f,0.5f}, 2)
			.indices(new int[] {0,1,3, 3,1,2})
			.create();
		this.shader = ShaderProgram.with()
			.shader(Shader.vertex("/shaders/sprite_vertex.glsl"))
			.shader(Shader.fragment("/shaders/sprite_fragment.glsl"))
			.attribute(0, "vertex")
			.attribute(1, "uv")
			.create();
		this.renderBatch = new HashMap<>();
	}

	@Override
	protected void beforeAll() {}

	@Override
	protected void process(GameEntity entity) {
		SpriteComponent sprite = entity.getComponent(SpriteComponent.class);
		if(this.renderBatch.containsKey(sprite.texture)) {
			this.renderBatch.get(sprite.texture).add(entity);
		} else {
			ArrayList<GameEntity> batch = new ArrayList<>();
			batch.add(entity);
			this.renderBatch.put(sprite.texture, batch);
		}
	}

	@Override
	protected void afterAll() {
		this.model.activate(() -> {
			ShaderProgram.start(this.shader);
			// TODO - Camera
			this.shader.load("projection_matrix", Matrices.projection(70.0f, 0.1f, 100.0f));
			this.shader.load("view_matrix", Matrices.view(new Float3(0.0f, 0.0f, 5.0f), 0.0f, 0.0f));
			this.renderBatch.forEach((texture, entities) -> {
				texture.bind();
				entities.forEach(entity -> {
					Transform2D transform = entity.getComponent(Transform2D.class);
					SpriteComponent sprite = entity.getComponent(SpriteComponent.class);
					this.shader.load("transformation_matrix", transform.matrix());
					this.shader.load("uv", sprite.uv);
					this.shader.load("size", sprite.size);
					DrawCalls.drawElements(6);
				});
			});
			ShaderProgram.stop();
		});
		this.renderBatch.clear();
	}
}
