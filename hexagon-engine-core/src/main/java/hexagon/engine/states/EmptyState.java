package hexagon.engine.states;

/**
 * Class that represents an empty game state.
 * Used to avoid using a null state as the first one.
 * 
 * @author Nico
 */
public final class EmptyState extends GameState {

	@Override
	public void onStart() {}

	@Override
	public void onUpdate() {}

	@Override
	public void onExit() {}
}
