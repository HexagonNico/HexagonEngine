package hexagon.engine.core.ecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	 * @param system The system to add.
	 */
	public void addSystem(GameSystem system) {
		this.systems.add(system);
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