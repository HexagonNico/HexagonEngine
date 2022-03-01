package hexagon.engine.states;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.utils.Log;
import hexagon.engine.utils.json.JsonArray;
import hexagon.engine.utils.json.JsonObject;

public final class EntitiesLoader {
	
	public static void loadEntities(GameState state, String entitiesFile) {
		JsonObject jsonObject = JsonObject.fromFileOrEmpty(entitiesFile);
		JsonArray entitiesJsonArray = jsonObject.getArray("entities").orElse(JsonArray.empty());
		entitiesJsonArray.forEachObject(entityJson -> {
			GameEntity entity = state.createEntity();
			entityJson.keySet().forEach(key -> {
				try {
					JsonObject componentJson = entityJson.getObject(key).orElse(JsonObject.empty());
					Component component = (Component) Class.forName(key).getConstructor(GameEntity.class).newInstance(entity);
					component.init(componentJson);
				} catch (NoSuchMethodException e) {
					Log.error("Cannot instantiate component of class " + key + ": Constructor cannot be found");
				} catch (ClassNotFoundException e) {
					Log.error("Class " + key + " does not exist");
				} catch (ClassCastException e) {
					Log.error("Class " + key + " does not inherit from Component");
				} catch (Exception e) {
					Log.error("Cannot instantiate class " + key);
				}
			});
		});
	}

	private EntitiesLoader() {}
}
