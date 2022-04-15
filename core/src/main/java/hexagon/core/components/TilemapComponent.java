package hexagon.core.components;

import hexagon.core.rendering.Camera;
import hexagon.lwjgl.opengl.ArrayTexture;
import hexagon.lwjgl.opengl.DrawCalls;
import hexagon.lwjgl.opengl.ShaderProgram;
import hexagon.lwjgl.opengl.VertexObject;
import hexagon.math.geometry.SizeInt;
import hexagon.math.vector.Float2;
import hexagon.utils.json.JsonArray;
import hexagon.utils.json.JsonObject;

public class TilemapComponent extends Render2DComponent {

	private VertexObject tilemapMesh;
	private ShaderProgram shader = ShaderProgram.getOrLoad("/shaders/tilemaps_default.json");
	private ArrayTexture tilesetTexture = ArrayTexture.ERROR;
	private SizeInt tilemapSize = new SizeInt(0, 0);

	@Override
	public void init(JsonObject jsonObject) {
		// Tilemap size
		jsonObject.getObject("size").ifPresent(sizeJson -> {
			int width = sizeJson.getInt("width", this.tilemapSize.width());
			int height = sizeJson.getInt("height", this.tilemapSize.height());
			this.tilemapSize = new SizeInt(width, height);
		});

		// Tilemap origin
		JsonObject originJson = jsonObject.getObjectOrEmpty("origin");
		Float2 origin = new Float2(originJson.getFloat("x", 0.0f), originJson.getFloat("y", 0.0f));

		// Vertex data
		JsonArray tilesJson = jsonObject.getArrayOrEmpty("tiles");
		float[] vertices = new float[this.width() * this.height() * 6 * 2];
		float[] tiles = new float[this.width() * this.height() * 6];
		int vertexPointer = 0, vertexTilePointer = 0, mapTilePointer = 0;
		for(int y = 0; y < this.height(); y++) {
			for(int x = 0; x < this.width(); x++) {
				int tile = tilesJson.getInt(mapTilePointer++).orElse(0);
				tiles[vertexTilePointer++] = tile; // Top left
				vertices[vertexPointer++] = origin.x() + x;
				vertices[vertexPointer++] = origin.y() + y + 1;
				tiles[vertexTilePointer++] = tile; // Bottom left
				vertices[vertexPointer++] = origin.x() + x;
				vertices[vertexPointer++] = origin.y() + y;
				tiles[vertexTilePointer++] = tile; // Bottom right
				vertices[vertexPointer++] = origin.x() + x + 1;
				vertices[vertexPointer++] = origin.y() + y;
				tiles[vertexTilePointer++] = tile; // Bottom right
				vertices[vertexPointer++] = origin.x() + x + 1;
				vertices[vertexPointer++] = origin.y() + y;
				tiles[vertexTilePointer++] = tile; // Top right
				vertices[vertexPointer++] = origin.x() + x + 1;
				vertices[vertexPointer++] = origin.y() + y + 1;
				tiles[vertexTilePointer++] = tile; // Top left
				vertices[vertexPointer++] = origin.x() + x;
				vertices[vertexPointer++] = origin.y() + y + 1;
			}
		}

		// Create mesh
		this.tilemapMesh = VertexObject.with()
				.attribute(0, vertices, 2)
				.attribute(1, tiles, 1)
				.create();
		
		// Get tileset
		jsonObject.getString("tileset").ifPresent(tilesetKey -> {
			this.tilesetTexture = ArrayTexture.getOrLoad(tilesetKey);
		});
	}

	@Override
	public void render(Transform transform) {
		this.tilemapMesh.bindIfNotBound();
		this.tilesetTexture.bindIfNotBound();
		this.shader.start();
		this.shader.load("projection_matrix", Camera.main().projection());
		this.shader.load("view_matrix", Camera.main().view());
		this.shader.load("transformation_matrix", transform.matrix());
		DrawCalls.drawTriangles(0, this.width() * this.height() * 6);
	}

	public VertexObject mesh() {
		return this.tilemapMesh;
	}

	public ShaderProgram shader() {
		return this.shader;
	}

	public ArrayTexture tileset() {
		return this.tilesetTexture;
	}

	public SizeInt size() {
		return this.tilemapSize;
	}

	public int width() {
		return this.size().width();
	}

	public int height() {
		return this.size().height();
	}
}
