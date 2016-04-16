package fr.iutvalence.info.dut.m2107.tiles;

import fr.iutvalence.info.dut.m2107.models.TileSprite;

/**
 * Defines each available variant of tiles
 * @author Xenation
 *
 */
public enum TileVariant {
	Dirt(new TileSprite("tile/dirt")),
	Stone(new TileSprite("tile/stone")),
	Grass(new TileSprite("tile/grass")),
	Log(new TileSprite("tile/log")),
	Leaves(new TileSprite("tile/leaves")),
	Fader(new TileSprite("tile/fader")),
	Spikes(new TileSprite("tile/spikes")),
	Sand(new TileSprite("tile/sand")),
	Creator(new TileSprite("tile/creator")),
	Piston_retracted(new TileSprite("tile/piston_retracted")),
	Piston_extended(new TileSprite("tile/piston_extended")),
	Piston_arm(new TileSprite("tile/piston_arm"));
	
	/**
	 * The sprite of this variant
	 */
	public final TileSprite sprite;
	
	/**
	 * A Variant with a given sprite
	 * @param spr the sprite of this variant
	 */
	private TileVariant(TileSprite spr) {
		this.sprite = spr;
	}
	
}
