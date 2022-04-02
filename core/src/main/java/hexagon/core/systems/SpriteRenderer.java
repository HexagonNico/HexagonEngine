package hexagon.core.systems;

import java.util.HashMap;
import java.util.HashSet;

import hexagon.core.GameEntity;
import hexagon.core.components.SpriteComponent;
import hexagon.core.components.Transform;
import hexagon.core.rendering.Camera;
import hexagon.core.rendering.RenderingSystem;
import hexagon.lwjgl.opengl.DrawCalls;
import hexagon.lwjgl.opengl.ShaderProgram;
import hexagon.lwjgl.opengl.Texture;
import hexagon.lwjgl.opengl.VertexObject;

public final class SpriteRenderer extends GameSystem<SpriteComponent> {

	static {
		RenderingSystem.addRenderingProcess(SpriteRenderer::render);
	}

	private static HashMap<Texture, HashSet<SpriteObject>> renderBatch = new HashMap<>();
	private static VertexObject quadModel = VertexObject.with()
			.attribute(0, new float[] {-0.5f,0.5f, -0.5f,-0.5f, 0.5f,-0.5f, 0.5f,0.5f}, 2)
			.indices(new int[] {0,1,3, 3,1,2})
			.create();

	private static void render() {
		quadModel.activate(() -> {
			renderBatch.keySet().forEach(texture -> {
				texture.bind();
				renderBatch.get(texture).forEach(renderer -> {
					ShaderProgram.start(renderer.sprite.shader());
					renderer.sprite.shader().load("projection_matrix", Camera.main().projection());
					renderer.sprite.shader().load("view_matrix", Camera.main().view());
					renderer.sprite.shader().load("transformation_matrix", renderer.transform.matrix());
					DrawCalls.drawElements(6);
					ShaderProgram.stop();
				});
			});
		});
		//renderBatch.clear();
	}

	public SpriteRenderer() {
		super(SpriteComponent.class);
	}

	@Override
	public void process(GameEntity entity, SpriteComponent sprite) {
		if(renderBatch.containsKey(sprite.texture())) {
			entity.findComponent(Transform.class).ifPresent(transform -> {
				renderBatch.get(sprite.texture()).add(new SpriteObject(sprite, transform));
			});
		} else {
			entity.findComponent(Transform.class).ifPresent(transform -> {
				HashSet<SpriteObject> set = new HashSet<>();
				set.add(new SpriteObject(sprite, transform));
				renderBatch.put(sprite.texture(), set);
			});
		}
	}

	private static record SpriteObject(SpriteComponent sprite, Transform transform) {

	}
}
