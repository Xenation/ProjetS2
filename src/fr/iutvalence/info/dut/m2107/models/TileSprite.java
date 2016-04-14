package fr.iutvalence.info.dut.m2107.models;

import fr.iutvalence.info.dut.m2107.render.Loader;
import fr.iutvalence.info.dut.m2107.tiles.Tile;

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
			0, Tile.TILE_SIZE,
			Tile.TILE_SIZE, Tile.TILE_SIZE, 
			0, 0,
			Tile.TILE_SIZE, 0
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
		super(Loader.TILE_LOADER.loadtoVao(POSITIONS, TEXTUREUVS), Loader.TILE_LOADER.loadTexture(fileName));
	}
	
}
