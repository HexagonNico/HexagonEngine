package hexagon.core.base;

import java.util.Timer;
import java.util.TimerTask;

import hexagon.utils.Log;

public abstract class Script extends TimerTask {
	
	public final GameEntity entity;
	private final Timer timer;

	public Script(GameEntity entity) {
		this.entity = entity;
		this.timer = new Timer();
		this.timer.schedule(this, 0, 1000 / 60);
	}

	protected abstract void update();

	@Override
	public final void run() {
		try {
			this.update();
		} catch (Exception e) {
			Log.error("Exception in script: " + e.getMessage());
			this.timer.cancel();
		}
	}

	public final void stop() {
		this.timer.cancel();
	}
}
