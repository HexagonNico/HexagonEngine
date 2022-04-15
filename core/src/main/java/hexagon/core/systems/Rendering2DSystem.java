package hexagon.core.systems;

import java.util.HashMap;

import hexagon.core.GameEntity;
import hexagon.core.components.Render2DComponent;
import hexagon.core.components.Transform;
import hexagon.utils.json.JsonObject;

public final class Rendering2DSystem extends RenderingSystem<Render2DComponent> {

	private HashMap<Render2DComponent, Transform> renderBatch = new HashMap<>();

	public Rendering2DSystem() {
		super(Render2DComponent.class);
	}

	@Override
	public void init(JsonObject jsonObject) {}

	@Override
	public void process(GameEntity entity, Render2DComponent component, float deltaTime) {
		entity.findComponent(Transform.class).ifPresent(transform -> {
			this.renderBatch.put(component, transform);
		});
	}

	@Override
	public void renderAll() {
		this.renderBatch.keySet().stream().sorted().forEach(renderer -> {
			Transform transform = this.renderBatch.get(renderer);
			renderer.render(transform);
		});
	}
}
