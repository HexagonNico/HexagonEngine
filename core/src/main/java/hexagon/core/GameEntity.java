package hexagon.core;

import java.util.List;
import java.util.Optional;

import hexagon.core.components.Component;
import hexagon.core.states.GameState;

/**
 * Represents an entity in the ECS system.
 * Entities are references to the components they "store".
 * 
 * @author Nico
 */
public final class GameEntity {

	/**State this entity is in */
	public final GameState state;

	/**
	 * Creates a new entity.
	 * 
	 * @param state The game state the entity is in
	 */
	public GameEntity(GameState state) {
		this.state = state;
	}

	/**
	 * Gets a component from this entity.
	 * Will look for a component in the game state this entity is in.
	 * If a component of that type cannot be found, this method returns {@code null}.
	 * 
	 * @param <T> Class of the component
	 * @param type Type of the component to get
	 * 
	 * @return The requested component or {@code null} if that component cannot be found
	 */
	public <T extends Component> T getComponent(Class<T> type) {
		return this.state.findComponent(this, type).orElse(null);
	}

	/**
	 * Gets an optional component from this entity.
	 * Will look for a component in the game state this entity is in
	 * and return an {@link Optional} that contains it.
	 * 
	 * @param <T> Class of the component
	 * @param type Type of the component to get
	 * 
	 * @return An {@link Optional} containing the requested component
	 */
	public <T extends Component> Optional<T> findComponent(Class<T> type) {
		return this.state.findComponent(this, type);
	}

	/**
	 * Adds a component to this entity.
	 * Will store the component in the game state this entity is in.
	 * 
	 * @param component The component to add
	 */
	public void addComponent(Component component) {
		this.state.addComponent(this, component);
	}

	/**
	 * Removes a component from this entity.
	 * Marks the requested component to be removed.
	 * Components marked to be removed will be removed the next frame.
	 * 
	 * @param <T> Class of the component
	 * @param type Type of the component to remove
	 */
	public <T extends Component> void removeComponent(Class<T> type) {
		this.getComponent(type).markToRemove();
	}

	public List<Component> getComponents() {
		return this.state.getComponents(this);
	}

	public void markToRemove() {
		this.getComponents().forEach(Component::markToRemove);
	}
}
