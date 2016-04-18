package fr.iutvalence.info.dut.m2107.events;

import fr.iutvalence.info.dut.m2107.tiles.Tile;

public class TileEvent extends Event {
	
	protected Tile tile;
	
	public TileEvent(Tile tile) {
		this.tile = tile;
	}
	
	public Tile getTile() {
		return tile;
	}
	
	
	public class TileRotatedEvent extends TileEvent {
		public TileRotatedEvent(Tile tile) {
			super(tile);
		}
	}
	
}
