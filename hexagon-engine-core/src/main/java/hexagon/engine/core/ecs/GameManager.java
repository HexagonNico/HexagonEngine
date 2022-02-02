package hexagon.engine.core.ecs;

import java.util.HashMap;

/**
 * Class that holds all the components that are currently in the scene and all the running systems.
 * 
 * @author Nico
 */
public final class GameManager {
	
	private final HashMap<Class<?>, HashMap<GameEntity, Object>> componentsTable;
	private int entityCount;

	public GameManager() {
		this.componentsTable = new HashMap<>();
		this.entityCount = 0;
	}

	public GameEntity createEntity() {
		return new GameEntity(this, this.entityCount++);
	}

	public void addComponent(GameEntity entity, Object component) {
		if(this.componentsTable.containsKey(component.getClass())) {
			this.componentsTable.get(component.getClass()).put(entity, component);
		} else {
			HashMap<GameEntity, Object> componentsMap = new HashMap<>();
			componentsMap.put(entity, component);
			this.componentsTable.put(component.getClass(), componentsMap);
		}
	}

	public void update() {
		System.out.println(this.componentsTable);
	}

	public <T> T getComponent(GameEntity entity, Class<T> type) {
		// TODO - Check for null values
		return type.cast(this.componentsTable.get(type).get(entity));
	}

	public void removeEntity(GameEntity entity) {
		this.componentsTable.values().forEach(componentMap -> {
			componentMap.entrySet().removeIf(pair -> pair.getKey().equals(entity));
		});
	}
}
