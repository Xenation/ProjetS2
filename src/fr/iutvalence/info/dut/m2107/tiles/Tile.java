package fr.iutvalence.info.dut.m2107.tiles;

import fr.iutvalence.info.dut.m2107.storage.Chunk;

/**
 * Defines a Tile
 * @author Xenation
 *
 */
public class Tile {
	
	/**
	 * The SIZE of a tile
	 */
	public static final float TILE_SIZE = 1;
	
	/**
	 * the x and y coordinates of the tile
	 */
	public final int x, y;
	/**
	 * the type of the tile
	 */
	protected TileType type;
	
	/**
	 * A Tile with the specified type and coordinates
	 * @param type the type of the tile
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 */
	public Tile(TileType type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Updates this tile using the behaviors of its type
	 * @return false if the tile needs to be deleted
	 */
	public boolean update() {
		return type.updateBehaviors(this);
	}

	/**
	 * Returns the type of this tile
	 * @return the type of this tile
	 */
	public TileType getType() {
		return type;
	}

	/**
	 * Returns the x coordinate of this tile
	 * @return the x coordinate of this tile
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns the y coordinate of this tile
	 * @return the y coordinate of this tile
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Returns the x coordinate of this tile relative to a specified chunk
	 * @param chk the chunk to use to get the relative position
	 * @return the x coordinate of this tile relative to the specified chunk
	 */
	public int getRelX(Chunk chk) {
		return x-chk.getX()*Chunk.CHUNK_SIZE;
	}
	
	/**
	 * Returns the y coordinate of this tile relative to a specified chunk
	 * @param chk the chunk to use to get the relative position
	 * @return the y coordinate of this tile relative to the specified chunk
	 */
	public int getRelY(Chunk chk) {
		return y-chk.getY()*Chunk.CHUNK_SIZE;
	}
	
}
