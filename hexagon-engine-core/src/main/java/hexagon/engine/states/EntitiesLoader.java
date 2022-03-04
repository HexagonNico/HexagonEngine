package hexagon.engine.states;

import java.util.ArrayList;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.utils.Log;
import hexagon.engine.utils.json.JsonArray;
import hexagon.engine.utils.json.JsonObject;

/**
 * Static utility class used to load entities files.
 * <p>
 * 	Entities files are json files that contain entities and their components
 * 	and are used to instantiate entities in a state.
 * </p>
 * 
 * @author Nico
 */
public final class EntitiesLoader {
	
	/**
	 * Loads an entities file by adding all the entities the given state.
	 * 
	 * @param state The state in which the entities should be loaded
	 * @param entitiesFile Path to the entities file, from the resources folder, starting with {@code /}
	 */
	public static void loadEntities(GameState state, String entitiesFile) {
		loadEntities(state, entitiesFile, -1);
	}

	/**
	 * Loads an entities file by adding all the entities the given state
	 * and returns one of the loaded entities.
	 * 
	 * @param state The state in which the entities should be loaded
	 * @param entitiesFile Path to the entities file, from the resources folder, starting with {@code /}
	 * @param rootEntity Index of the entity to be returned
	 * 
	 * @return The entity with the given index or {@code null} if the index is out of bounds
	 */
	public static GameEntity loadEntities(GameState state, String entitiesFile, int rootEntity) {
		JsonObject jsonObject = JsonObject.fromFileOrEmpty(entitiesFile);
		JsonArray entitiesJsonArray = jsonObject.getArray("entities").orElse(JsonArray.empty());
		ArrayList<GameEntity> loadedEntities = new ArrayList<>();
		entitiesJsonArray.forEachObject(entityJson -> {
			GameEntity entity = state.createEntity();
			loadedEntities.add(entity);
			entityJson.keySet().forEach(key -> {
				try {
					JsonObject componentJson = entityJson.getObjectOrEmpty(key);
					Component component = (Component) Class.forName(key).getConstructor(GameEntity.class).newInstance(entity);
					component.init(loadedEntities, componentJson);
					state.addComponent(entity, component);
				} catch (NoSuchMethodException e) {
					Log.error("Cannot instantiate component " + key + ": missing constructor");
				} catch (ClassNotFoundException e) {
					Log.error("Class " + key + " does not exist");
				} catch (ClassCastException e) {
					Log.error("Class " + key + " is not a component");
				} catch (Exception e) {
					Log.error("Cannot instantiate class " + key + ": " + e.getMessage());
				}
			});
		});
		if(rootEntity >= 0 && rootEntity < loadedEntities.size()) {
			return loadedEntities.get(rootEntity);
		} else {
			return null;
		}
	}

	/**Class should not be instantiated */
	private EntitiesLoader() {}
}
