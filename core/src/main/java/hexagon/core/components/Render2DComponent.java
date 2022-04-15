package hexagon.core.components;

import hexagon.utils.json.JsonObject;

public abstract class Render2DComponent extends Component implements Comparable<Render2DComponent> {

	protected byte depthLayer = 0; // TODO - Depth layer names
	protected byte orderInLayer = 0;

	@Override
	public void init(JsonObject jsonObject) {
		jsonObject.getObject("depth").ifPresent(depthJson -> {
			this.depthLayer = (byte) depthJson.getInt("layer", this.depthLayer);
			this.orderInLayer = (byte) depthJson.getInt("order", this.orderInLayer);
		});
	}

	public abstract void render(Transform transform);

	@Override
	public int compareTo(Render2DComponent that) {
		int depthDelta = this.depthLayer - that.depthLayer;
		return depthDelta == 0 ? this.orderInLayer - that.orderInLayer : depthDelta;
	}
}
