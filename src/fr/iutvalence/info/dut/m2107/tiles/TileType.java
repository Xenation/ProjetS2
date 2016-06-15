package fr.iutvalence.info.dut.m2107.tiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Defines the available types of tiles
 * @author Xenation
 *
 */
public enum TileType {
	Dirt(1, TileVariant.Dirt),
	Stone(2, TileVariant.Stone, TileVariant.Stone_bricks, TileVariant.Stone_bricks_corner),
	Grass(3, TileVariant.Grass, TileVariant.Grass_inner, TileVariant.Grass_outer),
	Log(4, TileVariant.Log),
	Leaves(5, TileVariant.Leaves, TileVariant.Leaves_corner),
	Fader(6, TileVariant.Fader, true, TileBehavior.FADING),
	Spikes(7, TileVariant.Spikes, false, TileBehavior.DAMAGING),
	Sand(8, TileVariant.Sand, TileVariant.Sand_corner),
	Creator(9, TileVariant.Creator, true, TileBehavior.CREATOR),
	Piston(10, TileVariant.Piston_retracted, true, TileBehavior.PISTON),
	PistonArm(11, TileVariant.Piston_arm, true, TileBehavior.FIXEDDEPENDANT),
	Water(12, TileVariant.Water, false, TileBehavior.LIQUID),
	Planks(13, TileVariant.Planks),
	Torch(14, TileVariant.Torch, false),
	Cobweb(15, TileVariant.Cobweb, false , TileBehavior.LIQUID);
	
	static {
		//// Add variants here
		// Piston
		Piston.variants.add(TileVariant.Piston_retracted);
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
	private final List<TileVariant> variants = new ArrayList<TileVariant>();
	/**
	 * Whether this type is solid (entities collide with it)
	 */
	private final boolean isSolid;
	/**
	 * The behaviors of this type
	 */
	private final SortedSet<TileBehavior> behaviors = new TreeSet<TileBehavior>(TileBehavior.COMPARATOR);
	
	/**
	 * A type of tile with a normal behavior
	 * @param id the id of the type
	 * @param variants the available variants of the type
	 */
	private TileType(int id, TileVariant... variants) {
		this.id = (byte) id;
		if (variants.length == 0) throw new IllegalArgumentException("One variant at least must be given");
		this.baseVariant = variants[0];
		for (TileVariant var : variants) {
			this.variants.add(var);
		}
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
	public List<TileVariant> getVariants() {
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
	 * Returns the next possible variant of this type starting from the given variant.<br>Returns the given variant if it doesn't belong to this type.
	 * @param var the current variant
	 * @return the variant right after the current variant
	 */
	public TileVariant getNext(TileVariant var) {
		if (!variants.contains(var)) return var;
		if (variants.indexOf(var) == variants.size()-1) return variants.get(0);
		return variants.get(variants.indexOf(var)+1);
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
	
	public boolean hasBehavior(TileBehavior b) {
		for (TileBehavior behavior : behaviors)
			if (behavior == b) return true;
		return false;
	}

}
