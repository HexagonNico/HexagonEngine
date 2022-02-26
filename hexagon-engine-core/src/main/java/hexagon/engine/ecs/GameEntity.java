package hexagon.engine.ecs;

import java.util.Optional;
import java.util.function.Function;

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
	 * Adds a component to this entity by adding it to the current state.
	 * 
	 * @param <T> Type of the component to add
	 * @param constructor The component's constructor passed as a method reference as in {@code Component::new}
	 * 
	 * @return The instantiated component
	 */
	public <T extends Component> T addComponent(Function<GameEntity, T> constructor) {
		T component = constructor.apply(this);
		this.state.addComponent(this, component);
		return component;
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
