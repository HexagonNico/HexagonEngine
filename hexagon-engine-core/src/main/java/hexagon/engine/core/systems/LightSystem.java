package hexagon.engine.core.systems;

import java.util.HashSet;
import java.util.function.Consumer;

import hexagon.engine.core.components.LightComponent;
import hexagon.engine.core.ecs.GameSystem;

public final class LightSystem extends GameSystem<LightComponent> {

	private static final HashSet<LightComponent> lights = new HashSet<>();

	public LightSystem() {
		super(LightComponent.class);
	}

	@Override
	protected void beforeAll() {}

	@Override
	protected void process(LightComponent component) {
		lights.add(component);
	}

	@Override
	protected void afterAll() {}

	public static void forEach(Consumer<LightComponent> consumer) {
		lights.forEach(consumer);
	}
}
