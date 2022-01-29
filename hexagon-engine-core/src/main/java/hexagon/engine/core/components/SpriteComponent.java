package hexagon.engine.core.components;

import hexagon.engine.lwjgl.texture.Texture;

public final class SpriteComponent {
	
	public Texture texture;

	public SpriteComponent(String texture) {
		this.texture = Texture.getOrLoad(texture);
	}
}
