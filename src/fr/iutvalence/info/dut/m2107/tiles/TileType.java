package fr.iutvalence.info.dut.m2107.tiles;

import java.util.HashSet;
import java.util.Set;

/**
 * Defines the available types of tiles
 * @author Xenation
 *
 */
public enum TileType {
	Dirt(1, TileVariant.Dirt),
	Stone(2, TileVariant.Stone),
	Grass(3, TileVariant.Grass),
	Log(4, TileVariant.Log),
	Leaves(5, TileVariant.Leaves),
	Fader(6, TileVariant.Fader, true, TileBehavior.FADING),
	Spikes(7, TileVariant.Spikes, false, TileBehavior.DAMAGING, TileBehavior.SUPPORTED),
	Sand(8, TileVariant.Sand, true, TileBehavior.FALLING),
	Creator(9, TileVariant.Creator, true, TileBehavior.CREATOR);
	
	static {
		// Add variants here
		
	}
	
	/**
	 * The id of the type (used for saving/loading)
	 */
	private final byte id;
	/*
	 * The base variant of this type
	 */
	private final TileVariant baseVariant;
	/**
	 * All the variants available for this type (including base)
	 */
	private final Set<TileVariant> variants = new HashSet<TileVariant>();
	/**
	 * Whether this type is solid (entities collide with it)
	 */
	private final boolean isSolid;
	/**
	 * The behaviors of this type
	 */
	private final Set<TileBehavior> behaviors = new HashSet<TileBehavior>();
	
	/**
	 * A type of tile with a normal behavior
	 * @param id the id of the type
	 * @param spr the sprite of the type
	 */
	private TileType(int id, TileVariant variant) {
		this.id = (byte) id;
		this.baseVariant = variant;
		this.variants.add(variant);
		this.isSolid = true;
		this.behaviors.add(TileBehavior.NORMAL);
	}
	
	/**
	 * A type of tile with the specified behaviors
	 * @param id the id of the type
	 * @param spr the sprite of the type
	 * @param behaviors the behaviors of the type
	 */
	private TileType(int id, TileVariant variant, boolean solid, TileBehavior... behaviors) {
		this.id = (byte) id;
		this.baseVariant = variant;
		this.variants.add(variant);
		this.isSolid = solid;
		for (TileBehavior behavior : behaviors) {
			this.behaviors.add(behavior);
		}
	}
	
	/**
	 * Returns the ID of this type
	 * @return the ID of this type
	 */
	public byte getId() {
		return id;
	}
	
	/**
	 * Returns the sprite of this type
	 * @return the sprite of this type
	 */
	public Set<TileVariant> getVariants() {
		return this.variants;
	}
	
	/**
	 * Returns the base variant for this type.
	 * @return the base variant for this type.
	 */
	public TileVariant getBaseVariant() {
		return baseVariant;
	}
	
	/**
	 * Returns whether this type is solid
	 * @return whether this type is solid
	 */
	public boolean isSolid() {
		return isSolid;
	}
	
	/**
	 * Returns the list of behaviors of this type
	 * @return the list of behaviors of this type
	 */
	public Set<TileBehavior> getBehaviors() {
		return behaviors;
	}
	
	/**
	 * Updates the specified tile using the behaviors of this type
	 * @param tile the tyle to update
	 * @return false if the tile needs to be deleted
	 */
	public boolean updateBehaviors(Tile tile) {
		for (TileBehavior behavior : behaviors) {
			if (!behavior.update(tile)) return false;
		}
		return true;
	}
	
	/**
	 * Returns the type corresponding to the specified ID
	 * @param id the id of the type
	 * @return the type corresponding to the given ID
	 */
	public static TileType getTypeById(byte id) {
		for (TileType type : values()) {
			if (type.id == id) return type;
		}
		return null;
	}

}
