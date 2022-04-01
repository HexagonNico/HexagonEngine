package hexagon.core.base;

public interface GameSystem<T> {

	void process(GameEntity entity, T component);
}
