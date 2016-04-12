package fr.iutvalence.info.dut.m2107.models;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.render.Loader;

/**
 * A Sprite that has a VBO and texture
 * @author Xenation
 *
 */
public class Sprite extends AbstractSprite {
	
	private Vector2f size;
	
	/**
	 * A Sprite with the specified VBO and texture (using IDs)
	 * @param vaoID the id of the VBO
	 * @param texID the id of the texture
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

	public Vector2f getSize() {
		return size;
	}

	public void setSize(Vector2f size) {
		this.size = size;
	}
	
}
