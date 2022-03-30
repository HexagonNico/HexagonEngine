package hexagon.core.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

import hexagon.utils.Log;
import hexagon.utils.json.JsonObject;

public final class GameState {
	
	private static GameState currentState = new GameState();

	public static void loadState(String filePath) {
		JsonObject stateJson = JsonObject.fromFileOrEmpty(filePath);
		currentState = new GameState();
		stateJson.getArrayOrEmpty("entities").forEachObject(entityJson -> {
			GameEntity entity = new GameEntity(currentState);
			HashMap<JsonObject, Component> loadingComponents = new HashMap<>();
			entityJson.getObject("components").ifPresent(componentsJson -> {
				componentsJson.keySet().forEach(componentKey -> createComponent(componentKey, entity, componentsJson, loadingComponents));
			});
			loadingComponents.forEach((jsonObject, component) -> component.init(jsonObject));
			// TODO - Scripts can be arrays of strings
			entityJson.getObject("scripts").ifPresent(scriptsJson -> {
				scriptsJson.keySet().forEach(scriptKey -> createScript(scriptKey, entity));
			});
		});
	}

	private static void createComponent(String className, GameEntity entity, JsonObject componentsJson, HashMap<JsonObject, Component> loadingComponents) {
		try {
			Component component = (Component) Class.forName(className).getConstructor(GameEntity.class).newInstance(entity);
			loadingComponents.put(componentsJson.getObject(className).orElse(JsonObject.empty()), component);
			currentState.addComponent(entity, component);
		} catch (NoSuchMethodException e) {
			Log.error("Cannot find constructor for " + className + ": define a constructor that takes in a GameEntity parameter");
		} catch (ClassNotFoundException e) {
			Log.error("Cannot find class for component " + className);
		} catch (Exception e) {
			Log.error("Cannot instantiate component " + className + ": " + e.getMessage());
		}
	}

	private static void createScript(String className, GameEntity entity) {
		try {
			Script script = (Script) Class.forName(className).getConstructor(GameEntity.class).newInstance(entity);
			currentState.addScript(entity, script);
		} catch (NoSuchMethodException e) {
			Log.error("Cannot find constructor for " + className + ": define a constructor that takes in a GameEntity parameter");
		} catch (ClassNotFoundException e) {
			Log.error("Cannot find class for script " + className);
		} catch (Exception e) {
			Log.error("Cannot instantiate script " + className + ": " + e.getMessage());
		}
	}

	public static void terminate() {
		currentState.scripts.values().stream().flatMap(ArrayList::stream).forEach(Script::stop);
	}

	private final HashMap<Class<?>, HashMap<GameEntity, Component>> components;
	private final HashMap<GameEntity, ArrayList<Script>> scripts;

	private GameState() {
		this.components = new HashMap<>();
		this.scripts = new HashMap<>();
	}

	private void addComponent(GameEntity entity, Component component) {
		Class<?> componentKey = this.getComponentKey(component.getClass());
		if(this.components.containsKey(componentKey)) {
			this.components.get(componentKey).put(entity, component);
		} else {
			HashMap<GameEntity, Component> map = new HashMap<>();
			map.put(entity, component);
			this.components.put(componentKey, map);
		}
	}

	public <T extends Component> T addComponent(GameEntity entity, Function<GameEntity, T> constructor) {
		if(entity != null && constructor != null) {
			T component = constructor.apply(entity);
			this.addComponent(entity, component);
			return component;
		}
		return null;
	}

	public <T extends Component> Optional<T> getComponent(GameEntity entity, Class<T> type) {
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

	private Class<?> getComponentKey(Class<?> componentType) {
		Class<?> superClass = componentType.getSuperclass();
		return superClass.equals(Component.class) ? componentType : this.getComponentKey(superClass);
	}

	private void addScript(GameEntity entity, Script script) {
		if(this.scripts.containsKey(entity)) {
			this.scripts.get(entity).add(script);
		} else {
			ArrayList<Script> list = new ArrayList<>();
			list.add(script);
			this.scripts.put(entity, list);
		}
	}

	public <T extends Script> T addScript(GameEntity entity, Function<GameEntity, T> constructor) {
		if(entity != null && constructor != null) {
			T script = constructor.apply(entity);
			this.addScript(entity, script);
			return script;
		}
		return null;
	}

	// TODO - Get script?
}
