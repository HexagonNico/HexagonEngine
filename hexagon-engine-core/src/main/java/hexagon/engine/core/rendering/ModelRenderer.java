package hexagon.engine.core.rendering;

import java.util.ArrayList;
import java.util.HashMap;

import hexagon.engine.core.components.ModelComponent;
import hexagon.engine.core.components.Transform3D;
import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.core.ecs.GameSystem;
import hexagon.engine.core.resources.Model;
import hexagon.engine.lwjgl.DrawCalls;
import hexagon.engine.lwjgl.shader.Shader;
import hexagon.engine.lwjgl.shader.ShaderProgram;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.vector.Float3;

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
	 * Creates model renderer.
	 * Initializes shader.
	 */
	public ModelRenderer() {
		super(ModelComponent.class, Transform3D.class);
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
		// TODO - Camera
		this.shader.load("projection_matrix", Matrices.projection(70.0f, 0.1f, 100.0f));
		this.shader.load("view_matrix", Matrices.view(new Float3(0.0f, 2.0f, 10.0f), 30.0f, 0.0f));
		this.renderBatch.forEach((key, entities) -> {
			key.model.vertexObject.activate(() -> {
				entities.forEach(entity -> {
					Transform3D transform = entity.getComponent(Transform3D.class);
					this.shader.load("transformation_matrix", transform.matrix());
					this.shader.load("color", key.color);
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
	private static record BatchKey(Model model, Float3 color) {
	}
}
