package hexagon.core.components;

import hexagon.utils.json.JsonObject;

public abstract class Render2DComponent extends Component implements Comparable<Render2DComponent> {

	protected byte sortingLayer = 0; // TODO - Depth layer names
	protected byte orderInLayer = 0;

	@Override
	public void init(JsonObject jsonObject) {
		jsonObject.getObject("sorting").ifPresent(depthJson -> {
			this.sortingLayer = (byte) depthJson.getInt("layer", this.sortingLayer);
			this.orderInLayer = (byte) depthJson.getInt("order", this.orderInLayer);
		});
	}

	public abstract void render(Transform transform);

	@Override
	public int compareTo(Render2DComponent that) {
		int depth = this.sortingLayer - that.sortingLayer;
		return depth == 0 ? this.orderInLayer - that.orderInLayer : depth;
	}
}
