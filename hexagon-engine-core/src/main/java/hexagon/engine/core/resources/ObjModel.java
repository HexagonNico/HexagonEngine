package hexagon.engine.core.resources;

import java.util.HashMap;

import hexagon.engine.lwjgl.VertexObject;
import hexagon.engine.utils.parsers.ObjParser;

/**
 * Class that represents a {@code .obj} model file.
 * Also used as a utility class to store obj models.
 * 
 * @author Nico
 */
public final class ObjModel {
	
	/**Map that stores all loaded models */
	private static final HashMap<String, ObjModel> loaded = new HashMap<>();

	/**
	 * Gets or loads an obj model.
	 * <p>
	 * 	If the model is not yet loaded, loads it and returns the model object.
	 * 	If it is already loaded, returns the same instance.
	 * </p>
	 * 
	 * @param modelFile Path to the obj file, from the resources folder starting with {@code /}.
	 * 
	 * @return The requested model.
	 */
	public static ObjModel getOrLoad(String modelFile) {
		ObjModel model = loaded.get(modelFile);
		if(model == null) {
			model = new ObjModel(ObjParser.parse(modelFile));
			loaded.put(modelFile, model);
		}
		return model;
	}

	/**Stores the model data and can be used for rendering */
	public final VertexObject vertexObject;
	/**Number of vertices (indices), needed to render the model */
	public final int vertexCount;

	/**
	 * Creates a model object.
	 * 
	 * @param data Model data.
	 */
	private ObjModel(ObjParser.Data data) {
		this.vertexObject = VertexObject.with()
			.attribute(0, data.vertices, 3)
			.attribute(1, data.textureCoords, 2)
			.attribute(2, data.normals, 3)
			.indices(data.indices)
			.create();
		this.vertexCount = data.indices.length;
	}
}
