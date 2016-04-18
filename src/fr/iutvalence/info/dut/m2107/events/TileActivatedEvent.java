package fr.iutvalence.info.dut.m2107.events;

import java.lang.reflect.Method;
import java.util.Map;

import fr.iutvalence.info.dut.m2107.tiles.Tile;

public class TileActivatedEvent extends TileEvent {
	
	protected static Map<Class<?>, Method> handlerClasses;
	
	public static HandlerList handlers = new HandlerList();
	
	public static void init() {
		handlerClasses = ListenersScanner.getHandlers(TileActivatedEvent.class);
	}
	
	public TileActivatedEvent(Tile tile) {
		super(tile);
	}
}
