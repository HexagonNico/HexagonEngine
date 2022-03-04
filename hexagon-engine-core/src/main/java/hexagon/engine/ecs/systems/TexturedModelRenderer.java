package hexagon.engine.ecs.systems;

import java.util.ArrayList;
import java.util.HashMap;

import hexagon.engine.ecs.GameSystem;
import hexagon.engine.ecs.components.Camera;
import hexagon.engine.ecs.components.ReflectivityComponent;
import hexagon.engine.ecs.components.TexturedModelComponent;
import hexagon.engine.ecs.components.Transform3D;
import hexagon.engine.opengl.DrawCalls;
import hexagon.engine.opengl.Shader;
import hexagon.engine.opengl.ShaderProgram;
import hexagon.engine.opengl.Texture;
import hexagon.engine.utils.models.Model;

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
	protected void beforeAll() {
		this.renderBatch.clear();
	}

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
		Camera.main().ifPresent(camera -> {
			this.shader.load("projection_matrix", camera.projectionMatrix());
			this.shader.load("view_matrix", camera.viewMatrix());
		});
		LightSystem.forEach(light -> {
			light.getSiblingComponent(Transform3D.class).ifPresent(transform -> {
				this.shader.load("light_position", transform.position());
			});
			this.shader.load("light_color", light.getColor().r(), light.getColor().g(), light.getColor().b());
			this.shader.load("light_intensity", light.getIntensity());
		});
		this.renderBatch.forEach((texturedModel, components) -> {
			texturedModel.texture.bind();
			texturedModel.model.vertexObject.activate(() -> {
				components.forEach(component -> {
					component.getSiblingComponent(Transform3D.class).ifPresent(transform -> {
						this.shader.load("transformation_matrix", transform.matrix());
					});
					this.shader.load("color", component.color.r(), component.color.g(), component.color.b());
					component.getSiblingComponent(ReflectivityComponent.class).ifPresent(reflectivityComponent -> {
						this.shader.load("diffuse_light", reflectivityComponent.getDiffuse());
						this.shader.load("reflectivity", reflectivityComponent.getReflectivity());
						this.shader.load("shine_damper", reflectivityComponent.getShineDamper());
					});
					DrawCalls.drawElements(texturedModel.model.vertexCount);
				});
			});
		});
		ShaderProgram.stop();
	}
	
	/**
	 * Used as a key for the render batch
	 */
	private static record BatchKey(Model model, Texture texture) {
	}
}
