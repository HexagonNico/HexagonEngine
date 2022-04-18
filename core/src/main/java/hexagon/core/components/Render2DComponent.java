package hexagon.core.components;

import hexagon.utils.json.JsonObject;

public abstract class Render2DComponent extends Component {

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

	public int sortingLayer() {
		return this.sortingLayer;
	}

	public int orderInLayer() {
		return this.orderInLayer;
	}
}
