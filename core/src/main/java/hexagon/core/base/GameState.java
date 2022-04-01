package hexagon.core.base;

import java.util.HashMap;
import java.util.Optional;

import hexagon.core.components.SpriteComponent;
import hexagon.core.rendering.SpriteRenderer;
import hexagon.utils.Log;
import hexagon.utils.json.JsonObject;

public final class GameState {
	
	private static GameState currentState = new GameState();

	public static void loadState(String filePath) {
		JsonObject stateJson = JsonObject.fromFileOrEmpty(filePath);
		currentState = new GameState();
		stateJson.getArrayOrEmpty("entities").forEachObject(entityJson -> {
			GameEntity entity = new GameEntity(currentState);
			entityJson.keySet().forEach(componentKey -> {
				try {
					JsonObject componentJson = entityJson.getObjectOrEmpty(componentKey);
					Object component = Class.forName(componentKey).getConstructor(JsonObject.class).newInstance(componentJson);
					currentState.addComponent(entity, component);
				} catch (Exception e) {
					Log.error("Cannot instantiate component " + componentKey + ": " + e.getMessage());
				}
			});
		});
		// Temporary
		currentState.startSystem(new SpriteRenderer(), SpriteComponent.class);
	}

	public static void stopSystems() {
		currentState.systems.values().forEach(SystemRunner::shutdown);
	}

	private final HashMap<Class<?>, HashMap<GameEntity, Object>> components;
	private final HashMap<Class<?>, SystemRunner<?>> systems;

	private GameState() {
		this.components = new HashMap<>();
		this.systems = new HashMap<>();
	}

	public void addComponent(GameEntity entity, Object component) {
		Class<?> componentKey = this.getComponentKey(component.getClass());
		if(this.components.containsKey(componentKey)) {
			this.components.get(componentKey).put(entity, component);
		} else {
			HashMap<GameEntity, Object> map = new HashMap<>();
			map.put(entity, component);
			this.components.put(componentKey, map);
		}
	}

	public <T> Optional<T> findComponent(GameEntity entity, Class<T> type) {
		if(entity != null && type != null) {
			Class<?> componentKey = this.getComponentKey(type);
			if(this.components.containsKey(componentKey)) {
				HashMap<GameEntity, Object> components = this.components.get(componentKey);
				if(components.containsKey(entity)) {
					return Optional.of(type.cast(components.get(entity)));
				}
			}
		}
		return Optional.empty();
	}

	public HashMap<GameEntity, Object> getComponents(Class<?> type) {
		if(type != null) {
			Class<?> componentKey = this.getComponentKey(type);
			if(this.components.containsKey(componentKey)) {
				return this.components.get(componentKey);
			}
		}
		return new HashMap<>();
	}

	private Class<?> getComponentKey(Class<?> componentType) {
		Class<?> superClass = componentType.getSuperclass();
		return superClass.equals(Object.class) ? componentType : this.getComponentKey(superClass);
	}

	public <T> void startSystem(GameSystem<T> system, Class<T> componentType) {
		if(system != null && componentType != null) {
			SystemRunner<T> runner = new SystemRunner<>(system, this, componentType);
			this.systems.put(system.getClass(), runner);
		}
	}
}
