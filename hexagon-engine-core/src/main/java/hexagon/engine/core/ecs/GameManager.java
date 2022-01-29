package hexagon.engine.core.ecs;

import java.util.HashMap;

public final class GameManager {
	
	private final HashMap<Integer, HashMap<Class<?>, Object>> componentsTable;

	public GameManager() {
		this.componentsTable = new HashMap<>();
	}

	public GameEntity createEntity() {
		int id = this.nextId();
		this.componentsTable.put(id, new HashMap<>());
		return new GameEntity(id);
	}

	public void addComponent(GameEntity entity, Object component) {
		System.out.println(component.getClass());
		this.componentsTable.get(entity.id).put(component.getClass(), component);
	}

	public void test() {
		System.out.println(this.componentsTable);
	}

	public void removeEntity(GameEntity entity) {
		this.componentsTable.remove(entity.id);
	}

	private int nextId() {
		int id = 0;
		while(id <= this.componentsTable.size()) {
			if(this.componentsTable.containsKey(id)) id++;
			else break;
		}
		return id;
	}
}
