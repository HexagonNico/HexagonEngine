package hexagon.engine.core.systems;

import java.util.ArrayList;
import java.util.HashMap;

import hexagon.engine.core.components.TexturedModelComponent;
import hexagon.engine.core.ecs.GameSystem;
import hexagon.engine.core.resources.Model;
import hexagon.engine.lwjgl.DrawCalls;
import hexagon.engine.lwjgl.shader.Shader;
import hexagon.engine.lwjgl.shader.ShaderProgram;
import hexagon.engine.lwjgl.texture.Texture;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.vector.Float3;

public final class TexturedModelRenderer extends GameSystem<TexturedModelComponent> {
	
	/**Shader program used for this renderer */
	private final ShaderProgram shader;
	/**Map that stores entities in batches to render them more efficiently */
	private final HashMap<BatchKey, ArrayList<TexturedModelComponent>> renderBatch;

	public TexturedModelRenderer() {
		super(TexturedModelComponent.class);
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
	protected void process(TexturedModelComponent component) {
		BatchKey texturedModel = new BatchKey(component.model, component.texture);
		if(this.renderBatch.containsKey(texturedModel)) {
			this.renderBatch.get(texturedModel).add(component);
		} else {
			ArrayList<TexturedModelComponent> batch = new ArrayList<>();
			batch.add(component);
			this.renderBatch.put(texturedModel, batch);
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
		this.renderBatch.forEach((texturedModel, components) -> {
			texturedModel.texture.bind();
			texturedModel.model.vertexObject.activate(() -> {
				components.forEach(component -> {
					this.shader.load("transformation_matrix", component.transformationMatrix());
					// TODO - Color tint
					//this.shader.load("color", component.color.r(), component.color.g(), component.color.b());
					// TODO - Reflectivity component
					//this.shader.load("reflectivity", 1.0f);
					//this.shader.load("shine_damper", 5.0f);
					DrawCalls.drawElements(texturedModel.model.vertexCount);
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
