package fr.iutvalence.info.dut.m2107.models;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.render.Loader;

/**
 * A Sprite that has a VBO and texture
 * @author Xenation
 *
 */
public class Sprite extends AbstractSprite {
	
	/**
	 * The size of this sprite
	 */
	private Vector2f size;
	
	/**
	 * A Sprite with the given texture file and size
	 * @param textureFile the path to the texture file
	 * @param size the size of the sprite
	 */
	public Sprite(String textureFile, Vector2f size) {
		super();
		this.size = size;
		float pos[] = {-this.size.x/2, this.size.y/2,
				this.size.x/2, this.size.y/2,
				-this.size.x/2, -this.size.y/2,
				this.size.x/2, -this.size.y/2};
		float tex[] = {0,0, 1,0, 0,1, 1,1};
		
		super.setVaoID(Loader.SPRITE_LOADER.loadtoVao(pos, tex));
		super.setTextureID(Loader.SPRITE_LOADER.loadTexture(textureFile));
	}
	
	/**
	 * Returns the size of this sprite
	 * @return the size of this sprite
	 */
	public Vector2f getSize() {
		return size;
	}
	
}
