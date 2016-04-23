package fr.iutvalence.info.dut.m2107.events;

import java.lang.reflect.Method;
import java.util.Map;

import fr.iutvalence.info.dut.m2107.tiles.Tile;

public class TileTouchGroundEvent extends TileEvent {

	/**
	 * The handling classes and the method that handles this event
	 */
	public static Map<Class<?>, Method> handlerClasses;
	
	/**
	 * The instances that handles this event
	 */
	public static HandlerList handlers = new HandlerList();
	
	/**
	 * The tile that is underneath the concerned tile
	 */
	private final Tile groundTile;
	
	/**
	 * Initialises the handling classes
	 */
	public static void init() {
		handlerClasses = ListenersScanner.getHandlers(TileTouchGroundEvent.class);
	}
	
	/**
	 * A Tile Destroyed Event with a given tile
	 * @param tile the tile that triggered this event
	 */
	public TileTouchGroundEvent(Tile tile, Tile groundTile) {
		super(tile);
		this.groundTile = groundTile;
	}

	/**
	 * Returns the tile that is underneath the concerned tile
	 * @return the tile that is underneath the concerned tile
	 */
	public Tile getGroundTile() {
		return groundTile;
	}
}
