package hexagon.core.rendering;

import java.util.ArrayList;
import java.util.HashMap;

import hexagon.core.components.SpriteComponent;
import hexagon.core.components.Transform;
import hexagon.lwjgl.opengl.DrawCalls;
import hexagon.lwjgl.opengl.ShaderProgram;
import hexagon.lwjgl.opengl.Texture;
import hexagon.lwjgl.opengl.VertexObject;

public final class SpriteRenderer {
	
	static {
		RenderingSystem.addRenderingProcess(SpriteRenderer::renderingProcesses);
	}

	private static HashMap<Texture, ArrayList<SpriteRenderer>> renderBatch = new HashMap<>();
	private static VertexObject quadModel = VertexObject.with()
			.attribute(0, new float[] {-0.5f,0.5f, -0.5f,-0.5f, 0.5f,-0.5f, 0.5f,0.5f}, 2)
			.indices(new int[] {0,1,3, 3,1,2})
			.create();

	public static void addToBatch(SpriteComponent sprite) {
		sprite.getSiblingComponent(Transform.class).ifPresent(transform -> {
			SpriteRenderer renderer = new SpriteRenderer(transform, sprite);
			if(renderBatch.containsKey(sprite.texture())) {
				renderBatch.get(sprite.texture()).add(renderer);
			} else {
				ArrayList<SpriteRenderer> list = new ArrayList<>();
				list.add(renderer);
				renderBatch.put(sprite.texture(), list);
			}
		});
		// TODO - Missing transform?
	}

	private static void renderingProcesses() {
		quadModel.activate(() -> {
			renderBatch.keySet().forEach(texture -> {
				texture.bind();
				renderBatch.get(texture).forEach(renderer -> {
					ShaderProgram.start(renderer.sprite.shader());
					renderer.sprite.shader().load("transformation_matrix", renderer.transform.matrix());
					DrawCalls.drawElements(6);
					ShaderProgram.stop();
				});
			});
		});
		ShaderProgram.stop();
	}

	private Transform transform;
	private SpriteComponent sprite;

	private SpriteRenderer(Transform transform, SpriteComponent sprite) {
		this.transform = transform;
		this.sprite = sprite;
	}
}
