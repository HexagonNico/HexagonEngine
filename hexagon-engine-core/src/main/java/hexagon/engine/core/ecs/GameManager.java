package hexagon.engine.core.ecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class GameManager {
	
	private final HashMap<GameEntity, HashMap<Class<?>, Object>> componentsTable;
	private final ArrayList<GameSystem> systems;

	public GameManager() {
		this.componentsTable = new HashMap<>();
		this.systems = new ArrayList<>();
	}

	public GameEntity createEntity() {
		GameEntity entity = new GameEntity(this, this.nextId());
		this.componentsTable.put(entity, new HashMap<>());
		return entity;
	}

	public void addComponent(GameEntity entity, Object component) {
		this.componentsTable.get(entity).put(component.getClass(), component);
	}

	public void addSystem(GameSystem system) {
		this.systems.add(system);
	}

	public void update() {
		this.systems.forEach(system -> {
			system.beforeAll();
			this.componentsTable.entrySet().stream()
				.filter(entry -> system.componentsMatch(entry.getValue().values()))
				.forEach(entry -> system.process(entry.getKey()));
			system.afterAll();
		});
	}

	public <T> T getComponent(GameEntity entity, Class<T> type) {
		return type.cast(this.componentsTable.get(entity).get(type));
	}

	public void removeEntity(GameEntity entity) {
		this.componentsTable.remove(entity);
	}

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
