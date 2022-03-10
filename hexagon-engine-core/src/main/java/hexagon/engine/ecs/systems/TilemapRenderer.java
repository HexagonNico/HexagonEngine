package hexagon.engine.ecs.systems;

import java.util.ArrayList;
import java.util.HashMap;

import hexagon.engine.ecs.GameSystem;
import hexagon.engine.ecs.components.Camera;
import hexagon.engine.ecs.components.TilemapComponent;
import hexagon.engine.opengl.DrawCalls;
import hexagon.engine.opengl.ShaderProgram;
import hexagon.engine.opengl.TextureArray;

/**
 * A {@link GameSystem} that processes all {@link TilemapComponent}s in the scene and renders them.
 * 
 * @author Nico
 */
public final class TilemapRenderer extends GameSystem<TilemapComponent> {
	
	/**Shader program used for this renderer */
	private final ShaderProgram shader;
	/**Map that stores tilemaps in batches to render them more efficiently */
	private final HashMap<TextureArray, ArrayList<TilemapComponent>> renderBatch;

	/**
	 * Creates tilemap renderer
	 */
	public TilemapRenderer() {
		super(TilemapComponent.class);
		this.shader = ShaderProgram.with()
			.vertexShader("/shaders/vertex/tilemap_shader.glsl")
			.fragmentShader("/shaders/fragment/texture_array_shader.glsl")
			.attribute(0, "vertex")
			.attribute(1, "tile")
			.create();
		this.renderBatch = new HashMap<>();
	}

	@Override
	protected void beforeAll() {
		// Clear batch from previous frame
		this.renderBatch.clear();
	}

	@Override
	protected void process(TilemapComponent tilemap) {
		// Add tilemap component to the batch
		if(this.renderBatch.containsKey(tilemap.getTileset())) {
			this.renderBatch.get(tilemap.getTileset()).add(tilemap);
		} else {
			ArrayList<TilemapComponent> batch = new ArrayList<>();
			batch.add(tilemap);
			this.renderBatch.put(tilemap.getTileset(), batch);
		}
	}

	@Override
	protected void afterAll() {
		// Render all components in the batch
		ShaderProgram.start(this.shader);
		Camera.main().ifPresent(camera -> {
			this.shader.load("projection_matrix", camera.projectionMatrix());
			this.shader.load("view_matrix", camera.viewMatrix());
		});
		this.renderBatch.forEach((tileset, tilemaps) -> {
			tileset.bind();
			tilemaps.forEach(tilemap -> {
				tilemap.renderMesh(() -> {
					this.shader.load("transformation_matrix", tilemap.transformationMatrix());
					DrawCalls.drawTriangles(0, tilemap.tilemapWidth() * tilemap.tilemapHeight() * 6);
				});
			});
		});
		ShaderProgram.stop();
	}
}
