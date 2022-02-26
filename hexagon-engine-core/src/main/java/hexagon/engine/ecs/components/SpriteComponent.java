package hexagon.engine.ecs.components;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.color.Color;
import hexagon.engine.math.vector.Float2;
import hexagon.engine.opengl.Texture;
import hexagon.engine.utils.json.JsonObject;

/**
 * Component used by sprite renderer.
 * 
 * @author Nico
 */
public class SpriteComponent extends Component {

	/**Reference to the texture */
	public Texture texture = Texture.ERROR;
	/**Coordinates of top left corner of the sprite in the texture */
	public Float2 uv = Float2.ZERO;
	/**Size of the sprite relative to the texture */
	public Float2 size = Float2.ONE;
	/**Color tint of the sprite */
	public Color color = new Color(1.0f, 1.0f, 1.0f);

	public SpriteComponent(GameEntity entity) {
		super(entity);
	}

	@Override
	protected void init(JsonObject jsonObject) {
		jsonObject.getString("texture").ifPresentOrElse(textureKey -> {
			this.texture = Texture.getOrLoad(textureKey);
		}, () -> {
			this.texture = Texture.ERROR;
		});
		JsonObject uvJson = jsonObject.getObject("uv").orElse(JsonObject.empty());
		JsonObject sizeJson = jsonObject.getObject("size").orElse(JsonObject.empty());
		this.uv = new Float2(uvJson.getFloat("x").orElse(0.0f), uvJson.getFloat("y").orElse(0.0f));
		this.size = new Float2(sizeJson.getFloat("x").orElse(1.0f), sizeJson.getFloat("y").orElse(1.0f));
		jsonObject.getObject("color").ifPresentOrElse(colorJson -> {
			float r = colorJson.getFloat("r").orElse(0.0f);
			float g = colorJson.getFloat("g").orElse(0.0f);
			float b = colorJson.getFloat("b").orElse(0.0f);
			float a = colorJson.getFloat("a").orElse(1.0f);
			this.color = new Color(r, g, b, a);
		}, () -> {
			this.color = new Color(1.0f, 1.0f, 1.0f);
		});
	}
}
