package hexagon.core.base;

public abstract class Script {
	
	public final GameEntity entity;

	public Script(GameEntity entity) {
		this.entity = entity;
	}

	public abstract void update();
}
