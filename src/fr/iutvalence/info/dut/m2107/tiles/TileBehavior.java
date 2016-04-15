package fr.iutvalence.info.dut.m2107.tiles;

import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

public enum TileBehavior {
	NORMAL,
	FADING,
	DAMAGING,
	SUPPORTED;
	
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
	
	private boolean updateNormal(Tile tile) {
		return true;
	}
	
	private boolean updateFading(Tile tile) {
		FadingTile fadtile = (FadingTile) tile;
		if (fadtile.timeRemaining <= 0) return false;
		fadtile.setTimeRemaining(fadtile.getTimeRemaining()-DisplayManager.deltaTime());
		return true;
	}
	
	private boolean updateDamaging(Tile tile) {
		return true;
	}
	
	private boolean updateSupported(Tile tile) {
		if (GameWorld.chunkMap.getTileAt(tile.x, tile.y-1) == null) return false;
		return true;
	}
	
}
