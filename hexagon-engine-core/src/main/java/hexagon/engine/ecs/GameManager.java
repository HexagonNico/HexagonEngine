package hexagon.engine.ecs;

import java.util.HashMap;
import java.util.Optional;

/**
 * Class that holds all the components that are currently in the scene and all the running systems.
 * 
 * @author Nico
 */
public final class GameManager {
	
	/**Class should not be instantiated */
	private GameManager() {}

	/**Table that holds all the components of all entities in the scene */
	private static final HashMap<Class<?>, HashMap<GameEntity, Component>> componentsTable = new HashMap<>();
	/**Map that holds all the running systems */
	private static final HashMap<Class<?>, GameSystem<?>> systems = new HashMap<>();
	/**Number used to give a unique id to all entities */
	private static int nextEntityId;

	/**
	 * Creates a {@link GameEntity}. Use this instead of the {@code GameEntity} constructor.
	 * 
	 * @return A newly instantiated game entity.
	 */
	public static GameEntity createEntity() {
		return new GameEntity(nextEntityId++);
	}

	/**
	 * Adds a component to an entity by putting in in the right place in the table.
	 * An entity can only hold one component of each type,
	 * adding a new component will replace the old one.
	 * 
	 * @param entity The entity to which the component should be added.
	 * @param component The component to add.
	 */
	public static void addComponent(GameEntity entity, Component component) {
		if(componentsTable.containsKey(component.getClass())) {
			componentsTable.get(component.getClass()).put(entity, component);
		} else {
			HashMap<GameEntity, Component> componentsMap = new HashMap<>();
			componentsMap.put(entity, component);
			componentsTable.put(component.getClass(), componentsMap);
		}
	}

	/**
	 * Adds a system so that it will run every frame.
	 * 
	 * @param system The system to add.
	 */
	public static void addSystem(GameSystem<?> system) {
		systems.put(system.getClass(), system);
	}

	/**
	 * Called every frame.
	 * Runs all systems.
	 */
	public static void update() {
		systems.values().forEach(system -> system.run(componentsTable.get(system.componentType).values()));
	}

	/**
	 * Gets a component from an entity in the scene.
	 * 
	 * @param <T> Type of the component.
	 * @param entity The entity that holds the component.
	 * @param type Type of the component to get, since an entity can only have one component of each type.
	 * 
	 * @return The requested component.
	 */
	public static <T> Optional<T> getComponent(GameEntity entity, Class<T> type) {
		if(componentsTable.containsKey(type)) {
			if(componentsTable.get(type).containsKey(entity))
				return Optional.of(type.cast(componentsTable.get(type).get(entity)));
		}
		return Optional.empty();
	}

	/**
	 * Removes all the components of the given entity from the components table,
	 * thus removing the entity.
	 * 
	 * @param entity The entity to remove.
	 */
	public static void removeEntity(GameEntity entity) {
		componentsTable.values().forEach(componentMap -> {
			componentMap.entrySet().removeIf(pair -> pair.getKey().equals(entity));
		});
	}
}
