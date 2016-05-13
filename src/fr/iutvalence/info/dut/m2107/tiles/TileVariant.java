package fr.iutvalence.info.dut.m2107.tiles;

import fr.iutvalence.info.dut.m2107.models.TileSprite;

/**
 * Defines each available variant of tiles
 * @author Xenation
 *
 */
public enum TileVariant {
	Dirt(1),
	Stone(2),
	Grass(3),
	Log(4),
	Leaves(5),
	Fader(6),
	Spikes(7),
	Sand(8),
	Creator(9),
	Piston_retracted(10),
	Piston_extended(11),
	Piston_arm(12),
	Water(13);
	
	/**
	 * The sprite of this variant
	 */
	public final TileSprite sprite;
	
	public final byte id;
	
	/**
	 * A Variant with a given sprite
	 * @param spr the sprite of this variant
	 */
	private TileVariant(int id) {
		this.sprite = new TileSprite(id-1);
		this.id = (byte) id;
	}
	
	public static TileVariant getVariantById(byte id) {
		for (TileVariant var : values()) {
			if (var.id == id) return var;
		}
		return null;
	}
	
}
