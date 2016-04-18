package fr.iutvalence.info.dut.m2107.events;

import java.lang.reflect.Method;
import java.util.Map;

import fr.iutvalence.info.dut.m2107.tiles.Tile;

/**
 * Event Triggered when a tile is activated.
 * @author Xenation
 *
 */
public class TileActivatedEvent extends TileEvent {
	
	/**
	 * The handling classes and the method that handles this event
	 */
	public static Map<Class<?>, Method> handlerClasses;
	
	/**
	 * The instances that handles this event
	 */
	public static HandlerList handlers = new HandlerList();
	
	/**
	 * Initialises the handling classes
	 */
	public static void init() {
		handlerClasses = ListenersScanner.getHandlers(TileActivatedEvent.class);
	}
	
	/**
	 * A Tile Activated Event with a given tile
	 * @param tile the tile that triggered this event
	 */
	public TileActivatedEvent(Tile tile) {
		super(tile);
	}
}
