package hexagon.engine.core.rendering;

import java.util.ArrayList;
import java.util.HashMap;

import hexagon.engine.core.components.ObjModelComponent;
import hexagon.engine.core.components.Transform3D;
import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.core.ecs.GameSystem;
import hexagon.engine.core.resources.ObjModel;
import hexagon.engine.lwjgl.DrawCalls;
import hexagon.engine.lwjgl.shader.Shader;
import hexagon.engine.lwjgl.shader.ShaderProgram;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.vector.Float3;

public final class ObjModelRenderer extends GameSystem {

	// TODO - We are going to need a renderer system that can renderer generic models, not just .obj

	private final ShaderProgram shader;
	private final HashMap<ObjModel, ArrayList<GameEntity>> renderBatch;

	public ObjModelRenderer() {
		super(ObjModelComponent.class, Transform3D.class);
		this.shader = ShaderProgram.with()
			.shader(Shader.vertex("/shaders/test_vertex.glsl"))
			.shader(Shader.fragment("/shaders/test_fragment.glsl"))
			.attribute(0, "vertex")
			.create();
		this.renderBatch = new HashMap<>();
	}

	@Override
	protected void beforeAll() {}

	@Override
	protected void process(GameEntity entity) {
		ObjModelComponent component = entity.getComponent(ObjModelComponent.class);
		if(this.renderBatch.containsKey(component.model)) {
			this.renderBatch.get(component.model).add(entity);
		} else {
			ArrayList<GameEntity> batch = new ArrayList<>();
			batch.add(entity);
			this.renderBatch.put(component.model, batch);
		}
	}

	@Override
	protected void afterAll() {
		ShaderProgram.start(this.shader);
		// TODO - Camera
		this.shader.load("projection_matrix", Matrices.projection(70.0f, 0.1f, 100.0f));
		this.shader.load("view_matrix", Matrices.view(new Float3(0.0f, 6.0f, 30.0f), 45.0f, 0.0f));
		this.renderBatch.forEach((model, entities) -> {
			model.vertexObject.activate(() -> {
				entities.forEach(entity -> {
					Transform3D transform = entity.getComponent(Transform3D.class);
					this.shader.load("transformation_matrix", transform.matrix());
					DrawCalls.drawElements(model.vertexCount);
				});
			});
		});
		ShaderProgram.stop();
		this.renderBatch.clear();
	}
	
}
