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
	protected final int vaoID;
	/**
	 * the ID of the texture
	 */
	protected final int textureID;
	
	/**
	 * A Sprite with the specified VBO and texture
	 * @param vaoID the ID of the VBO
	 * @param texID the ID of the Texture
	 */
	public AbstractSprite(int vaoID, int texID) {
		this.vaoID = vaoID;
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
	public int getVaoID() {
		return vaoID;
	}
	
}
