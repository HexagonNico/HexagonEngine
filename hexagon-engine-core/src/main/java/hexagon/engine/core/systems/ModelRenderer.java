package hexagon.engine.core.systems;

import java.util.ArrayList;
import java.util.HashMap;

import hexagon.engine.core.components.Camera3D;
import hexagon.engine.core.components.ModelComponent;
import hexagon.engine.core.components.ReflectivityComponent;
import hexagon.engine.core.ecs.GameSystem;
import hexagon.engine.core.resources.Model;
import hexagon.engine.lwjgl.DrawCalls;
import hexagon.engine.lwjgl.shader.Shader;
import hexagon.engine.lwjgl.shader.ShaderProgram;

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
		Camera3D.main().ifPresent(camera -> {
			this.shader.load("projection_matrix", camera.projectionMatrix());
			this.shader.load("view_matrix", camera.viewMatrix());
		});
		LightSystem.forEach(light -> {
			this.shader.load("light_position", light.position);
			this.shader.load("light_color", light.color.r(), light.color.g(), light.color.b());
			this.shader.load("light_intensity", light.intensity);
		});
		this.renderBatch.forEach((model, components) -> {
			model.vertexObject.activate(() -> {
				components.forEach(component -> {
					this.shader.load("transformation_matrix", component.transformationMatrix());
					this.shader.load("color", component.color.r(), component.color.g(), component.color.b());
					component.getSiblingComponent(ReflectivityComponent.class).ifPresent(reflectivityComponent -> {
						this.shader.load("diffuse_light", reflectivityComponent.diffuseLight);
						this.shader.load("reflectivity", reflectivityComponent.reflectivity);
						this.shader.load("shine_damper", reflectivityComponent.shineDamper);
					});
					DrawCalls.drawElements(component.model.vertexCount);
				});
			});
		});
		ShaderProgram.stop();
		this.renderBatch.clear();
	}
}
