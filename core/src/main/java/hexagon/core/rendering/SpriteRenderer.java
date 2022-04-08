package hexagon.core.rendering;

import java.util.HashMap;
import java.util.HashSet;

import hexagon.core.GameEntity;
import hexagon.core.components.SpriteComponent;
import hexagon.core.components.Transform;
import hexagon.core.states.GameState;
import hexagon.lwjgl.opengl.DrawCalls;
import hexagon.lwjgl.opengl.ShaderProgram;
import hexagon.lwjgl.opengl.Texture;
import hexagon.lwjgl.opengl.VertexObject;

/**
 * Game system that handles the rendering of sprites.
 * Iterates through all {@link SpriteComponent}s and adds them to the rendering batch.
 * 
 * @author Nico
 */
public final class SpriteRenderer extends RenderingSystem<SpriteComponent> {

	/**Sprites batch */
	private static HashMap<Texture, HashSet<SpriteObject>> renderBatch = new HashMap<>();
	/**Quad model used to render sprites */
	private static VertexObject quadModel = VertexObject.with()
			.attribute(0, new float[] {-0.5f,0.5f, -0.5f,-0.5f, 0.5f,-0.5f, 0.5f,0.5f}, 2)
			.indices(new int[] {0,1,3, 3,1,2})
			.create();

	/**
	 * Creates a sprite renderer
	 */
	public SpriteRenderer() {
		super(SpriteComponent.class);
	}

	@Override
	public void processEntities() {
		GameState.current().getComponents(SpriteComponent.class).forEach((entity, component) -> {
			this.process(entity, (SpriteComponent) component, 0.0f);
		});
	}

	@Override
	public void process(GameEntity entity, SpriteComponent sprite, float deltaTime) {
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

	@Override
	public void render() {
		quadModel.activate(() -> {
			renderBatch.keySet().forEach(texture -> {
				texture.bind();
				renderBatch.get(texture).forEach(renderer -> {
					ShaderProgram.start(renderer.sprite.shader());
					renderer.sprite.shader().load("projection_matrix", Camera.main().projection());
					renderer.sprite.shader().load("view_matrix", Camera.main().view());
					renderer.sprite.shader().load("transformation_matrix", renderer.transform.matrix());
					renderer.sprite.shader().load("offset", renderer.sprite.offset());
					DrawCalls.drawElements(6);
					ShaderProgram.stop();
				});
			});
		});
		renderBatch.clear();
	}

	/**
	 * Used in rendering batch to store a sprite and a transform
	 */
	private static record SpriteObject(SpriteComponent sprite, Transform transform) {

	}
}
