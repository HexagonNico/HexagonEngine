package hexagon.engine.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.ecs.components.Camera;
import hexagon.engine.ecs.components.LightComponent;
import hexagon.engine.ecs.components.ModelComponent;
import hexagon.engine.ecs.components.ReflectivityComponent;
import hexagon.engine.ecs.components.SpriteComponent;
import hexagon.engine.ecs.components.TexturedModelComponent;
import hexagon.engine.ecs.components.Transform2D;
import hexagon.engine.ecs.components.Transform3D;
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

	/**Registry containing all components that can be instantiated */
	private static final HashMap<String, Function<GameEntity, ? extends Component>> componentsRegistry = new HashMap<>();

	static {
		registerComponent("hexagon.engine.Transform2D", Transform2D::new);
		registerComponent("hexagon.engine.Transform3D", Transform3D::new);
		registerComponent("hexagon.engine.SpriteComponent", SpriteComponent::new);
		registerComponent("hexagon.engine.Camera", Camera::new);
		registerComponent("hexagon.engine.ModelComponent", ModelComponent::new);
		registerComponent("hexagon.engine.TexturedModelComponent", TexturedModelComponent::new);
		registerComponent("hexagon.engine.LightComponent", LightComponent::new);
		registerComponent("hexagon.engine.ReflectivityComponent", ReflectivityComponent::new);
	}

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
				if(componentsRegistry.containsKey(key)) {
					JsonObject componentJson = entityJson.getObject(key).orElse(JsonObject.empty());
					Component component = entity.addComponent(componentsRegistry.get(key));
					component.init(loadedEntities, componentJson);
				} else {
					Log.error("Component " + key + " is not in registry");
				}
			});
		});
		if(rootEntity >= 0 && rootEntity < loadedEntities.size()) {
			return loadedEntities.get(rootEntity);
		} else {
			return null;
		}
	}

	/**
	 * Registers a component.
	 * Only registered components can be loaded from entities files.
	 * 
	 * @param key Key to be used in the json files to create this component
	 * @param constructor The component's constructor passed as a method reference
	 */
	public static void registerComponent(String key, Function<GameEntity, ? extends Component> constructor) {
		if(!componentsRegistry.containsKey(key)) {
			componentsRegistry.put(key, constructor);
		} else {
			Log.error("Registry already contains component with key " + key);
		}
	}

	/**Class should not be instantiated */
	private EntitiesLoader() {}
}
