package hexagon.engine.core.rendering;

import java.util.ArrayList;
import java.util.HashMap;

import hexagon.engine.core.components.Camera3D;
import hexagon.engine.core.components.TexturedModelComponent;
import hexagon.engine.core.components.Transform3D;
import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.core.ecs.GameSystem;
import hexagon.engine.core.resources.Model;
import hexagon.engine.lwjgl.DrawCalls;
import hexagon.engine.lwjgl.shader.Shader;
import hexagon.engine.lwjgl.shader.ShaderProgram;
import hexagon.engine.lwjgl.texture.Texture;

/**
 * Game system that renders textured models.
 * <p>
 * 	Renders all entities with a {@link TexturedModelComponent} and a {@link Transform3D}.
 * </p>
 * 
 * @author Nico
 */
public final class TexturedModelRenderer extends GameSystem {
	
	/**Shader program used for this renderer */
	private final ShaderProgram shader;
	/**Map that stores entities in batches to render them more efficiently */
	private final HashMap<BatchKey, ArrayList<GameEntity>> renderBatch;

	/**
	 * Creates textured model renderer.
	 * Initializes shader.
	 */
	public TexturedModelRenderer() {
		super(TexturedModelComponent.class, Transform3D.class);
		this.shader = ShaderProgram.with()
			.shader(Shader.vertex("/shaders/vertex/model_shader.glsl"))
			.shader(Shader.fragment("/shaders/fragment/texture_shader.glsl"))
			.attribute(0, "vertex")
			.attribute(1, "texture_coordinates")
			.create();
		this.renderBatch = new HashMap<>();
	}
	
	@Override
	protected void beforeAll() {}

	@Override
	protected void process(GameEntity entity) {
		TexturedModelComponent component = entity.getComponent(TexturedModelComponent.class);
		BatchKey texturedModel = new BatchKey(component.model, component.texture);
		if(this.renderBatch.containsKey(texturedModel)) {
			this.renderBatch.get(texturedModel).add(entity);
		} else {
			ArrayList<GameEntity> batch = new ArrayList<>();
			batch.add(entity);
			this.renderBatch.put(texturedModel, batch);
		}
	}

	@Override
	protected void afterAll() {
		ShaderProgram.start(this.shader);
		if(Camera3D.main() != null) {
			// TODO - Looks kinda ugly
			this.shader.load("projection_matrix", Camera3D.main().projectionMatrix());
			this.shader.load("view_matrix", Camera3D.main().viewMatrix());
		}
		this.renderBatch.forEach((key, entities) -> {
			key.texture.bind();
			key.model.vertexObject.activate(() -> {
				entities.forEach(entity -> {
					Transform3D transform = entity.getComponent(Transform3D.class);
					this.shader.load("transformation_matrix", transform.matrix());
					DrawCalls.drawElements(key.model.vertexCount);
				});
			});
		});
		ShaderProgram.stop();
		this.renderBatch.clear();
	}
	
	/**
	 * Used as a key for the render batch
	 */
	private static record BatchKey(Model model, Texture texture) {
	}
}
