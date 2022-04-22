package hexagon.core.states;

import java.util.HashMap;
import java.util.Optional;

import hexagon.core.GameEntity;
import hexagon.core.components.Component;
import hexagon.core.systems.GameSystem;
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
		// TODO - This may not need synchronized
		Log.info("Loading state " + filePath);
		currentState.clear();
		currentState = StateLoader.loadState(new GameState(), filePath);
	}

	/**
	 * Updates the current state by performing actions that must
	 * run on the main thread or that are not coupled to a system.
	 * Called from the main application class.
	 */
	public static synchronized void update() {
		currentState.components.removeIf(Component::markedForRemoval);
	}

	/**Component table that holds all entities and their components */
	private final ComponentsTable components = new ComponentsTable();
	/**System runner that manages all running systems in this state */
	private final SystemRunner systems = new SystemRunner();

	/**
	 * Creates a {@link GameEntity} in this state.
	 * This is equivalent as calling {@code new GameEntity(state)}.
	 * 
	 * @return A newly created game entity
	 */
	public GameEntity createEntity() {
		return new GameEntity(this);
	}

	/**
	 * Adds a component to an entity by storing it in the components table.
	 * This method rarely needs to be called, use {@link GameEntity#addComponent(Component)} instead.
	 * 
	 * @param entity The entity to add the component to
	 * @param component The component to add
	 */
	public void addComponent(GameEntity entity, Component component) {
		this.components.add(entity, component);
	}

	/**
	 * Gets a component from an entity.
	 * This method rarely needs to be called, use {@link GameEntity#findComponent(Class)} instead.
	 * 
	 * @param <T> Type of the component to get
	 * @param entity The entity to get the component from
	 * @param type The class of the component to get
	 * 
	 * @return An {@link Optional} containing the required component or
	 * 		an empty {@link Optional} if the table does not contain said component
	 * 		or if the given entity or the given type are {@code null}
	 */
	public <T extends Component> Optional<T> findComponent(GameEntity entity, Class<T> type) {
		return this.components.find(entity, type);
	}

	/**
	 * Gets a component from an entity.
	 * This method rarely needs to be called, use {@link GameEntity#findComponent(Class)} instead.
	 * 
	 * @param <T> Type of the component to get
	 * @param entity The entity to get the component from
	 * @param type The class of the component to get
	 * 
	 * @return The requested component or {@code null} if that component cannot be found
	 */
	public <T extends Component> T getComponent(GameEntity entity, Class<T> type) {
		return this.findComponent(entity, type).orElse(null);
	}

	public <T extends Component> HashMap<GameEntity, T> getComponents(Class<T> type) {
		return this.components.getAll(type);
	}

	/**
	 * Starts a game system with the {@link SystemRunner}.
	 * 
	 * @param system The game system to start
	 */
	public void startSystem(GameSystem<?> system) {
		this.systems.start(this, system);
	}

	/**
	 * Stops a game system of a certain type.
	 * 
	 * @param type Type of the game system to stop
	 */
	public void stopSystem(Class<? extends GameSystem<?>> type) {
		this.systems.stop(type);
	}

	/**
	 * Stops all game systems.
	 * Called when closing the game to stop all running threads.
	 */
	public void clear() {
		this.systems.stopAll();
	}
}
