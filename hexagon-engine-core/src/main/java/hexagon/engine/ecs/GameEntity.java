package hexagon.engine.ecs;

import java.util.Optional;

import hexagon.engine.states.GameState;

/**
 * Class that represents an entity in the Entity-Component-System.
 * An entity is anything that can hold components.
 * 
 * @author Nico
 */
public final class GameEntity {

	/**The game state the entity is in */
	private final GameState state;

	/**
	 * Creates a game entity.
	 * 
	 * @param state The game state the entity is in
	 */
	public GameEntity(GameState state) {
		this.state = state;
	}

	/**
	 * Adds a component to this entity.
	 * 
	 * @param component The component to add.
	 */
	public void addComponent(Component component) {
		this.state.addComponent(this, component);
	}

	/**
	 * Gets one of the entity's components
	 * 
	 * @param <T> Type of the component
	 * @param type The component's class as in {@code Component.class}
	 * 
	 * @return An {@link Optional} containing the requested component or an empty {@link Optional} if the entity does not have such component
	 */
	public <T> Optional<T> getComponent(Class<T> type) {
		return this.state.getComponent(this, type);
	}
}
