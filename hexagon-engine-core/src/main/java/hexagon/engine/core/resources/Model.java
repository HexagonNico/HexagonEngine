package hexagon.engine.core.resources;

import java.util.HashMap;

import hexagon.engine.lwjgl.VertexObject;
import hexagon.engine.utils.parsers.ObjParser;

/**
 * Class that represents a 3D model file.
 * Also used as a utility class to store 3D models.
 * 
 * @author Nico
 */
public final class Model {
	
	/**Map that stores all loaded models */
	private static final HashMap<String, Model> loaded = new HashMap<>();

	/**
	 * Gets or loads a 3D model file.
	 * <p>
	 * 	If the model is not yet loaded, loads it and returns the model object.
	 * 	If it is already loaded, returns the same instance.
	 * </p>
	 * 
	 * @param modelFile Path to the model file, from the resources folder starting with {@code /}.
	 * 
	 * @return The requested model.
	 */
	public static Model getOrLoad(String modelFile) {
		Model model = loaded.get(modelFile);
		if(model == null) {
			// TODO - Detect file time from here
			model = new Model(ObjParser.parse(modelFile));
			loaded.put(modelFile, model);
		}
		return model;
	}

	/**Stores the model data and can be used for rendering */
	public final VertexObject vertexObject;
	/**Number of vertices (indices), needed to render the model */
	public final int vertexCount;

	/**
	 * Creates a model object from a {@code .obj} file.
	 * 
	 * @param data Model data.
	 */
	private Model(ObjParser.Data data) {
		this.vertexObject = VertexObject.with()
			.attribute(0, data.vertices, 3)
			.attribute(1, data.textureCoords, 2)
			.attribute(2, data.normals, 3)
			.indices(data.indices)
			.create();
		this.vertexCount = data.indices.length;
	}
}
