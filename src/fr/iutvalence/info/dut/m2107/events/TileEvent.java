package fr.iutvalence.info.dut.m2107.events;

import fr.iutvalence.info.dut.m2107.tiles.Tile;

/**
 * Defines an event relating to a tile. Isn't considered as a valid event (has no init()).
 * @author Xenation
 *
 */
public class TileEvent extends Event {
	
	/**
	 * The list of handlers (to be overridden by extending classes)
	 */
	public static final HandlerList handlers = new HandlerList();
	
	/**
	 * The tile concerned by this event
	 */
	protected Tile tile;
	
	/**
	 * A TileEvent with the given tile
	 * @param tile the tile concerned by the new Event
	 */
	public TileEvent(Tile tile) {
		this.tile = tile;
	}
	
	/**
	 * Returns the tile concerned by this event
	 * @return the tile concerned by this event
	 */
	public Tile getTile() {
		return tile;
	}
	
}
