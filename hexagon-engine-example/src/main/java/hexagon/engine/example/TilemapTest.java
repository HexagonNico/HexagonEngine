package hexagon.engine.example;

import hexagon.engine.ecs.systems.TilemapRenderer;
import hexagon.engine.states.EntitiesLoader;
import hexagon.engine.states.GameState;

public final class TilemapTest extends GameState {

	public TilemapTest() {
		super(new TilemapRenderer());
	}

	@Override
	public void onStart() {
		EntitiesLoader.loadEntities(this, "/entities/test-tilemap.json");
	}

	@Override
	public void onUpdate() {

	}

	@Override
	public void onExit() {

	}
	
}
