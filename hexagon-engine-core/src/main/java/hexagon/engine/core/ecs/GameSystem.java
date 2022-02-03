package hexagon.engine.core.ecs;

import java.util.Collection;

/**
 * A game system performs a certain task on every component of a certain type each frame.
 * 
 * @author Nico
 */
public abstract class GameSystem<T extends Component> {

	/**Type of the component to process */
	protected final Class<T> componentType;

	/**
	 * Creates game system.
	 * 
	 * @param componentType Type of the component to process.
	 */
	public GameSystem(Class<T> componentType) {
		this.componentType = componentType;
	}

	/**
	 * Runs the system for the given components, called in game manager.
	 * 
	 * @param components Collection of the components to process.
	 */
	public final void run(Collection<Component> components) {
		this.beforeAll();
		components.stream().map(this.componentType::cast).forEach(this::process);
		this.afterAll();
	}

	/**
	 * Runs once per frame before processing all the components.
	 */
	protected abstract void beforeAll();

	/**
	 * Runs once per each component, applies this system's task.
	 * 
	 * @param component The component to process.
	 */
	protected abstract void process(T component);

	/**
	 * Runs once per frame after processing all the components.
	 */
	protected abstract void afterAll();
}
