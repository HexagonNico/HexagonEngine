package hexagon.engine.core;

import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.core.ecs.GameSystem;
import hexagon.engine.core.ecs.TestComponent;

public class TestSystem extends GameSystem {

	public TestSystem() {
		super(TestComponent.class);
	}

	@Override
	protected void beforeAll() {
		System.out.println("Before all");
	}

	@Override
	protected void process(GameEntity entity) {
		TestComponent test = entity.getComponent(TestComponent.class);
		System.out.println("Process " + test.test);
	}

	@Override
	protected void afterAll() {
		System.out.println("After all");
	}
	
}
