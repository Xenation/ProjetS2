package com.github.eagl.models;

/**
 * Represent a basic Sprite
 * @author Xenation
 *
 */
public abstract class AbstractSprite {
	
	/**
	 * the ID of the VBO
	 */
	protected final int vboID;
	/**
	 * the ID of the texture
	 */
	protected final int textureID;
	
	/**
	 * A Sprite with the specified VBO and texture
	 * @param vboID the ID of the VBO
	 * @param texID the ID of the Texture
	 */
	public AbstractSprite(int vboID, int texID) {
		this.vboID = vboID;
		this.textureID = texID;
	}
	
	/**
	 * Returns the ID of the VBO
	 * @return the ID of the VBO
	 */
	public int getTextureID() {
		return textureID;
	}
	
	/**
	 * Returns the ID of the Texture
	 * @return the ID of the Texture
	 */
	public int getVboID() {
		return vboID;
	}
	
}
