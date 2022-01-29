package hexagon.engine.core.ecs;

public class TestComponent {
	
	public final String test;

	public TestComponent(String test) {
		this.test = test;
	}

	@Override
	public String toString() {
		return this.test;
	}
}
