package hexagon.core.rendering;

import java.util.HashMap;
import java.util.HashSet;

import hexagon.core.GameEntity;
import hexagon.core.components.TilemapComponent;
import hexagon.core.components.Transform;
import hexagon.core.states.GameState;
import hexagon.lwjgl.opengl.ArrayTexture;
import hexagon.lwjgl.opengl.DrawCalls;
import hexagon.lwjgl.opengl.ShaderProgram;

public class TilemapRenderer extends RenderingSystem<TilemapComponent> {

	// TODO - We need a better rendering thingy

	private static HashMap<ArrayTexture, HashSet<TilemapObject>> renderBatch = new HashMap<>();

	public TilemapRenderer() {
		super(TilemapComponent.class);
	}

	@Override
	public void processEntities() {
		GameState.current().getComponents(TilemapComponent.class).forEach((entity, component) -> {
			this.process(entity, (TilemapComponent) component, 0.0f);
		});
	}

	@Override
	public void process(GameEntity entity, TilemapComponent tilemap, float deltaTime) {
		if(renderBatch.containsKey(tilemap.tileset())) {
			entity.findComponent(Transform.class).ifPresent(transform -> {
				renderBatch.get(tilemap.tileset()).add(new TilemapObject(tilemap, transform));
			});
		} else {
			entity.findComponent(Transform.class).ifPresent(transform -> {
				HashSet<TilemapObject> set = new HashSet<>();
				set.add(new TilemapObject(tilemap, transform));
				renderBatch.put(tilemap.tileset(), set);
			});
		}
	}

	@Override
	public void render() {
		renderBatch.keySet().forEach(tilesetTexture -> {
			tilesetTexture.bind();
			renderBatch.get(tilesetTexture).forEach(renderer -> {
				renderer.tilemap.mesh().activate(() -> {
					ShaderProgram.start(renderer.tilemap.shader());
					renderer.tilemap.shader().load("projection_matrix", Camera.main().projection());
					renderer.tilemap.shader().load("view_matrix", Camera.main().view());
					renderer.tilemap.shader().load("transformation_matrix", renderer.transform.matrix());
					DrawCalls.drawTriangles(0, renderer.tilemap.width() * renderer.tilemap.height() * 6);
					ShaderProgram.stop();
				});
			});
		});
	}

	private static record TilemapObject(TilemapComponent tilemap, Transform transform) {

	}
}
