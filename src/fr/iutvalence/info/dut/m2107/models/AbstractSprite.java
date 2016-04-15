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
	
	protected void setVaoID(int vaoID) {
		this.vaoID = vaoID;
	}

	protected void setTextureID(int texID) {
		this.textureID = texID;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
}
