package hexagon.engine.core.ecs;

/**
 * Class that represents an entity in the Entity-Component-System.
 * An entity is anything that can hold components.
 * 
 * @author Nico
 */
public final class GameEntity {
	
	/**Reference to the game manager */
	private final GameManager gameManager;
	/**Entity id */
	public final int id;

	/**
	 * Creates a game entity.
	 * <p>
	 * 	In general, this constructor should not be used.
	 * 	Entities should be created with {@link GameManager#createEntity()} instead.
	 * </p>
	 * 
	 * @param gameManager Reference to the game manager.
	 * @param id Entity id.
	 */
	protected GameEntity(GameManager gameManager, int id) {
		this.gameManager = gameManager;
		this.id = id;
	}

	/**
	 * Adds a component to this entity.
	 * 
	 * @param component Component to add.
	 */
	public void addComponent(Object component) {
		this.gameManager.addComponent(this, component);
	}

	/**
	 * Gets a component from this entity.
	 * 
	 * @param <T> Type of the component.
	 * @param type Class of the component to get.
	 * 
	 * @return The requested component or {@code null} if this entity does not have a component of that type.
	 */
	public <T> T getComponent(Class<T> type) {
		return this.gameManager.getComponent(this, type);
	}

	@Override
	public String toString() {
		return "Entity" + this.id;
	}
}
