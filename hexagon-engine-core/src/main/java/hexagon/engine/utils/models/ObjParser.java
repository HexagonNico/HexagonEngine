package hexagon.engine.utils.models;

import java.util.ArrayList;

import hexagon.engine.resources.ResourceLoadingException;
import hexagon.engine.resources.Resources;

public class ObjParser {
	
	// TODO - Move somewhere else

	public static final Data parse(String filePath) {
		ArrayList<VertexData> verticesData = new ArrayList<>();
		ArrayList<VertexData> normalsData = new ArrayList<>();
		ArrayList<VertexData> textureData = new ArrayList<>();
		ArrayList<FaceData> facesData = new ArrayList<>();
		try {
			Resources.forEachLine(filePath, line -> {
				String[] currentLine = line.split(" ");
				if(line.startsWith("v ")) {
					float x = Float.parseFloat(currentLine[1]);
					float y = Float.parseFloat(currentLine[2]);
					float z = Float.parseFloat(currentLine[3]);
					verticesData.add(new VertexData(x, y, z));
				} else if(line.startsWith("vn ")) {
					float x = Float.parseFloat(currentLine[1]);
					float y = Float.parseFloat(currentLine[2]);
					float z = Float.parseFloat(currentLine[3]);
					normalsData.add(new VertexData(x, y, z));
				} else if(line.startsWith("vt ")) {
					float x = Float.parseFloat(currentLine[1]);
					float y = Float.parseFloat(currentLine[2]);
					textureData.add(new VertexData(x, y, 0.0f));
				} else if(line.startsWith("f ")) {
					facesData.add(processFace(currentLine[1].split("/")));
					facesData.add(processFace(currentLine[2].split("/")));
					facesData.add(processFace(currentLine[3].split("/")));
				}
			});
		} catch(ResourceLoadingException e) {
			e.printStackTrace();
			// TODO - Handle exception
		}
		float[] verticesValues = new float[verticesData.size() * 3];
		float[] textureValues = new float[verticesData.size() * 2];
		float[] normalsValues = new float[verticesData.size() * 3];
		ArrayList<Integer> indices = new ArrayList<>();
		for(FaceData faceVertex : facesData) {
			VertexData texture = textureData.get(faceVertex.texture);
			VertexData normal = normalsData.get(faceVertex.normal);
			textureValues[faceVertex.vertex * 2] = texture.x;
			textureValues[faceVertex.vertex * 2 + 1] = texture.y;
			normalsValues[faceVertex.vertex * 3] = normal.x;
			normalsValues[faceVertex.vertex * 3 + 1] = normal.y;
			normalsValues[faceVertex.vertex * 3 + 2] = normal.z;
			indices.add(faceVertex.vertex);
		}
		for(int i = 0; i < verticesValues.length; i += 3) {
			verticesValues[i] = verticesData.get(i / 3).x;
			verticesValues[i + 1] = verticesData.get(i / 3).y;
			verticesValues[i + 2] = verticesData.get(i / 3).z;
		}
		int[] indicesValues = indices.stream().mapToInt(i -> i).toArray();
		return new Data(verticesValues, normalsValues, textureValues, indicesValues);
	}

	private static FaceData processFace(String[] faceVertex) {
		int vertex = Integer.parseInt(faceVertex[0]) - 1;
		int texture = Integer.parseInt(faceVertex[1]) - 1;
		int normal = Integer.parseInt(faceVertex[2]) - 1;
		return new FaceData(vertex, normal, texture);
	}

	private static record VertexData(float x, float y, float z) {
	}

	private static record FaceData(int vertex, int normal, int texture) {
	}

	public static class Data {

		public final float[] vertices;
		public final float[] normals;
		public final float[] textureCoords;
		public final int[] indices;

		private Data(float[] vertices, float[] normals, float[] textureCoords, int[] indices) {
			this.vertices = vertices;
			this.normals = normals;
			this.textureCoords = textureCoords;
			this.indices = indices;
		}
	}
}
