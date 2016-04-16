package fr.iutvalence.info.dut.m2107.tiles;

import java.util.HashSet;
import java.util.Set;

import fr.iutvalence.info.dut.m2107.models.TileSprite;

/**
 * Defines the available types of tiles
 * @author Xenation
 *
 */
public enum TileType {
	Dirt(1, new TileSprite("tile/dirt")),
	Stone(2, new TileSprite("tile/stone")),
	Grass(3, new TileSprite("tile/grass")),
	Log(4, new TileSprite("tile/log")),
	Leaves(5, new TileSprite("tile/leaves")),
	Fader(6, new TileSprite("tile/fader"), true, TileBehavior.FADING),
	Spikes(7, new TileSprite("tile/spikes"), false, TileBehavior.DAMAGING, TileBehavior.SUPPORTED);
	
	/**
	 * The id of the type (used for saving/loading)
	 */
	private final byte id;
	/*
	 * The sprite of this type
	 */
	private final TileSprite sprite;
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
	private TileType(int id, TileSprite spr) {
		this.id = (byte) id;
		this.sprite = spr;
		this.isSolid = true;
		this.behaviors.add(TileBehavior.NORMAL);
	}
	
	/**
	 * A type of tile with the specified behaviors
	 * @param id the id of the type
	 * @param spr the sprite of the type
	 * @param behaviors the behaviors of the type
	 */
	private TileType(int id, TileSprite spr, boolean solid, TileBehavior... behaviors) {
		this.id = (byte) id;
		this.sprite = spr;
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
	public TileSprite getSprite() {
		return this.sprite;
	}
	
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
