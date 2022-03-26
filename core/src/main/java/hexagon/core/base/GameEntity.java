package hexagon.core.base;

import java.util.ArrayList;
import java.util.Optional;

public final class GameEntity {

	private ArrayList<Component> components = new ArrayList<>();
	private ArrayList<Script> scripts = new ArrayList<>();

	protected GameEntity() { }

	public void update() {
		this.scripts.forEach(Script::update);
	}

	public <T extends Component> Optional<T> getComponent(Class<T> type) {
		return this.components.stream()
				.filter(component -> component.getClass().isAssignableFrom(type))
				.findFirst().map(type::cast);
	}

	public void addComponent(Component component) {
		this.components.removeIf(old -> old.getClass().isAssignableFrom(component.getClass()));
		this.components.add(component);
	}

	public void addScript(Script script) {
		this.scripts.removeIf(old -> old.getClass().isAssignableFrom(script.getClass()));
		this.scripts.add(script);
	}
}
