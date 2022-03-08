package hexagon.engine.ecs.systems;

import java.util.ArrayList;
import java.util.HashMap;

import hexagon.engine.ecs.GameSystem;
import hexagon.engine.ecs.components.Camera;
import hexagon.engine.ecs.components.TilemapComponent;
import hexagon.engine.opengl.DrawCalls;
import hexagon.engine.opengl.ShaderProgram;
import hexagon.engine.opengl.TextureArray;

public final class TilemapRenderer extends GameSystem<TilemapComponent> {
	
	private final ShaderProgram shader;
	private final HashMap<TextureArray, ArrayList<TilemapComponent>> renderBatch;

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
		this.renderBatch.clear();
	}

	@Override
	protected void process(TilemapComponent tilemap) {
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
		ShaderProgram.start(this.shader);
		Camera.main().ifPresent(camera -> {
			this.shader.load("projection_matrix", camera.projectionMatrix());
			this.shader.load("view_matrix", camera.viewMatrix());
		});
		this.renderBatch.forEach((tileset, tilemaps) -> {
			tileset.bind();
			tilemaps.forEach(tilemap -> {
				tilemap.getTilemapMesh().activate(() -> {
					this.shader.load("transformation_matrix", tilemap.transformationMatrix());
					DrawCalls.drawElements(tilemap.getMeshVertices());
				});
			});
		});
		ShaderProgram.stop();
	}
	
}
