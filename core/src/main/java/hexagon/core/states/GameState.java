package hexagon.core.states;

import java.util.HashMap;
import java.util.Optional;

import hexagon.core.GameEntity;
import hexagon.core.components.Component;
import hexagon.utils.Log;

/**
 * Class that represents a game state.
 * A game state is a collection of components that represents a state the game can be in.
 * Examples for a game states can be "TitleScreen", "PlayingState", "GameOver"...
 * 
 * @author Nico
 */
public final class GameState {
	
	/**Currently active game state */
	private static GameState currentState = new GameState();

	/**
	 * Gets the current state.
	 * 
	 * @return The currently active state
	 */
	public static GameState current() {
		return currentState;
	}

	/**
	 * Uses {@link StateLoader} to load a new game state and set it as the current one.
	 * 
	 * @param filePath Path to the state file
	 */
	public static synchronized void loadState(String filePath) {
		Log.info("Loading state " + filePath);
		currentState = StateLoader.loadState(new GameState(), filePath);
	}

	/**
	 * Updates the current state by performing actions that must
	 * run on the main thread or that are not coupled to a system.
	 * Called from the main application class.
	 */
	public static synchronized void update() {
		currentState.components.forEach((type, map) -> {
			map.values().removeIf(Component::markedForRemoval);
		});
	}

	/**Map containing all entities (or their components) in this game state */
	private final HashMap<Class<?>, HashMap<GameEntity, Component>> components = new HashMap<>();

	/**
	 * Adds a component to an entity by storing it in the components table.
	 * 
	 * @param entity The entity to add the component to
	 * @param component The component to add
	 */
	public void addComponent(GameEntity entity, Component component) {
		Class<?> componentKey = this.getComponentKey(component.getClass());
		if(this.components.containsKey(componentKey)) {
			this.components.get(componentKey).put(entity, component);
		} else {
			HashMap<GameEntity, Component> map = new HashMap<>();
			map.put(entity, component);
			this.components.put(componentKey, map);
		}
	}

	/**
	 * Finds a component in the components table.
	 * 
	 * @param <T> Class of the requested component
	 * 
	 * @param entity The entity that holds that component
	 * @param type Type of the component to get
	 * 
	 * @return An {@link Optional} containing the requested component
	 * 		or an empty {@link Optional} if that component is not found
	 */
	public <T extends Component> Optional<T> findComponent(GameEntity entity, Class<T> type) {
		if(entity != null && type != null) {
			Class<?> componentKey = this.getComponentKey(type);
			if(this.components.containsKey(componentKey)) {
				HashMap<GameEntity, Component> components = this.components.get(componentKey);
				if(components.containsKey(entity)) {
					return Optional.of(type.cast(components.get(entity)));
				}
			}
		}
		return Optional.empty();
	}

	/**
	 * Gets all components of a certain type that are in this state.
	 * 
	 * @param type Type of the components to get
	 * 
	 * @return A {@link HashMap} containing the requested components as values
	 * 		and the entities that hold them as keys
	 */
	public HashMap<GameEntity, Component> getComponents(Class<?> type) {
		if(type != null) {
			Class<?> componentKey = this.getComponentKey(type);
			if(this.components.containsKey(componentKey)) {
				return this.components.get(componentKey);
			}
		}
		return new HashMap<>();
	}

	/**
	 * Gets a component's key. Used internally to store components.
	 * 
	 * @param componentType Class of the component
	 * 
	 * @return The component's superclass that directly inherits from {@link Component}
	 */
	private Class<?> getComponentKey(Class<?> componentType) {
		Class<?> superClass = componentType.getSuperclass();
		return superClass.equals(Component.class) ? componentType : this.getComponentKey(superClass);
	}
}
