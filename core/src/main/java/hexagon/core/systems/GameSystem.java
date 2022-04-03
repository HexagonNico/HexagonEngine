package hexagon.core.systems;

import hexagon.core.GameEntity;
import hexagon.core.components.Component;

/**
 * Represents a System in the ECS system.
 * Systems are run on separate threads by a {@link SystemRunner}
 * and iterate through all components of a certain type to process them.
 * 
 * @author Nico
 */
public abstract class GameSystem<T extends Component> {

	/**The type of component this system processes */
	public final Class<T> componentType;

	/**
	 * Creates a new game system.
	 * Subclasses should have a no-args constructor that passes the
	 * component class to this super constructor.
	 * 
	 * @param componentType The type of component this system processes
	 */
	public GameSystem(Class<T> componentType) {
		this.componentType = componentType;
	}

	/**
	 * Processes a component.
	 * Systems iterate through all components in the current state
	 * every frame to process them.
	 * 
	 * @param entity The entity that holds the given component
	 * @param component The component to process
	 */
	public abstract void process(GameEntity entity, T component);
}
