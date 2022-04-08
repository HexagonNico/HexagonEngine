package hexagon.core.systems;

import hexagon.core.GameEntity;
import hexagon.core.components.Script;
import hexagon.utils.Log;

/**
 * Game system that handles the running of scripts.
 * Calls {@link Script#update(GameEntity)} on all scripts
 * and handles possible errors.
 * 
 * @author Nico
 */
public class ScriptSystem extends GameSystem<Script> {

	/**
	 * Creates script system
	 */
	public ScriptSystem() {
		super(Script.class);
	}

	@Override
	public void process(GameEntity entity, Script script, float deltaTime) {
		try {
			if(!script.isStarted()) {
				script.start(entity);
				script.setStarted();
			}
			script.update(entity, deltaTime);
		} catch (Exception exception) {
			Log.error("Exception in script " + script.getClass());
			exception.printStackTrace();
			script.markToRemove();
		}
	}
}
