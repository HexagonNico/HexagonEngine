package hexagon.engine.core.ecs;

import java.util.Arrays;
import java.util.Collection;

public abstract class GameSystem {
	
	private final Collection<Class<?>> requiredComponents;

	public GameSystem(Class<?>... requiredComponents) {
		this.requiredComponents = Arrays.asList(requiredComponents);
	}

	public final boolean componentsMatch(Collection<Object> components) {
		return components.stream().map(component -> component.getClass()).toList().containsAll(this.requiredComponents);
	}

	protected abstract void beforeAll();

	protected abstract void process(GameEntity entity);

	protected abstract void afterAll();
}
