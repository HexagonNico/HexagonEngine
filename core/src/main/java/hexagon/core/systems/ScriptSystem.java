package hexagon.core.systems;

import hexagon.core.base.GameEntity;
import hexagon.core.base.GameSystem;
import hexagon.core.components.Script;
import hexagon.utils.Log;

public class ScriptSystem implements GameSystem<Script> {

	@Override
	public void process(GameEntity entity, Script script) {
		try {
			if(!script.isStarted()) {
				script.start(entity);
				script.setStarted();
			}
			script.update(entity);
		} catch (Exception exception) {
			Log.error("Exception in script " + script.getClass());
			exception.printStackTrace();
			script.queueRemove();
		}
	}
	
}
