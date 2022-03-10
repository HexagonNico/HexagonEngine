package hexagon.engine.ecs.components;

import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.vector.Float2;
import hexagon.engine.math.vector.Int2;
import hexagon.engine.opengl.TextureArray;
import hexagon.engine.opengl.VertexObject;
import hexagon.engine.utils.json.JsonArray;
import hexagon.engine.utils.json.JsonObject;

/**
 * Component that holds a data about a tilemap.
 * Contains a {@link VertexObject} to hold the tilemap mesh.
 * 
 * @author Nico
 */
public class TilemapComponent extends Transform2D {

	/**Tilemap mesh that contains vertices and tiles */
	private VertexObject mesh;
	/**Tileset texture array */
	private TextureArray tileset = TextureArray.ERROR;
	/**Size of the tilemap in tiles */
	private Int2 size = Int2.ZERO;

	/**
	 * Creates a tilemap component
	 * 
	 * @param entity The entity that holds this component
	 */
	public TilemapComponent(GameEntity entity) {
		super(entity);
	}

	@Override
	public void init(JsonObject jsonObject) {
		super.init(jsonObject);

		// Tilemap size
		jsonObject.getObject("size").ifPresent(sizeJson -> {
			int width = sizeJson.getInt("width", this.size.a());
			int height = sizeJson.getInt("height", this.size.b());
			this.size = new Int2(width, height);
		});

		// Tilemap origin
		JsonObject originJson = jsonObject.getObjectOrEmpty("origin");
		Float2 origin = new Float2(originJson.getFloat("x", 0.0f), originJson.getFloat("y", 0.0f));

		// Vertex data
		JsonArray tilesJson = jsonObject.getArrayOrEmpty("tiles");
		float[] vertices = new float[this.tilemapWidth() * this.tilemapHeight() * 6 * 2];
		float[] tiles = new float[this.tilemapWidth() * this.tilemapHeight() * 6];
		int vertexPointer = 0, vertexTilePointer = 0, mapTilePointer = 0;
		for(int y = 0; y < this.tilemapHeight(); y++) {
			for(int x = 0; x < this.tilemapWidth(); x++) {
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
		this.mesh = VertexObject.with()
				.attribute(0, vertices, 2)
				.attribute(1, tiles, 1)
				.create();
		
		// Get tileset
		jsonObject.getString("tileset").ifPresent(tilesetKey -> {
			this.tileset = TextureArray.getOrLoad(tilesetKey);
		});
	}

	// TODO - Set tiles (modify VBO)

	/**
	 * Used in {@link hexagon.engine.ecs.systems.TilemapRenderer} to render tilemaps.
	 * Calls {@link VertexObject#activate(Runnable)} to render the tilemap if the tilemap mesh is not null.
	 * Prevents possible errors if the object was not initialized.
	 * 
	 * @param renderer Renderer method
	 */
	public void renderMesh(Runnable renderer) {
		if(this.mesh != null) this.mesh.activate(renderer);
	}

	/**
	 * Gets the tileset ({@link TextureArray}) used by this tilemap.
	 * 
	 * @return The reference to the texture array used
	 */
	public TextureArray getTileset() {
		return this.tileset;
	}

	/**
	 * Gets the width of this tilemap in terms of tiles.
	 * 
	 * @return Number of tiles on the x axis
	 */
	public int tilemapWidth() {
		return this.size.a();
	}

	/**
	 * Gets the height of this tilemap in terms of tiles.
	 * 
	 * @return Number of tiles on the y axis
	 */
	public int tilemapHeight() {
		return this.size.b();
	}
}
