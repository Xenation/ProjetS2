package fr.iutvalence.info.dut.m2107.tiles;

import fr.iutvalence.info.dut.m2107.events.EventManager;
import fr.iutvalence.info.dut.m2107.events.TileVariantChangedEvent;
import fr.iutvalence.info.dut.m2107.storage.Chunk;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

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
	public int x, y;
	/**
	 * the type of the tile
	 */
	protected TileType type;
	
	/**
	 * The variant of this tile
	 */
	private TileVariant variant;
	
	/**
	 * The orientation of this tile
	 */
	protected TileOrientation orientation;
	
	/**
	 * A Tile with the specified type and coordinates
	 * @param type the type of the tile
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 */
	public Tile(TileType type, int x, int y) {
		this.type = type;
		this.variant = type.getBaseVariant();
		this.x = x;
		this.y = y;
		this.orientation = TileOrientation.RIGHT;
	}
	
	/**
	 * A Tile with the specified type, variant and coordinates
	 * @param type the type of the tile
	 * @param variant the variant of the tile
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 */
	public Tile(TileType type, TileVariant variant, int x, int y) {
		this.type = type;
		this.variant = variant;
		this.x = x;
		this.y = y;
		this.orientation = TileOrientation.RIGHT;
	}
	
	/**
	 * A Tile with the specified type, coordinates and orientation
	 * @param type the type of the tile
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 * @param orientation the orientation of the tile
	 */
	public Tile(TileType type, int x, int y, TileOrientation orientation) {
		this.type = type;
		this.variant = type.getBaseVariant();
		this.x = x;
		this.y = y;
		this.orientation = orientation;
	}
	
	/**
	 * A Tile with the specified type, variant, coordinates and orientation
	 * @param type the type of the tile
	 * @param variant the variant of the tile
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 * @param orientation the orientation of the tile
	 */
	public Tile(TileType type, TileVariant variant, int x, int y, TileOrientation orientation) {
		this.type = type;
		this.variant = variant;
		this.x = x;
		this.y = y;
		this.orientation = orientation;
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
	
	public TileVariant getVariant() {
		return variant;
	}
	
	public void setVariant(TileVariant var) {
		TileVariant old = this.variant;
		this.variant = var;
		GameWorld.chunkMap.get(Chunk.toChunkPosition(x, y)).updateVariant(this, old);
		EventManager.sendEvent(new TileVariantChangedEvent(this, old));
	}
	
	/**
	 * Only meant to be used on world loading
	 * @param var
	 */
	public void setVariantUnsafe(TileVariant var) {
		this.variant = var;
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
	 * Returns the orientation of this tile
	 * @return the orientation of this tile
	 */
	public TileOrientation getOrientation() {
		return orientation;
	}
	
	/**
	 * Sets the orientation of this tile
	 * @param orientation the new orientation of this tile
	 */
	public void setOrientation(TileOrientation orientation) {
		this.orientation = orientation;
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
	
	/**
	 * Returns the x coordinate of the tile in front
	 * @return the x coordinate of the tile in front
	 */
	public int getFrontX() {
		switch (orientation) {
		case DOWN:
			return x;
		case LEFT:
			return x-1;
		case RIGHT:
			return x+1;
		case UP:
			return x;
		default:
			return x;
		}
	}
	
	/**
	 * Returns the y coordinate of the tile in front
	 * @return the y coordinate of the tile in front
	 */
	public int getFrontY() {
		switch (orientation) {
		case DOWN:
			return y-1;
		case LEFT:
			return y;
		case RIGHT:
			return y;
		case UP:
			return y+1;
		default:
			return y;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
	
}
