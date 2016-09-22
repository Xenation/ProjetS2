package fr.iutvalence.info.dut.m2107.events;

import fr.iutvalence.info.dut.m2107.tiles.Tile;

/**
 * Event Triggered when a tile is destroyed.
 * @author Xenation
 *
 */
public class TileDestroyedEvent extends TileEvent {
	
	/**
	 * A Tile Destroyed Event with a given tile
	 * @param tile the tile that triggered this event
	 */
	public TileDestroyedEvent(Tile tile) {
		super(tile);
	}
}
