package fr.iutvalence.info.dut.m2107.events;

import fr.iutvalence.info.dut.m2107.tiles.Tile;

/**
 * Event Triggered when a tile is activated.
 * @author Xenation
 *
 */
public class TileActivatedEvent extends TileEvent {
	
	/**
	 * A Tile Activated Event with a given tile
	 * @param tile the tile that triggered this event
	 */
	public TileActivatedEvent(Tile tile) {
		super(tile);
	}
}
