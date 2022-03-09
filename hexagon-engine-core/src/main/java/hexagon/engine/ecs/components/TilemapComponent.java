package hexagon.engine.ecs.components;

import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.vector.Int2;
import hexagon.engine.opengl.TextureArray;
import hexagon.engine.opengl.VertexObject;
import hexagon.engine.utils.json.JsonArray;
import hexagon.engine.utils.json.JsonObject;

public class TilemapComponent extends Transform2D {

	// TODO - Default values (?)
	private VertexObject mesh;
	// TODO - This goes somewhere else (?)
	private int meshVertices;
	private TextureArray tileset;
	private Int2 size = Int2.ZERO;

	public TilemapComponent(GameEntity entity) {
		super(entity);
	}

	// TODO - This needs to be cleaned up

	@Override
	public void init(JsonObject jsonObject) {
		super.init(jsonObject);
		jsonObject.getObject("size").ifPresent(sizeJson -> {
			int width = sizeJson.getInt("width", this.size.a());
			int height = sizeJson.getInt("height", this.size.b());
			this.size = new Int2(width, height);
		});
		JsonArray tilesJson = jsonObject.getArrayOrEmpty("tiles");
		this.meshVertices = this.tilemapWidth() * this.tilemapHeight() * 6 * 2;
		float[] vertices = new float[this.tilemapWidth() * this.tilemapHeight() * 6 * 2];
		float[] tiles = new float[this.tilemapWidth() * this.tilemapHeight() * 6];
		int vertexPointer = 0, vertexTilePointer = 0, mapTilePointer = 0;
		// TODO - Tilemap origin (?)
		for(int y = 0; y < this.tilemapHeight(); y++) {
			for(int x = 0; x < this.tilemapWidth(); x++) {
				int tile = tilesJson.getInt(mapTilePointer++).orElse(0);
				tiles[vertexTilePointer++] = tile; // Top left
				vertices[vertexPointer++] = x;
				vertices[vertexPointer++] = y + 1;
				tiles[vertexTilePointer++] = tile; // Bottom left
				vertices[vertexPointer++] = x;
				vertices[vertexPointer++] = y;
				tiles[vertexTilePointer++] = tile; // Bottom right
				vertices[vertexPointer++] = x + 1;
				vertices[vertexPointer++] = y;
				tiles[vertexTilePointer++] = tile; // Bottom right
				vertices[vertexPointer++] = x + 1;
				vertices[vertexPointer++] = y;
				tiles[vertexTilePointer++] = tile; // Top right
				vertices[vertexPointer++] = x + 1;
				vertices[vertexPointer++] = y + 1;
				tiles[vertexTilePointer++] = tile; // Top left
				vertices[vertexPointer++] = x;
				vertices[vertexPointer++] = y + 1;
			}
		}
		this.mesh = VertexObject.with()
				.attribute(0, vertices, 2)
				.attribute(1, tiles, 1)
				.create();
		jsonObject.getString("tileset").ifPresent(tilesetKey -> {
			this.tileset = TextureArray.getOrLoad(tilesetKey);
		});
	}

	public VertexObject getTilemapMesh() {
		return this.mesh;
	}

	public int getMeshVertices() {
		return meshVertices;
	}

	public TextureArray getTileset() {
		return this.tileset;
	}

	public int tilemapWidth() {
		return this.size.a();
	}

	public int tilemapHeight() {
		return this.size.b();
	}
}
