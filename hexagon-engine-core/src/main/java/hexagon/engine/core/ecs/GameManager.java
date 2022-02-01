package hexagon.engine.core.ecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import hexagon.engine.core.scene.SceneLoader;

/**
 * Class that holds all the components that are currently in the scene and all the running systems.
 * 
 * @author Nico
 */
public final class GameManager {
	
	/**Table of components that uses entities and component's classes as rows and columns of a table */
	private final HashMap<GameEntity, HashMap<Class<?>, Object>> componentsTable;
	/**List of all running game systems */
	private final ArrayList<GameSystem> systems;

	/**
	 * Creates game manager.
	 */
	public GameManager() {
		this.componentsTable = new HashMap<>();
		this.systems = new ArrayList<>();
	}

	/**
	 * Creates an entity with the first free id.
	 * 
	 * @return The newly created game entity.
	 */
	public GameEntity createEntity() {
		GameEntity entity = new GameEntity(this, this.nextId());
		this.componentsTable.put(entity, new HashMap<>());
		return entity;
	}

	/**
	 * Adds a component to an entity.
	 * 
	 * @param entity The entity to add the component to.
	 * @param component The component to add.
	 */
	public void addComponent(GameEntity entity, Object component) {
		this.componentsTable.get(entity).put(component.getClass(), component);
	}

	/**
	 * Adds a game system.
	 * 
	 * @param system Constructor of the system to add.
	 */
	public void addSystem(Function<GameManager, GameSystem> system) {
		// TODO - Add systems to scene file (?)
		this.systems.add(system.apply(this));
	}

	/**
	 * Loads a scene by deleting all entities in the game manager
	 * and adding all the entities from the scene file.
	 * 
	 * @param sceneFile Path to the scene file from the resources folder starting with {@code /}.
	 */
	public void loadScene(String sceneFile) {
		this.componentsTable.clear();
		SceneLoader.loadScene(sceneFile, this);
	}

	/**
	 * Runs all systems.
	 * Called in main game loop.
	 */
	public void update() {
		this.systems.forEach(system -> {
			system.beforeAll();
			this.componentsTable.entrySet().stream()
				.filter(entry -> system.componentsMatch(entry.getValue().values()))
				.forEach(entry -> system.process(entry.getKey()));
			system.afterAll();
		});
	}

	/**
	 * Gets a component from an entity.
	 * 
	 * @param <T> Type of the component.
	 * @param entity Entity holding the component.
	 * @param type Class of the component.
	 * 
	 * @return The requested component.
	 */
	public <T> T getComponent(GameEntity entity, Class<T> type) {
		return type.cast(this.componentsTable.get(entity).get(type));
	}

	/**
	 * Gets one of the active systems.
	 * 
	 * @param <T> Type of the system.
	 * @param type Class of the system.
	 * 
	 * @return The requested system.
	 */
	public <T extends GameSystem> T getSystem(Class<T> type) {
		return type.cast(this.systems.stream().filter(type::isInstance).findFirst());
	}

	/**
	 * Removes an entity from the scene.
	 * 
	 * @param entity The entity to remove.
	 */
	public void removeEntity(GameEntity entity) {
		this.componentsTable.remove(entity);
	}

	/**
	 * Gets the next free id to create an entity.
	 * 
	 * @return The first unused id.
	 */
	private int nextId() {
		int id = 0;
		List<Integer> entities = this.componentsTable.keySet().stream()
			.map(entity -> entity.id)
			.toList();
		while(entities.contains(id))
			id++;
		return id;
	}
}
