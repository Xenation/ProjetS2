package fr.iutvalence.info.dut.m2107.tiles;

import fr.iutvalence.info.dut.m2107.events.EventManager;
import fr.iutvalence.info.dut.m2107.events.TileTouchGroundEvent;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

/**
 * Defines a behavior of a tile through the update method 
 * @author Xenation
 *
 */
public enum TileBehavior {
	NORMAL(0),
	FADING(1),
	DAMAGING(2),
	SUPPORTED(1),
	FALLING(1),
	LIQUID(2),
	CREATOR(1),
	PISTON(1),
	DEPENDANT(1);
	
	public static final BehaviorComparator COMPARATOR = new BehaviorComparator();
	
	protected final int priority;
	
	private TileBehavior(int prio) {
		this.priority = prio;
	}
	
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
		case LIQUID:
			return updateLiquid(tile);
		case CREATOR:
			return updateCreator(tile);
		case PISTON:
			return updatePiston(tile);
		case DEPENDANT:
			return updateDependant(tile);
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
		Tile support = GameWorld.chunkMap.getAdjacentTile(tile, TileOrientation.DOWN);
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
		TimedTile timed = (TimedTile) tile;
		if (timed.time < 0) {
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
			EventManager.sendEvent(new TileTouchGroundEvent(tile, support));
		} else {
			timed.time -= DisplayManager.deltaTime();
		}
		return true;
	}
	
	private boolean updateLiquid(Tile tile) {
		LiquidTile liquid = (LiquidTile) tile;
		Tile support = GameWorld.chunkMap.getBottomTile(tile);
		if (liquid.time < 0) {
			if (support == null) {
				GameWorld.chunkMap.addTile(TileBuilder.buildTile(tile.type, tile.x, tile.y-1));
				return false;
			}
			if (!support.type.isSolid() && support.type != TileType.Water) {
				GameWorld.chunkMap.removeTileAt(support.x, support.y);
				GameWorld.chunkMap.addTile(TileBuilder.buildTile(tile.type, tile.x, tile.y-1));
				return false;
			}
		} else {
			liquid.time -= DisplayManager.deltaTime();
		}
		if (liquid.spreadingTime < 0 && support != null && (support.type.isSolid() || support.type == TileType.Water)) {
			Tile left = GameWorld.chunkMap.getLeftTile(tile);
			Tile right = GameWorld.chunkMap.getRightTile(tile);
			if (left == null) {
				GameWorld.chunkMap.addTile(TileBuilder.buildTile(TileType.Water, tile.x-1, tile.y));
			} else if (!left.type.isSolid() && left.type != TileType.Water) {
				GameWorld.chunkMap.removeTile(left);
				GameWorld.chunkMap.addTile(TileBuilder.buildTile(TileType.Water, tile.x-1, tile.y));
			}
			if (right == null) {
				GameWorld.chunkMap.addTile(TileBuilder.buildTile(TileType.Water, tile.x+1, tile.y));
			} else if (!right.type.isSolid() && right.type != TileType.Water) {
				GameWorld.chunkMap.removeTile(right);
				GameWorld.chunkMap.addTile(TileBuilder.buildTile(TileType.Water, tile.x+1, tile.y));
			}
		} else {
			liquid.spreadingTime -= DisplayManager.deltaTime();
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
		if (pushing.pushinginterval < 0 && !pushing.isPushing()) {
			pushing.pushinginterval += PushingTile.DEF_INTERVAL;
			pushing.setPushing(true);
			Tile front = GameWorld.chunkMap.getTileFront(tile);
			if (front != null) {
				GameWorld.chunkMap.pushTiles(front.x, front.y, pushing.orientation);
			}
			DependantTile arm = (DependantTile) TileBuilder.buildTile(TileType.PistonArm, tile.getFrontX(), tile.getFrontY());
			arm.setDepending(pushing);
			arm.setOrientation(pushing.orientation.getOpposite());
			GameWorld.chunkMap.addTile(arm);
			pushing.setVariant(TileVariant.Piston_extended);
		} else if (pushing.pushinginterval < 0 && pushing.isPushing()) {
			pushing.pushinginterval += PushingTile.DEF_INTERVAL;
			pushing.setPushing(false);
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
	
	private boolean updateDependant(Tile tile) {
		DependantTile dependant = (DependantTile) tile;
		Tile realDep = GameWorld.chunkMap.getTileAt(dependant.depending.x, dependant.depending.y);
		if (realDep == null) return false;
		if (realDep.type != dependant.dependingType) return false;
		if (realDep.orientation != dependant.dependingOri) return false;
		return true;
	}
	
}
