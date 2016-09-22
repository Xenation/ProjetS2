package fr.iutvalence.info.dut.m2107.events;

import fr.iutvalence.info.dut.m2107.tiles.Tile;

public class TileTouchGroundEvent extends TileEvent {
	
	/**
	 * The tile that is underneath the concerned tile
	 */
	private final Tile groundTile;
	
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
