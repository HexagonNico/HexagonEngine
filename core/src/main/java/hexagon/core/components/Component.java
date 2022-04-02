package hexagon.core.components;

import hexagon.utils.json.JsonObject;

public abstract class Component {
	
	private boolean remove = false;

	public abstract void init(JsonObject jsonObject);

	public final void queueRemove() {
		this.remove = true;
	}

	public final boolean markedForRemoval() {
		return this.remove;
	}
}
