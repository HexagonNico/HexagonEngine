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

	@Override
	public void init(JsonObject jsonObject) {
		super.init(jsonObject);
		jsonObject.getObject("size").ifPresent(sizeJson -> {
			int width = sizeJson.getInt("width", this.size.a());
			int height = sizeJson.getInt("height", this.size.b());
			this.size = new Int2(width, height);
		});
		JsonArray tilesJson = jsonObject.getArrayOrEmpty("tiles");
		int tilesCount = (this.tilemapWidth() + 1) * (this.tilemapHeight() + 1);
		float[] vertices = new float[tilesCount * 2];
		int[] tiles = new int[tilesCount];
		int[] indices = new int[this.tilemapWidth() * this.tilemapHeight() * 6];
		int indexPointer = 0;
		this.meshVertices = vertices.length;
		for(int x = 0; x < this.tilemapWidth() - 1; x++) {
			for(int y = 0; y < this.tilemapHeight() - 1; y++) {
				int tileIndex = x * this.tilemapWidth() + y;
				vertices[tileIndex * 2] = (float) x;
				vertices[tileIndex * 2 + 1] = (float) y;
				tiles[tileIndex] = tilesJson.getInt(tileIndex).orElse(0);
				indices[indexPointer++] = x * this.tilemapWidth() + y;
				indices[indexPointer++] = (x + 1) * this.tilemapWidth() + y;
				indices[indexPointer++] = (x + 1) * this.tilemapWidth() + y + 1;
				indices[indexPointer++] = x * this.tilemapWidth() + y;
				indices[indexPointer++] = (x + 1) * this.tilemapWidth() + y + 1;
				indices[indexPointer++] = (x + 1) * this.tilemapWidth() + y;
			}
		}
		this.mesh = VertexObject.with()
				.attribute(0, vertices, 2)
				.attribute(1, tiles, 1)
				.indices(indices)
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
