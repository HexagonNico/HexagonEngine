package hexagon.engine.core.ecs;

import java.util.HashMap;
import java.util.Optional;

/**
 * Class that holds all the components that are currently in the scene and all the running systems.
 * 
 * @author Nico
 */
public final class GameManager {
	
	/**Table that holds all the components of all entities in the scene */
	private final HashMap<Class<?>, HashMap<GameEntity, Component>> componentsTable;
	/**Map that holds all the running systems */
	private final HashMap<Class<?>, GameSystem<?>> systems;
	/**Number used to give a unique id to all entities */
	private int nextEntityId;

	/**
	 * Creates game manager by initializing maps.
	 */
	public GameManager() {
		this.componentsTable = new HashMap<>();
		this.systems = new HashMap<>();
		this.nextEntityId = 0;
	}

	/**
	 * Creates a {@link GameEntity}. Use this instead of the {@code GameEntity} constructor.
	 * 
	 * @return A newly instantiated game entity.
	 */
	public GameEntity createEntity() {
		return new GameEntity(this, this.nextEntityId++);
	}

	/**
	 * Adds a component to an entity by putting in in the right place in the table.
	 * An entity can only hold one component of each type,
	 * adding a new component will replace the old one.
	 * 
	 * @param entity The entity to which the component should be added.
	 * @param component The component to add.
	 */
	public void addComponent(GameEntity entity, Component component) {
		if(this.componentsTable.containsKey(component.getClass())) {
			this.componentsTable.get(component.getClass()).put(entity, component);
		} else {
			HashMap<GameEntity, Component> componentsMap = new HashMap<>();
			componentsMap.put(entity, component);
			this.componentsTable.put(component.getClass(), componentsMap);
		}
	}

	/**
	 * Adds a system so that it will run every frame.
	 * 
	 * @param system The system to add.
	 */
	public void addSystem(GameSystem<?> system) {
		this.systems.put(system.getClass(), system);
	}

	/**
	 * Called every frame.
	 * Runs all systems.
	 */
	public void update() {
		this.systems.values().forEach(system -> system.run(this.componentsTable.get(system.componentType).values()));
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
	public <T> Optional<T> getComponent(GameEntity entity, Class<T> type) {
		if(this.componentsTable.containsKey(type)) {
			if(this.componentsTable.get(type).containsKey(entity))
				return Optional.of(type.cast(this.componentsTable.get(type).get(entity)));
		}
		return Optional.empty();
	}

	/**
	 * Removes all the components of the given entity from the components table,
	 * thus removing the entity.
	 * 
	 * @param entity The entity to remove.
	 */
	public void removeEntity(GameEntity entity) {
		this.componentsTable.values().forEach(componentMap -> {
			componentMap.entrySet().removeIf(pair -> pair.getKey().equals(entity));
		});
	}
}
