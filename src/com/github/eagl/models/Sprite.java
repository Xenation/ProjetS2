package com.github.eagl.models;

/**
 * A Sprite that has a VBO and texture
 * @author Xenation
 *
 */
public class Sprite extends AbstractSprite {
	
	/**
	 * A Sprite with the specified VBO and texture (using IDs)
	 * @param vboID the id of the VBO
	 * @param texID the id of the texture
	 */
	public Sprite(int vboID, int texID) {
		super(vboID, texID);
	}
	
}
