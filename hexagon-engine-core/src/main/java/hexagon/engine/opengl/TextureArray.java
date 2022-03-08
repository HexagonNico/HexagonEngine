package hexagon.engine.opengl;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import hexagon.engine.resources.Image;
import hexagon.engine.resources.ResourceLoadingException;
import hexagon.engine.resources.Resources;
import hexagon.engine.utils.Log;

public final class TextureArray {
	
	public final int id;

	private TextureArray(int id) {
		this.id = id;
	}
	
	public void bind() {
		// TODO - Texture unit
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}

	public static Builder ofSize(int width, int height) {
		return new Builder(width, height);
	}

	public static final class Builder {

		private final int width;
		private final int height;
		private final HashMap<String, Image> loadedImages;
		private final ArrayList<Layer> layers;

		private Builder(int width, int height) {
			this.width = width;
			this.height = height;
			this.loadedImages = new HashMap<>();
			this.layers = new ArrayList<>();
		}

		public void withLayer(String imageFile, int offsetX, int offsetY) {
			Image image = loadedImages.get(imageFile);
			if(image == null) {
				try {
					image = Resources.loadImage(imageFile);
					this.loadedImages.put(imageFile, image);
				} catch (ResourceLoadingException e) {
					Log.error("Could not load file " + imageFile);
					// TODO - Error texture
				}
			}
			this.layers.add(new Layer(offsetX, offsetY, image));
		}

		public TextureArray create() {
			int id = OpenGL.createTexture();
			ByteBuffer buffer = ByteBuffer.allocateDirect(4 * this.width * this.height * this.layers.size());
			this.layers.forEach(layer -> layer.image.toBuffer(buffer, layer.offsetX, layer.offsetY, this.width, this.height));
			GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, id);
			// glTexStorage3D(GL_TEXTURE_2D_ARRAY, mipLevelCount, GL_RGBA8, width, height, layerCount); ?
			GL12.glTexImage3D(GL30.GL_TEXTURE_2D_ARRAY, 0, GL11.GL_RGBA, this.width, this.height, this.layers.size(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer.flip());
			GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST); // TODO - Texture parameters
			GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
			return new TextureArray(id);
		}
	}

	private static record Layer(int offsetX, int offsetY, Image image) {

	}
}
