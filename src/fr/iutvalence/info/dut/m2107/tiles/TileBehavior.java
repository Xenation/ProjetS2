package fr.iutvalence.info.dut.m2107.tiles;

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
	DEPENDANT(1),
	FIXEDDEPENDANT(1);
	
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
			return updateNormal(tile);
		case DAMAGING:
			return updateNormal(tile);
		case SUPPORTED:
			return updateNormal(tile);
		case FALLING:
			return updateNormal(tile);
		case LIQUID:
			return updateNormal(tile);
		case CREATOR:
			return updateCreator(tile);
		case PISTON:
			return updatePiston(tile);
		case DEPENDANT:
			return updateDependant(tile);
		case FIXEDDEPENDANT:
			return updateFixedDependant(tile);
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
	 * Updates a creating tile
	 * @param tile the tile to update
	 * @return false if the tile has no tile under it
	 */
	private boolean updateCreator(Tile tile) {
		CreatingTile creating = (CreatingTile) tile;
		creating.time += CreatingTile.DEF_INTERVAL;
		Tile front = GameWorld.chunkMap.getTileFront(tile);
		if (front != null) {
			GameWorld.chunkMap.pushTiles(front.x, front.y, creating.getOrientation());
		}
		GameWorld.chunkMap.addTile(TileBuilder.buildTile(creating.createdType, tile.getFrontX(), tile.getFrontY()));
		return true;
	}
	
	private boolean updatePiston(Tile tile) {
		PushingTile pushing = (PushingTile) tile;
		pushing.time += PushingTile.DEF_INTERVAL;
		if (!pushing.isPushing()) {
			pushing.setPushing(true);
			Tile front = GameWorld.chunkMap.getTileFront(tile);
			if (front != null) {
				GameWorld.chunkMap.pushTiles(front.x, front.y, pushing.orientation);
			}
			DependantFixedTile arm = (DependantFixedTile) TileBuilder.buildTile(TileType.PistonArm, tile.getFrontX(), tile.getFrontY());
			arm.setDepending(pushing);
			arm.setOrientation(pushing.orientation.getOpposite());
			arm.resetFixed();
			GameWorld.chunkMap.addTile(arm);
			pushing.setVariant(TileVariant.Piston_extended);
		} else {
			pushing.setPushing(false);
			Tile front = GameWorld.chunkMap.getTileFront(tile);
			if (front != null && front.type == TileType.PistonArm) {
				GameWorld.chunkMap.removeTile(front);
			}
			pushing.setVariant(TileVariant.Piston_retracted);
		}
		return true;
	}
	
	private boolean updateDependant(Tile tile) {
		DependantTile dependant = (DependantTile) tile;
		if (dependant.depending == null) {
			return false;
		}
		Tile realDep = GameWorld.chunkMap.getTileAt(dependant.depending.x, dependant.depending.y);
		if (realDep == null) return false;
		if (realDep.type != dependant.dependingType) return false;
		if (realDep.orientation != dependant.dependingOri) return false;
		return true;
	}
	
	private boolean updateFixedDependant(Tile tile) {
		if (!updateDependant(tile)) return false;
		DependantFixedTile dependant = (DependantFixedTile) tile;
		if (dependant.fixX != dependant.x) return false;
		if (dependant.fixY != dependant.y) return false;
		if (dependant.fixOri != dependant.orientation) return false;
		return true;
	}
	
}
