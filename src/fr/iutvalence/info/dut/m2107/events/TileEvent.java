package fr.iutvalence.info.dut.m2107.events;

import fr.iutvalence.info.dut.m2107.tiles.Tile;

public class TileEvent extends Event {
	
	public static final HandlerList handlers = new HandlerList();
	
	protected Tile tile;
	
	public TileEvent(Tile tile) {
		this.tile = tile;
	}
	
	public Tile getTile() {
		return tile;
	}
	
}
