package hexagon.core.scripts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import hexagon.core.RenderingSystem;
import hexagon.core.base.GameEntity;
import hexagon.core.base.Script;
import hexagon.core.components.SpriteComponent;
import hexagon.lwjgl.opengl.DrawCalls;
import hexagon.lwjgl.opengl.Texture;
import hexagon.lwjgl.opengl.VertexObject;

public final class SpriteRenderer extends Script {

	static {
		RenderingSystem.addRenderingProcess(SpriteRenderer::render);
	}

	private static HashMap<Texture, ArrayList<SpriteRenderer>> renderBatch = new HashMap<>();
	private static VertexObject quadModel = VertexObject.with()
			.attribute(0, new float[] {-0.5f,0.5f, -0.5f,-0.5f, 0.5f,-0.5f, 0.5f,0.5f}, 2)
			.indices(new int[] {0,1,3, 3,1,2})
			.create();
	/*private static ShaderProgram shader = ShaderProgram.with()
			.vertexShader("/shaders/vertex/sprite_shader.glsl")
			.fragmentShader("/shaders/fragment/texture_shader.glsl")
			.attribute(0, "vertex")
			.attribute(1, "uv")
			.create();*/

	//private Optional<Transform> transform;
	private Optional<SpriteComponent> sprite;

	public SpriteRenderer(GameEntity entity) {
		super(entity);
		//this.transform = entity.getComponent(Transform.class);
		this.sprite = entity.getComponent(SpriteComponent.class);
	}

	@Override
	public void update() {
		this.sprite.ifPresent(sprite -> {
			if(renderBatch.containsKey(sprite.texture())) {
				renderBatch.get(sprite.texture()).add(this);
			} else {
				ArrayList<SpriteRenderer> list = new ArrayList<>();
				list.add(this);
				renderBatch.put(sprite.texture(), list);
			}
		});
	}

	private static void render() {
		quadModel.activate(() -> {
			// TODO - Render with texture and shader
			renderBatch.values().stream()
					.flatMap(ArrayList::stream)
					.forEach(sprite -> DrawCalls.drawElements(6));
		});
		renderBatch.clear();
	}
}
