package hexagon.engine.core.components;

import org.json.JSONObject;

import hexagon.engine.core.resources.ObjModel;

public class ObjModelComponent {
	
	public final ObjModel model;

	public ObjModelComponent(JSONObject jsonObject) {
		// TODO - Handle missing value
		this.model = ObjModel.getOrLoad(jsonObject.getString("model"));
	}
}
