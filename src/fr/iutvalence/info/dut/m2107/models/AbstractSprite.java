package fr.iutvalence.info.dut.m2107.models;

/**
 * Represent a basic Sprite
 * @author Xenation
 *
 */
public abstract class AbstractSprite {
	
	/**
	 * the ID of the VBO
	 */
	protected int vaoID;
	/**
	 * the ID of the texture
	 */
	protected int textureID;
	
	/**
	 * the alpha to apply over the texture
	 */
	protected float alpha = 1;
	
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
	 * A Sprite with VAO and Texture IDs unspecified yet.
	 * Used by Sprite class to generate the vertices in the constructor
	 */
	public AbstractSprite() {
		this.vaoID = 0;
		this.textureID = 0;
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
	
	/**
	 * Sets a new ID for the VAO
	 * @param vaoID the ID of the new VAO
	 */
	protected void setVaoID(int vaoID) {
		this.vaoID = vaoID;
	}
	
	/**
	 * Sets a new ID for the Texture
	 * @param vaoID the ID of the new Texture
	 */
	protected void setTextureID(int texID) {
		this.textureID = texID;
	}
	
	/**
	 * Returns the alpha of this sprite
	 * @return the alpha of this sprite
	 */
	public float getAlpha() {
		return alpha;
	}
	
	/**
	 * Sets the alpha of this sprite
	 * @param alpha the new alpha
	 */
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
}
