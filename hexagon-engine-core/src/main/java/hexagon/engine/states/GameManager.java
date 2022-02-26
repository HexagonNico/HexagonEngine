package hexagon.engine.states;

import hexagon.engine.ecs.GameEntity;

/**
 * Class that holds and processes the current {@link GameState}.
 * 
 * @author Nico
 */
public final class GameManager {
	
	/**Class should not be instantiated */
	private GameManager() {}

	/**The current game state */
	private static GameState currentState = new EmptyState();
	/**Game state used when changing state */
	private static GameState nextState = null;

	/**
	 * Changes the {@link GameState} state to a new one.
	 * 
	 * @param state The new game state
	 */
	public static void changeState(GameState state) {
		if(state != null) {
			nextState = state;
		}
	}

	/**
	 * Updates the current {@link GameState}.
	 */
	public static void update() {
		currentState.runSystems();
		currentState.onUpdate();
		if(nextState != null) {
			currentState.onExit();
			currentState = nextState;
			nextState = null;
			currentState.onStart();
		}
	}

	/**
	 * Creates a {@link GameEntity} in the current {@link GameState}.
	 * 
	 * @return The newly created game entity
	 */
	public static GameEntity createEntity() {
		return currentState.createEntity();
	}

	/**
	 * Removes a {@link GameEntity} from the current {@link GameState}.
	 * 
	 * @param entity The entity to remove
	 */
	public static void removeEntity(GameEntity entity) {
		currentState.removeEntity(entity);
	}
}
