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
	SUPPORTED,
	FALLING,
	CREATOR,
	PISTON;
	
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
		case FALLING:
			return updateFalling(tile);
		case CREATOR:
			return updateCreator(tile);
		case PISTON:
			return updatePiston(tile);
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
		Tile support = GameWorld.chunkMap.getTileAt(tile.x, tile.y-1);
		if (support == null) return false;
		if (!support.getType().isSolid()) return false;
		return true;
	}
	
	/**
	 * Updates a falling tile
	 * @param tile the tile to update
	 * @return false if the tile has no tile under it
	 */
	private boolean updateFalling(Tile tile) {
		FallingTile falling = (FallingTile) tile;
		if (falling.fallingTime < 0) {
			Tile support = GameWorld.chunkMap.getTileAt(tile.x, tile.y-1);
			if (support == null) {
				GameWorld.chunkMap.addTile(TileBuilder.buildTile(tile.type, tile.x, tile.y-1));
				return false;
			}
			if (!support.getType().isSolid()) {
				GameWorld.chunkMap.removeTileAt(support.x, support.y);
				GameWorld.chunkMap.addTile(TileBuilder.buildTile(tile.type, tile.x, tile.y-1));
				return false;
			}
		} else {
			falling.fallingTime -= DisplayManager.deltaTime();
		}
		return true;
	}
	
	/**
	 * Updates a creating tile
	 * @param tile the tile to update
	 * @return false if the tile has no tile under it
	 */
	private boolean updateCreator(Tile tile) {
		CreatingTile creating = (CreatingTile) tile;
		if (creating.creatingTime < 0) {
			creating.creatingTime = 1;
			Tile front = GameWorld.chunkMap.getTileFront(tile);
			if (front != null) {
				GameWorld.chunkMap.pushTiles(front.x, front.y, creating.getOrientation());
			}
			GameWorld.chunkMap.addTile(TileBuilder.buildTile(creating.createdType, tile.getFrontX(), tile.getFrontY()));
		} else {
			creating.creatingTime -= DisplayManager.deltaTime();
		}
		return true;
	}
	
	private boolean updatePiston(Tile tile) {
		PushingTile pushing = (PushingTile) tile;
		if (pushing.pushinginterval < 0 && !pushing.isPushing) {
			pushing.pushinginterval = PushingTile.DEF_INTERVAL;
			pushing.isPushing = true;
			Tile front = GameWorld.chunkMap.getTileFront(tile);
			if (front != null) {
				GameWorld.chunkMap.pushTiles(front.x, front.y, pushing.orientation);
			}
			GameWorld.chunkMap.addTile(TileBuilder.buildTile(TileType.PistonArm, tile.getFrontX(), tile.getFrontY()));
			pushing.setVariant(TileVariant.Piston_extended);
		} else if (pushing.pushinginterval < 0 && pushing.isPushing) {
			pushing.pushinginterval = PushingTile.DEF_INTERVAL;
			pushing.isPushing = false;
			Tile front = GameWorld.chunkMap.getTileFront(tile);
			if (front != null && front.type == TileType.PistonArm) {
				GameWorld.chunkMap.removeTile(front);
			}
			pushing.setVariant(TileVariant.Piston_retracted);
		} else {
			pushing.pushinginterval -= DisplayManager.deltaTime();
		}
		return true;
	}
	
}
