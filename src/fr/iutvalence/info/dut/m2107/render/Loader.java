package fr.iutvalence.info.dut.m2107.render;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
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
	 * The default loader used by the sprites
	 */
	public static final Loader SPRITE_LOADER = new Loader();
	
	/**
	 * The default loader used by the texts
	 */
	public static final Loader TEXT_LOADER = new Loader();
	
	/**
	 * The default loader used by the GUI
	 */
	public static final Loader GUI_LOADER = new Loader();
	
	/**
	 * The IDs of every loaded VAO and the linked VBO
	 */
	private Map<Integer, Integer> vaoMap = new HashMap<Integer, Integer>();
	/**
	 * The list of IDs from the textures that have been loaded with this loader
	 */
	private List<Integer> textures = new ArrayList<Integer>();
	
	/**
	 * The ID of the vao currently created
	 */
	private int currentVao;
	
	/**
	 * Loads the specified positions and textureUVs inside a new VAO
	 * positions and textureUVs are to be in the same order
	 * @param positions the array of positions
	 * @param textureUVs the array of textureUVs
	 * @return the ID of the created VAO
	 */
	public int loadtoVao(float[] positions, float[] textureUVs) {
		int vaoID = createVAO();
		storeInterleavedDataInAttributeList(0, 1, 2, positions, textureUVs);
		unbindVAO();
		return vaoID;
	}
	
	public void updateVao(int vaoID, float[] positions, float[] textureUVs) {
		GL30.glBindVertexArray(vaoID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vaoMap.get(vaoID));
		
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, storeInterleavedDataInFloatBuffer(positions, textureUVs));
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * Returns a String representing the state of the loader
	 * @return a String representing the state of the loader
	 */
	public String debugValues() {
		return "VAOs:"+vaoMap.size()+"  VBOs:"+vaoMap.size()+"  Tex:"+textures.size();
	}
	
	/**
	 * unloads all the VAOs, VBOs and Textures that loaded this Loader
	 */
	public void unloadAll() {
		for (int texture : textures) {
			GL11.glDeleteTextures(texture);
		}
		textures.clear();
		for (int vao : vaoMap.keySet()) {
			GL15.glDeleteBuffers(vaoMap.get(vao));
			GL30.glDeleteVertexArrays(vao);
		}
		vaoMap.clear();
	}
	
	/**
	 * Unloads a VAO using the given VAO ID
	 * @param vao the ID of the VAO to unload
	 */
	public void unloadVAO(int vao) {
		if (vaoMap.containsKey(vao)) {
			GL15.glDeleteBuffers(vaoMap.get(vao));
			GL30.glDeleteVertexArrays(vao);
			vaoMap.remove(vao);
		}
	}
	
	/**
	 * Unloads a Texture using the given texture ID
	 * @param texID the ID of the texture to unload
	 */
	public void unloadTexture(int texID) {
		if (textures.contains(new Integer(texID))) {
			GL11.glDeleteTextures(texID);
			textures.remove(new Integer(texID));
		}
	}
	
	/**
	 * Stores the two data arrays in a single Interleaved VBO and links it into two different attributes
	 * @param attrib1 the index of the first attribute (data1)
	 * @param attrib2 the index of the second attribute (data2)
	 * @param coordinateSize the size of coordinates (here 2: x,y)
	 * @param data1 the first array of data
	 * @param data2 the second array of data
	 */
	private void storeInterleavedDataInAttributeList(int attrib1, int attrib2, int coordinateSize, float[] data1, float[] data2) {
		int vboID = GL15.glGenBuffers();
		vaoMap.put(currentVao, vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeInterleavedDataInFloatBuffer(data1, data2);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attrib1, coordinateSize, GL11.GL_FLOAT, false, 16, 0);
		GL20.glVertexAttribPointer(attrib2, coordinateSize, GL11.GL_FLOAT, false, 16, 8);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * Creates a new VAO
	 * @return the ID of the newly created VAO
	 */
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		currentVao = vaoID;
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	/**
	 * unbinds the currently bound VAO
	 */
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
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
	
	public Texture loadAtlas(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		textures.add(texture.getTextureID());
		return texture;
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
