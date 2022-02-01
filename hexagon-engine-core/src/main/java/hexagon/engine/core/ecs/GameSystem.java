package hexagon.engine.core.ecs;

import java.util.Arrays;
import java.util.Collection;

/**
 * A game system runs once every frame
 * and updates all entities that have the right components.
 * 
 * @author Nico
 */
public abstract class GameSystem {
	
	/**Reference to the game manager */
	protected final GameManager gameManager;
	/**All the components that an entity needs for this system */
	private final Collection<Class<?>> requiredComponents;

	/**
	 * Creates a game system.
	 * 
	 * @param gameManager Reference to the game manager.
	 * @param requiredComponents List of all the components an entity needs.
	 */
	public GameSystem(GameManager gameManager, Class<?>... requiredComponents) {
		this.gameManager = gameManager;
		this.requiredComponents = Arrays.asList(requiredComponents);
	}

	/**
	 * Checks if all the required components are in the given collection.
	 * 
	 * @param components Collection of all of the components of an entity.
	 * 
	 * @return True if the given collection contains all the required components, otherwise false.
	 */
	public final boolean componentsMatch(Collection<Object> components) {
		return components.stream().map(component -> component.getClass()).toList().containsAll(this.requiredComponents);
	}

	/**
	 * Runs once per frame before any entity is processed.
	 */
	protected abstract void beforeAll();

	/**
	 * Runs once per every entity that has the required components.
	 * Applies this system's function.
	 * 
	 * @param entity The entity that is being processed.
	 */
	protected abstract void process(GameEntity entity);

	/**
	 * Runs once per frame after all entities have been processed.
	 */
	protected abstract void afterAll();
}
