package fr.iutvalence.info.dut.m2107.events;

import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.tiles.TileVariant;

public class TileVariantChangedEvent extends TileEvent {
	
	/**
	 * The old variant of the concerned tile
	 */
	private final TileVariant oldVariant;
	
	/**
	 * A Tile Variant Changed Event with a given tile
	 * @param tile the tile that triggered this event
	 */
	public TileVariantChangedEvent(Tile tile, TileVariant oldvariant) {
		super(tile);
		this.oldVariant = oldvariant;
	}
	
	/**
	 * Returns the old variant of the concerned tile
	 * @return the old variant of the concerned tile
	 */
	public TileVariant getOldVariant() {
		return oldVariant;
	}
	
}
