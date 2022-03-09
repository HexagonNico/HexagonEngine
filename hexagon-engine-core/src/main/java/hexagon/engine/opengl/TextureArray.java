package hexagon.engine.opengl;

import java.nio.ByteBuffer;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import hexagon.engine.resources.Image;
import hexagon.engine.resources.ResourceLoadingException;
import hexagon.engine.resources.Resources;
import hexagon.engine.utils.Log;
import hexagon.engine.utils.json.JsonArray;
import hexagon.engine.utils.json.JsonObject;

public final class TextureArray {

	private static final HashMap<String, TextureArray> textures = new HashMap<>();

	public static TextureArray getOrLoad(String textureFile) {
		TextureArray texture = textures.get(textureFile);
		return texture != null ? texture : loadTextureArray(textureFile);
	}

	public final int id;

	private TextureArray(int id) {
		this.id = id;
	}
	
	public void bind() {
		// TODO - Texture unit
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, this.id);
	}

	private static TextureArray loadTextureArray(String file) {
		try {
			// Load json file
			JsonObject textureJson = JsonObject.fromFile(file);
			JsonObject sizeJson = textureJson.getObjectOrEmpty("size");
			JsonArray layersArray = textureJson.getArrayOrEmpty("layers");
			
			// All layers have the same size
			int width = sizeJson.getInt("width", 16);
			int height = sizeJson.getInt("height", 16);
			int layers = layersArray.length();

			// Allocate buffer
			ByteBuffer buffer = ByteBuffer.allocateDirect(4 * width * height * layers);
			HashMap<String, Image> loadedImages = new HashMap<>();

			// Load layers
			layersArray.forEachObject(layerJson -> {
				layerJson.getString("image").ifPresent(imagePath -> {
					Image image = loadedImages.get(imagePath);
					if(image == null) {
						try {
							image = Resources.loadImage(imagePath);
							loadedImages.put(imagePath, image);
						} catch (ResourceLoadingException e) {
							// TODO - Error layer
						}
					}
					int x = layerJson.getInt("x", 0);
					int y = layerJson.getInt("y", 0);
					image.toBuffer(buffer, x, y, width, height);
				});
			});
			buffer.flip();

			// Create texture
			int id = OpenGL.createTexture();
			GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, id);
			// glTexStorage3D(GL_TEXTURE_2D_ARRAY, mipLevelCount, GL_RGBA8, width, height, layerCount); ?
			GL12.glTexImage3D(GL30.GL_TEXTURE_2D_ARRAY, 0, GL11.GL_RGBA, width, height, layers, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
			GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST); // TODO - Texture parameters
			GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
			return new TextureArray(id);
		} catch (ResourceLoadingException e) {
			Log.error("Error loading texture array " + file);
			// TODO - Error texture array
			return null;
		}
	}
}
