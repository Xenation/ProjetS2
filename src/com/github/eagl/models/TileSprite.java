package com.github.eagl.models;

import com.github.eagl.render.Loader;

/**
 * A Sprite with fixed size to be used with Tiles
 * @author Xenation
 *
 */
public class TileSprite extends AbstractSprite {
	
	/**
	 * The standard array of positions for a TileSprite
	 */
	public static final float[] POSITIONS = {
			0, 1,
			1, 1, 
			0, 0,
			1, 0
	};
	/**
	 * The standard array of textures for a TileSprite (to be replaced with a atlas index (non static) when atlases are implemented)
	 */
	public static final float[] TEXTUREUVS = {
			0, 0,
			1, 0,
			0, 1,
			1, 1
	};
	
	/**
	 * A TileSprite using the specified texture file
	 * @param fileName the name of the texture file
	 */
	public TileSprite(String fileName) {
		super(Loader.TILE_LOADER.loadVbo(POSITIONS, TEXTUREUVS), Loader.TILE_LOADER.loadTexture(fileName));
	}
	
}
