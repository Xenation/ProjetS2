package fr.iutvalence.info.dut.m2107.tiles;

import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

/**
 * Defines a behavior of a tile through the update method 
 * @author Xenation
 *
 */
public enum TileBehavior {
	NORMAL,
	FADING,
	DAMAGING,
	SUPPORTED;
	
	/**
	 * Updates the specified tile using this behavior
	 * @param tile the tile to update
	 * @return false if the tile needs to be deleted
	 */
	public boolean update(Tile tile) {
		switch (this) {
		case NORMAL:
			return updateNormal(tile);
		case FADING:
			return updateFading(tile);
		case DAMAGING:
			return updateDamaging(tile);
		case SUPPORTED:
			return updateSupported(tile);
		default:
			return updateNormal(tile);
		}
	}
	
	/**
	 * Updates a normal tile (always returns true)
	 * @param tile the tile to update
	 * @return true (a normal tile is never deleted by itself)
	 */
	private boolean updateNormal(Tile tile) {
		return true;
	}
	
	/**
	 * Updates a fading tile
	 * @param tile the tile to update
	 * @return false if the tile has elapsed its lifetime
	 */
	private boolean updateFading(Tile tile) {
		FadingTile fadtile = (FadingTile) tile;
		if (fadtile.timeRemaining <= 0) return false;
		fadtile.setTimeRemaining(fadtile.getTimeRemaining()-DisplayManager.deltaTime());
		return true;
	}
	
	/**
	 * Updates a damaging tile
	 * @param tile the tile to update
	 * @return true (not fully implemented yet)
	 */
	private boolean updateDamaging(Tile tile) {
		return true;
	}
	
	/**
	 * Updates a supported tile
	 * @param tile the tile to update
	 * @return false if the tile has no tile under it
	 */
	private boolean updateSupported(Tile tile) {
		if (GameWorld.chunkMap.getTileAt(tile.x, tile.y-1) == null) return false;
		return true;
	}
	
}
