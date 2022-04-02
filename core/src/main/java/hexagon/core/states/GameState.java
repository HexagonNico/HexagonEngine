package hexagon.core.states;

import java.util.HashMap;
import java.util.Optional;

import hexagon.core.GameEntity;
import hexagon.core.components.Component;

public final class GameState {
	
	private static GameState currentState = new GameState();

	public static GameState current() {
		return currentState;
	}

	public static synchronized void loadState(String filePath) {
		currentState = StateLoader.loadState(new GameState(), filePath);
	}

	public static synchronized void update() {
		currentState.components.forEach((type, map) -> {
			map.values().removeIf(Component::markedForRemoval);
		});
	}
/*
	public static void stopSystems() {
		currentState.systems.values().forEach(SystemRunner::shutdown);
	}
*/
	private final HashMap<Class<?>, HashMap<GameEntity, Component>> components;
	//private final HashMap<Class<?>, SystemRunner<?>> systems;

	private GameState() {
		this.components = new HashMap<>();
		//this.systems = new HashMap<>();
	}

	public void addComponent(GameEntity entity, Component component) {
		Class<?> componentKey = this.getComponentKey(component.getClass());
		if(this.components.containsKey(componentKey)) {
			this.components.get(componentKey).put(entity, component);
		} else {
			HashMap<GameEntity, Component> map = new HashMap<>();
			map.put(entity, component);
			this.components.put(componentKey, map);
		}
	}

	public <T extends Component> Optional<T> findComponent(GameEntity entity, Class<T> type) {
		if(entity != null && type != null) {
			Class<?> componentKey = this.getComponentKey(type);
			if(this.components.containsKey(componentKey)) {
				HashMap<GameEntity, Component> components = this.components.get(componentKey);
				if(components.containsKey(entity)) {
					return Optional.of(type.cast(components.get(entity)));
				}
			}
		}
		return Optional.empty();
	}

	public HashMap<GameEntity, Component> getComponents(Class<?> type) {
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
		return superClass.equals(Component.class) ? componentType : this.getComponentKey(superClass);
	}
/*
	public <T extends Component> void startSystem(GameSystem<T> system, Class<T> componentType) {
		if(system != null && componentType != null) {
			SystemRunner<T> runner = new SystemRunner<>(system, this, componentType);
			this.systems.put(system.getClass(), runner);
		}
	}
*/
}