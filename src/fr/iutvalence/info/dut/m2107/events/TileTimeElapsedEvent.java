package fr.iutvalence.info.dut.m2107.events;

import fr.iutvalence.info.dut.m2107.tiles.Tile;

public class TileTimeElapsedEvent extends TileEvent {
	
	/**
	 * A Tile Activated Event with a given tile
	 * @param tile the tile that triggered this event
	 */
	public TileTimeElapsedEvent(Tile tile) {
		super(tile);
	}

}
