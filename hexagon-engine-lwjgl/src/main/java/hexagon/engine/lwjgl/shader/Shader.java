package hexagon.engine.lwjgl.shader;

import java.util.HashMap;
import java.util.function.IntSupplier;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import hexagon.engine.lwjgl.OpenGL;
import hexagon.engine.utils.Log;
import hexagon.engine.utils.resources.ResourceLoadingException;
import hexagon.engine.utils.resources.Resources;

public final class Shader {
	
	private static final HashMap<String, Shader> vertexShaders = new HashMap<>();
	private static final HashMap<String, Shader> fragmentShaders = new HashMap<>();

	public static Shader vertex(String file) {
		Shader shader = vertexShaders.get(file);
		return shader != null ? shader : loadShader(file, OpenGL::createVertexShader, vertexShaders);
	}

	public static Shader fragment(String file) {
		Shader shader = fragmentShaders.get(file);
		return shader != null ? shader : loadShader(file, OpenGL::createFragmentShader, fragmentShaders);
	}

	public final int id;

	private Shader(int id) {
		this.id = id;
	}

	private static Shader loadShader(String filePath, IntSupplier type, HashMap<String, Shader> map) {
		try {
			String shaderCode = Resources.readAsString(filePath);
			int id = type.getAsInt();
			GL20.glShaderSource(id, shaderCode);
			GL20.glCompileShader(id);
			if(GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
				Log.error("Could not compile shader " + filePath);
				Log.info("Shader compilation info: \n" + GL20.glGetShaderInfoLog(id, 512));
				throw new RuntimeException("Could not compile shader " + filePath);
			} else {
				Shader shader = new Shader(id);
				map.put(filePath, shader);
				return shader;
			}
		} catch (ResourceLoadingException e) {
			Log.error("Could not load shader " + filePath);
			throw new RuntimeException("Could not load shader " + filePath, e);
		}
	}
}
