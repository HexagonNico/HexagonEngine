package hexagon.engine.core.scene;

import java.util.HashMap;
import java.util.function.Function;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hexagon.engine.core.components.Camera3D;
import hexagon.engine.core.components.ModelComponent;
import hexagon.engine.core.components.SpriteComponent;
import hexagon.engine.core.components.TexturedModelComponent;
import hexagon.engine.core.components.Transform2D;
import hexagon.engine.core.components.Transform3D;
import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.core.ecs.GameManager;
import hexagon.engine.utils.Log;
import hexagon.engine.utils.resources.ResourceLoadingException;
import hexagon.engine.utils.resources.Resources;

/**
 * Utility class used to load a scene to the {@link GameManager}.
 * Scenes are json files containing a bundle of entities.
 * 
 * @author Nico
 */
public final class SceneLoader {
	
	/**Component registry used to create component from keys in the json file */
	private static final HashMap<String, Function<JSONObject, Object>> componentsRegistry = new HashMap<>();

	static {
		registerComponent(SpriteComponent.class, SpriteComponent::new);
		registerComponent(Transform2D.class, Transform2D::new);
		registerComponent(Transform3D.class, Transform3D::new);
		registerComponent(Camera3D.class, Camera3D::new);
		registerComponent(ModelComponent.class, ModelComponent::new);
		registerComponent(TexturedModelComponent.class, TexturedModelComponent::new);
	}

	/**
	 * Adds a component to the components registry.
	 * All components need to be registered to be loaded from an entity's json object.
	 * 
	 * @param componentClass Class of the component object, used as the json key.
	 * @param componentFactory A method that creates the component from the json object.
	 * 		Can be the component's constructor, declared as {@code Component(JSONObject jsonObject)} and passed as {@code Component::new}
	 * 		or any other method with the signature {@code Component method(JSONObject jsonObject)}.
	 */
	public static void registerComponent(Class<?> componentClass, Function<JSONObject, Object> componentFactory) {
		componentsRegistry.put(componentClass.getName(), componentFactory);
	}

	/**
	 * Loads a scene to the game manager.
	 * Will read a scene json file and add all entities with their components to the game manager.
	 * 
	 * @param filePath Path to the scene file from the resources folder starting with {@code /}.
	 * @param gameManager Reference to the game manager to add entities.
	 */
	public static void loadScene(String filePath, GameManager gameManager) {
		Log.info("Loading scene " + filePath);
		try {
			JSONObject sceneJson = new JSONObject(Resources.readAsString(filePath));
			JSONArray entitiesArray = sceneJson.getJSONArray("entities");
			for(int i = 0; i < entitiesArray.length(); i++) {
				JSONObject entityJson = entitiesArray.getJSONObject(i);
				GameEntity entity = gameManager.createEntity();
				for(String componentKey : entityJson.keySet()) {
					Function<JSONObject, Object> component = componentsRegistry.get(componentKey);
					JSONObject componentJson = entityJson.getJSONObject(componentKey);
					if(component != null) {
						entity.addComponent(component.apply(componentJson));
					} else {
						Log.error("Could not find component " + componentKey + " in registry");
					}
				}
			}
		} catch (JSONException | ResourceLoadingException e) {
			Log.error("Could not read scene file " + filePath);
		}
	}
}
