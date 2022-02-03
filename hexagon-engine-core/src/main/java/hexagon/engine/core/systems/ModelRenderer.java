package hexagon.engine.core.systems;

import java.util.ArrayList;
import java.util.HashMap;

import hexagon.engine.core.components.ModelComponent;
import hexagon.engine.core.ecs.GameSystem;
import hexagon.engine.core.resources.Model;
import hexagon.engine.lwjgl.DrawCalls;
import hexagon.engine.lwjgl.shader.Shader;
import hexagon.engine.lwjgl.shader.ShaderProgram;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.vector.Float3;

public final class ModelRenderer extends GameSystem<ModelComponent> {

	/**Shader program used for this renderer */
	private final ShaderProgram shader;
	/**Map that stores entities in batches to render them more efficiently */
	private final HashMap<Model, ArrayList<ModelComponent>> renderBatch;

	/**
	 * Creates model renderer, initializes shader.
	 * 
	 * @param gameManager Reference to the game manager.
	 */
	public ModelRenderer() {
		super(ModelComponent.class);
		this.shader = ShaderProgram.with()
			.shader(Shader.vertex("/shaders/vertex/model_shader.glsl"))
			.shader(Shader.fragment("/shaders/fragment/light.glsl"))
			.shader(Shader.fragment("/shaders/fragment/plain_color_shader.glsl"))
			.attribute(0, "vertex")
			.attribute(2, "normal")
			.create();
		this.renderBatch = new HashMap<>();
	}

	@Override
	protected void beforeAll() {}

	@Override
	protected void process(ModelComponent component) {
		if(this.renderBatch.containsKey(component.model)) {
			this.renderBatch.get(component.model).add(component);
		} else {
			ArrayList<ModelComponent> batch = new ArrayList<>();
			batch.add(component);
			this.renderBatch.put(component.model, batch);
		}
	}

	@Override
	protected void afterAll() {
		ShaderProgram.start(this.shader);
		// TODO - Fix the camera
		this.shader.load("projection_matrix", Matrices.projection(70.0f, 0.1f, 1000.0f));
		this.shader.load("view_matrix", Matrices.view(new Float3(0, 0, 5), 0, 0));
		// TODO - Lighting
		this.shader.load("light_position", 200.0f, 200.0f, 100.0f);
		this.shader.load("light_color", 1.0f, 1.0f, 1.0f);
		this.shader.load("light_intensity", 1.0f);
		this.renderBatch.forEach((model, components) -> {
			model.vertexObject.activate(() -> {
				components.forEach(component -> {
					this.shader.load("transformation_matrix", component.transformationMatrix());
					this.shader.load("color", component.color.r(), component.color.g(), component.color.b());
					// TODO - Reflectivity component
					//this.shader.load("reflectivity", 1.0f);
					//this.shader.load("shine_damper", 5.0f);
					DrawCalls.drawElements(component.model.vertexCount);
				});
			});
		});
		ShaderProgram.stop();
		this.renderBatch.clear();
	}
}
