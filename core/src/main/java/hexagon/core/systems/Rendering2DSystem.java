package hexagon.core.systems;

import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

import hexagon.core.GameEntity;
import hexagon.core.components.Render2DComponent;
import hexagon.core.components.Transform;

public final class Rendering2DSystem extends RenderingSystem<Render2DComponent> {

	private HashMap<Render2DComponent, Transform> renderBatch = new HashMap<>();

	public Rendering2DSystem() {
		super(Render2DComponent.class);
	}

	@Override
	public void process(GameEntity entity, Render2DComponent component, float deltaTime) {
		entity.findComponent(Transform.class).ifPresent(transform -> {
			this.renderBatch.put(component, transform);
		});
	}

	@Override
	public void renderAll() {
		this.renderBatch.entrySet().removeIf(entry -> entry.getKey().markedForRemoval());
		this.renderBatch.entrySet().stream()
				.collect(Collectors.groupingBy(entry -> entry.getKey().sortingLayer()))
				.forEach((layerIndex, sortingLayer) -> {
					sortingLayer.stream()
							.sorted(Comparator.comparing(entry -> entry.getKey().orderInLayer()))
							.forEach(entry -> entry.getKey().render(entry.getValue()));
				});
	}
}
