package hexagon.engine.core.rendering;

import java.util.ArrayList;
import java.util.HashMap;

import hexagon.engine.core.components.Camera3D;
import hexagon.engine.core.components.SpriteComponent;
import hexagon.engine.core.components.Transform2D;
import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.core.ecs.GameSystem;
import hexagon.engine.lwjgl.DrawCalls;
import hexagon.engine.lwjgl.VertexObject;
import hexagon.engine.lwjgl.shader.Shader;
import hexagon.engine.lwjgl.shader.ShaderProgram;
import hexagon.engine.lwjgl.texture.Texture;

/**
 * Game system that renders 2d sprites.
 * <p>
 * 	Renders all entities with a {@link SpriteComponent} and a {@link Transform2D}.
 * </p>
 * 
 * @author Nico
 */
public final class SpriteRenderer extends GameSystem {

	/**Vertex object storing a quad model */
	private final VertexObject model;
	/**Shader program used for this renderer */
	private final ShaderProgram shader;
	/**Map that stores entities in batches to render them more efficiently */
	private final HashMap<Texture, ArrayList<GameEntity>> renderBatch;

	/**
	 * Creates sprite renderer.
	 * Initializes vertex object and shader.
	 */
	public SpriteRenderer() {
		super(Transform2D.class, SpriteComponent.class);
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
			// TODO - 2D orthographic camera
			if(Camera3D.main() != null) {
				// TODO - Looks kinda ugly
				this.shader.load("projection_matrix", Camera3D.main().projectionMatrix());
				this.shader.load("view_matrix", Camera3D.main().viewMatrix());
			}
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
