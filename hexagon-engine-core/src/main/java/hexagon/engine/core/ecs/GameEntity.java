package hexagon.engine.core.ecs;

public final class GameEntity {
	
	private final GameManager gameManager;
	public final int id;

	protected GameEntity(GameManager gameManager, int id) {
		this.gameManager = gameManager;
		this.id = id;
	}

	public void addComponent(Object component) {
		this.gameManager.addComponent(this, component);
	}

	public <T> T getComponent(Class<T> type) {
		return this.gameManager.getComponent(this, type);
	}
}
