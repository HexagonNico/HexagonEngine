package hexagon.core.test;

import java.util.Optional;

import hexagon.core.base.GameEntity;
import hexagon.core.base.Script;

public class TestScript extends Script {

	private Optional<TestComponent> testComponent;

	public TestScript(GameEntity entity) {
		super(entity);
		this.testComponent = entity.getComponent(TestComponent.class);
	}

	@Override
	public void update() {
		this.testComponent.ifPresent(component -> {
			System.out.println(component);
		});
	}
	
}
