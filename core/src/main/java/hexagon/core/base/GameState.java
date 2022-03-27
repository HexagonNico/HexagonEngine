package hexagon.core.base;

import java.util.ArrayList;
import java.util.HashMap;

import hexagon.utils.Log;
import hexagon.utils.json.JsonObject;

public final class GameState {
	
	private static GameState currentState = new GameState(new ArrayList<>());

	public static void update() {
		currentState.entities.forEach(GameEntity::update);
	}

	public static void loadState(String filePath) {
		JsonObject stateJson = JsonObject.fromFileOrEmpty(filePath);
		ArrayList<GameEntity> entitiesList = new ArrayList<>();
		stateJson.getArrayOrEmpty("entities").forEachObject(entityJson -> {
			GameEntity entity = new GameEntity();
			HashMap<JsonObject, Component> loadingComponents = new HashMap<>();
			entityJson.getObject("components").ifPresent(componentsJson -> {
				componentsJson.keySet().forEach(componentKey -> createComponent(componentKey, entity, componentsJson, loadingComponents));
			});
			loadingComponents.forEach((jsonObject, component) -> component.init(jsonObject));
			entityJson.getObject("scripts").ifPresent(scriptsJson -> {
				scriptsJson.keySet().forEach(scriptKey -> createScript(scriptKey, entity));
			});
			entitiesList.add(entity);
		});
		currentState = new GameState(entitiesList);
	}

	private static void createComponent(String className, GameEntity entity, JsonObject componentsJson, HashMap<JsonObject, Component> loadingComponents) {
		try {
			Component component = (Component) Class.forName(className).getConstructor(GameEntity.class).newInstance(entity);
			loadingComponents.put(componentsJson.getObject(className).orElse(JsonObject.empty()), component);
			entity.addComponent(component);
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
			entity.addScript(script);
		} catch (NoSuchMethodException e) {
			Log.error("Cannot find constructor for " + className + ": define a constructor that takes in a GameEntity parameter");
		} catch (ClassNotFoundException e) {
			Log.error("Cannot find class for script " + className);
		} catch (Exception e) {
			Log.error("Cannot instantiate script " + className + ": " + e.getMessage());
		}
	}

	private ArrayList<GameEntity> entities = new ArrayList<>();

	public GameState(ArrayList<GameEntity> entities) {
		this.entities = entities;
	}
}
