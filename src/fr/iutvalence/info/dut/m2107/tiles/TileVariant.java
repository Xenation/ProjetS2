package fr.iutvalence.info.dut.m2107.tiles;

import fr.iutvalence.info.dut.m2107.models.TileSprite;

/**
 * Defines each available variant of tiles
 * @author Xenation
 *
 */
public enum TileVariant {
	Dirt(1, new TileSprite("tile/dirt")),
	Stone(2, new TileSprite("tile/stone")),
	Grass(3, new TileSprite("tile/grass")),
	Log(4, new TileSprite("tile/log")),
	Leaves(5, new TileSprite("tile/leaves")),
	Fader(6, new TileSprite("tile/fader")),
	Spikes(7, new TileSprite("tile/spikes")),
	Sand(8, new TileSprite("tile/sand")),
	Creator(9, new TileSprite("tile/creator")),
	Piston_retracted(10, new TileSprite("tile/piston_retracted")),
	Piston_extended(11, new TileSprite("tile/piston_extended")),
	Piston_arm(12, new TileSprite("tile/piston_arm")),
	Water(13, new TileSprite("tile/water"));
	
	/**
	 * The sprite of this variant
	 */
	public final TileSprite sprite;
	
	public final byte id;
	
	/**
	 * A Variant with a given sprite
	 * @param spr the sprite of this variant
	 */
	private TileVariant(int id, TileSprite spr) {
		this.sprite = spr;
		this.id = (byte) id;
	}
	
	public static TileVariant getVariantById(byte id) {
		for (TileVariant var : values()) {
			if (var.id == id) return var;
		}
		return null;
	}
	
}
