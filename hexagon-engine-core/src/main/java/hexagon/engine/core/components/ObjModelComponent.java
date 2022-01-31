package hexagon.engine.core.components;

import org.json.JSONObject;

import hexagon.engine.core.resources.ObjModel;
import hexagon.engine.lwjgl.texture.Texture;

public class ObjModelComponent {
	
	public final ObjModel model;
	public final Texture texture;

	public ObjModelComponent(JSONObject jsonObject) {
		// TODO - Handle missing value
		this.model = ObjModel.getOrLoad(jsonObject.getString("model"));
		this.texture = Texture.getOrLoad(jsonObject.getString("texture"));
	}
}
