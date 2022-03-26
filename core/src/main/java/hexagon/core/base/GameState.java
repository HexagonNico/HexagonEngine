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
		stateJson.getArray("entities").ifPresent(entitiesArray -> entitiesArray.forEachObject(entityJson -> {
			GameEntity entity = new GameEntity();
			HashMap<JsonObject, Component> loadingComponents = new HashMap<>();
			entityJson.getObject("components").ifPresent(componentsJson -> {
				componentsJson.keySet().forEach(componentKey -> {
					try {
						Component component = (Component) Class.forName(componentKey).getConstructor(GameEntity.class).newInstance(entity);
						loadingComponents.put(componentsJson.getObject(componentKey).orElse(JsonObject.empty()), component);
						entity.addComponent(component);
					} catch (NoSuchMethodException e) {
						Log.error("Cannot instantiate component " + componentKey + ": missing constructor");
					} catch (ClassNotFoundException e) {
						Log.error("Cannot instantiate component " + componentKey + ": class not found");
					} catch (Exception e) {
						Log.error("Cannot instantiate component " + componentKey + ": " + e.getMessage());
					}
				});
			});
			loadingComponents.forEach((jsonObject, component) -> component.init(jsonObject));
			entityJson.getObject("scripts").ifPresent(scriptsJson -> {
				scriptsJson.keySet().forEach(scriptKey -> {
					try {
						Script script = (Script) Class.forName(scriptKey).getConstructor(GameEntity.class).newInstance(entity);
						entity.addScript(script);
					} catch (NoSuchMethodException e) {
						Log.error("Cannot instantiate script " + scriptKey + ": missing constructor");
					} catch (ClassNotFoundException e) {
						Log.error("Cannot instantiate script " + scriptKey + ": class not found");
					} catch (Exception e) {
						Log.error("Cannot instantiate script " + scriptKey + ": " + e.getMessage());
					}
				});
			});
			entitiesList.add(entity);
		}));
		currentState = new GameState(entitiesList);
	}

	private ArrayList<GameEntity> entities = new ArrayList<>();

	public GameState(ArrayList<GameEntity> entities) {
		this.entities = entities;
	}
}
