package hexagon.core.systems;

import hexagon.core.GameEntity;
import hexagon.core.components.Script;
import hexagon.utils.Log;

public class ScriptSystem extends GameSystem<Script> {

	public ScriptSystem() {
		super(Script.class);
	}

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
