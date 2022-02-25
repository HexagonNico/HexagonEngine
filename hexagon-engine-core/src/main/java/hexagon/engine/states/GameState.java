package hexagon.engine.states;

import java.util.HashMap;
import java.util.Optional;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.ecs.GameSystem;

/**
 * Class that represents a game state.
 * <p>
 * 	A game state is a container for entities and game systems.
 * 	Examples of game states can be {@code MainMenu}, {@code PlayingState}, or {@code GameOver}.
 * </p>
 * <p>
 * 	A state should contain all the parts of the game that follow the same logic.
 * 	For example, a {@code MainMenu} state should handle guis and loading saved data,
 *  a {@code PlayingState} should handle the actual game's logic.
 * </p>
 * <p>
 * 	A state should not be considered as a "level" or as a "scene".
 * 	For example, a {@code LevelOne} state and a {@code LevelTwo} state should not be separate states, as they both follow the same logic.
 * 	It is better to have a single {@code Playing} state and add/remove entities when a level changes.
 * 	The same applies for states such as {@code TitleScreen} and {@code OptionsMenu}, which would work better in a single {@code MainMenu} state.
 * </p>
 * 
 * @author Nico
 */
public abstract class GameState {

	/**Table that holds all the entities (or their components) in the state */
	private final HashMap<Class<?>, HashMap<GameEntity, Component>> componentsTable;
	/**Map that contains all the systems running in this state */
	private final HashMap<Class<?>, GameSystem<?>> systems;

	/**
	 * Creates a game state.
	 */
	public GameState() {
		this.componentsTable = new HashMap<>();
		this.systems = new HashMap<>();
	}

	/**
	 * Creates a new {@link GameEntity} from this state.
	 * 
	 * @return A newly created {@link GameEntity}
	 */
	public final GameEntity createEntity() {
		return new GameEntity(this);
	}

	/**
	 * Adds a component to an entity by adding it to the components table.
	 * 
	 * @param entity The entity to add the component to
	 * @param component The component to add
	 */
	public final void addComponent(GameEntity entity, Component component) {
		if(this.componentsTable.containsKey(component.getClass())) {
			this.componentsTable.get(component.getClass()).put(entity, component);
		} else {
			HashMap<GameEntity, Component> componentsMap = new HashMap<>();
			componentsMap.put(entity, component);
			this.componentsTable.put(component.getClass(), componentsMap);
		}
	}
	
	/**
	 * Gets a component from an entity.
	 * 
	 * @param <T> Type of the component
	 * @param entity The entity that has that component
	 * @param type Class of the component as in {@code Component.class}
	 * 
	 * @return An {@link Optional} containing the requested component or an empty {@link Optional} if that component does not exist
	 */
	public final <T> Optional<T> getComponent(GameEntity entity, Class<T> type) {
		if(this.componentsTable.containsKey(type)) {
			if(this.componentsTable.get(type).containsKey(entity))
				return Optional.of(type.cast(this.componentsTable.get(type).get(entity)));
		}
		return Optional.empty();
	}
	
	/**
	 * Removes an entity by removing all of its components from the table.
	 * 
	 * @param entity The entity to remove
	 */
	public final void removeEntity(GameEntity entity) {
		this.componentsTable.values().forEach(componentMap -> {
			componentMap.entrySet().removeIf(pair -> pair.getKey().equals(entity));
		});
	}

	/**
	 * Called when a state is added to the {@link GameManager}
	 */
	public abstract void onStart();

	/**
	 * Runs all the game systems of this state.
	 * Called in {@link GameManager#update()}
	 */
	public final void runSystems() {
		this.systems.values().forEach(system -> system.run(this.componentsTable.get(system.componentType).values()));
	}

	/**
	 * Called every frame after all systems have been processed.
	 * Can be used for everything that needs to run every frame but is not related to a system.
	 */
	public abstract void onUpdate();

	/**
	 * Called before a state is changed.
	 */
	public abstract void onExit();
}
