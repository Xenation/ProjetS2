package com.github.eagl.render;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * Loads the quads and textures of the game
 * @author Xenation
 *
 */
public class Loader {
	
	/**
	 * The default loader used by the Tiles
	 */
	public static final Loader TILE_LOADER = new Loader();
	
	/**
	 * The list of IDs from the VBOs that have been loaded with this loader
	 */
	private List<Integer> vbos = new ArrayList<Integer>();
	/**
	 * The list of IDs from the textures that have been loaded with this loader
	 */
	private List<Integer> textures = new ArrayList<Integer>();
	
	/**
	 * Loads a new quad in OpenGL using an array of positions and textureUVs.
	 * The order of the positions must be the same as textureUVs
	 * @param positions the array of positions of the quad
	 * @param textureUVs the array of textureUVs of the quad
	 * @return the id of the loaded VBO
	 */
	public int loadVbo(float[] positions, float[] textureUVs) {
		return createInterleavedVbo(positions, textureUVs);
	}
	
	/**
	 * Loads a texture from an outside file located in res folder and with .png extension
	 * @param fileName the name of the texture file without .png
	 * @return the id of the loaded texture
	 */
	public int loadTexture(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;
	}
	
	/**
	 * Creates a Interleaved VBO using data1 and data2.
	 * Interleaved VBO:
	 * a VBO that stores both the positions and the UVs alternatively
	 * Exemple: x, y, u, v, x, y, u, v
	 * @param data1 the first array of data (positions)
	 * @param data2 the second array of data (textureUVs)
	 * @return the id of the created VBO
	 */
	private int createInterleavedVbo(float[] data1, float[] data2) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeInterleavedDataInFloatBuffer(data1, data2);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return vboID;
	}
	
	/**
	 * Stores the data of two arrays in one buffer to make a Interleaved Buffer.
	 * currently splits the elements of data two by two (puts two from first then two from second and so on)
	 * @param data1 the first array of data (positions)
	 * @param data2 the second array of data (textureUVs)
	 * @return the FloatBuffer that contains the Interleaved data
	 */
	private FloatBuffer storeInterleavedDataInFloatBuffer(float[] data1, float[] data2) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data1.length + data2.length);
		// Stores position and textureUV (x,y,u,v) for each vertex
		for (int i = 0; i < data1.length; i += 2) {
			buffer.put(data1[i]);
			buffer.put(data1[i+1]);
			buffer.put(data2[i]);
			buffer.put(data2[i+1]);
		}
		buffer.flip();
		return buffer;
	}
	
}
