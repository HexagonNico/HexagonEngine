package hexagon.engine.core.rendering;

import java.util.ArrayList;
import java.util.HashMap;

import hexagon.engine.core.components.Camera3D;
import hexagon.engine.core.components.ModelComponent;
import hexagon.engine.core.components.Transform3D;
import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.core.ecs.GameManager;
import hexagon.engine.core.ecs.GameSystem;
import hexagon.engine.core.resources.Model;
import hexagon.engine.lwjgl.DrawCalls;
import hexagon.engine.lwjgl.shader.Shader;
import hexagon.engine.lwjgl.shader.ShaderProgram;
import hexagon.engine.math.color.Color;

/**
 * Game system that renders 3d models.
 * <p>
 * 	Renders all entities with a {@link ModelComponent} and a {@link Transform3D}.
 * </p>
 * 
 * @author Nico
 */
public final class ModelRenderer extends GameSystem {

	/**Shader program used for this renderer */
	private final ShaderProgram shader;
	/**Map that stores entities in batches to render them more efficiently */
	private final HashMap<BatchKey, ArrayList<GameEntity>> renderBatch;

	/**
	 * Creates model renderer, initializes shader.
	 * 
	 * @param gameManager Reference to the game manager.
	 */
	public ModelRenderer(GameManager gameManager) {
		super(gameManager, ModelComponent.class, Transform3D.class);
		this.shader = ShaderProgram.with()
			.shader(Shader.vertex("/shaders/vertex/model_shader.glsl"))
			.shader(Shader.fragment("/shaders/fragment/plain_color_shader.glsl"))
			.attribute(0, "vertex")
			.create();
		this.renderBatch = new HashMap<>();
	}

	@Override
	protected void beforeAll() {}

	@Override
	protected void process(GameEntity entity) {
		ModelComponent component = entity.getComponent(ModelComponent.class);
		BatchKey key = new BatchKey(component.model, component.color);
		if(this.renderBatch.containsKey(key)) {
			this.renderBatch.get(key).add(entity);
		} else {
			ArrayList<GameEntity> batch = new ArrayList<>();
			batch.add(entity);
			this.renderBatch.put(key, batch);
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
			key.model.vertexObject.activate(() -> {
				entities.forEach(entity -> {
					Transform3D transform = entity.getComponent(Transform3D.class);
					this.shader.load("transformation_matrix", transform.matrix());
					this.shader.load("color", key.color.r(), key.color.g(), key.color.b());
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
	private static record BatchKey(Model model, Color color) {
	}
}
