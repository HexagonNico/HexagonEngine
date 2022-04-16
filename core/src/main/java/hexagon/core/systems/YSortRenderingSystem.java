package hexagon.core.systems;

import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

import hexagon.core.GameEntity;
import hexagon.core.components.Render2DComponent;
import hexagon.core.components.Transform2D;
import hexagon.utils.json.JsonObject;

public class YSortRenderingSystem extends RenderingSystem<Render2DComponent> {

	private final HashMap<Render2DComponent, Transform2D> renderBatch = new HashMap<>();

	public YSortRenderingSystem() {
		super(Render2DComponent.class);
	}

	@Override
	public void init(JsonObject jsonObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(GameEntity entity, Render2DComponent render, float deltaTime) {
		entity.findComponent(Transform2D.class).ifPresent(transform -> {
			this.renderBatch.put(render, transform);
		});
	}

	@Override
	public void renderAll() {
		this.renderBatch.entrySet().stream()
				.collect(Collectors.groupingBy(entry -> entry.getKey().sortingLayer()))
				.forEach((layerIndex, sortingLayer) -> {
					sortingLayer.stream()
							.sorted(Comparator.comparing(entry -> -entry.getValue().position().y()))
							.forEach(entry -> entry.getKey().render(entry.getValue()));
				});
	}
}
