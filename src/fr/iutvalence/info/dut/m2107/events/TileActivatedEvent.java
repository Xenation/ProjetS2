package fr.iutvalence.info.dut.m2107.events;

import java.lang.reflect.Method;
import java.util.Map;

import fr.iutvalence.info.dut.m2107.tiles.Tile;

public class TileActivatedEvent extends TileEvent {
	
	protected static final Map<Class<?>, Method> handlerClasses = ListenersScanner.getHandlers(TileActivatedEvent.class);
	
	public TileActivatedEvent(Tile tile, Listener list) {
		super(tile);
		this.handlers = new HandlerList();
		this.handlers.add(list);
	}
}
