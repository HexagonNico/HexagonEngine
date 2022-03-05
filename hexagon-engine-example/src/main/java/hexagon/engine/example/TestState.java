package hexagon.engine.example;

import hexagon.engine.ecs.systems.SpriteRenderer;
import hexagon.engine.states.EntitiesLoader;
import hexagon.engine.states.GameState;

public final class TestState extends GameState {

	//private GameEntity dragonEntity;

	public TestState() {
		super(new SpriteRenderer()/*, new ModelRenderer(), new LightSystem()*/);
	}

	@Override
	public void onStart() {
		//dragonEntity = EntitiesLoader.loadEntities(this, "/entities/dragon-orbit.json", 1);
		EntitiesLoader.loadEntities(this, "/entities/test-2D.json");
	}

	@Override
	public void onUpdate() {
		//this.dragonEntity.getComponent(Transform3D.class).ifPresent(transform -> {
		//	transform.rotate(0.0f, 0.1f, 0.0f);
		//});
	}

	@Override
	public void onExit() {

	}
}
