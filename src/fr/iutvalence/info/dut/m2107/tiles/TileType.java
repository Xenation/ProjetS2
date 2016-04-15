package fr.iutvalence.info.dut.m2107.tiles;

import java.util.HashSet;
import java.util.Set;

import fr.iutvalence.info.dut.m2107.models.TileSprite;

public enum TileType {
	Dirt(1, new TileSprite("tile/dirt")),
	Stone(2, new TileSprite("tile/stone")),
	Grass(3, new TileSprite("tile/grass")),
	Log(4, new TileSprite("tile/log")),
	Leaves(5, new TileSprite("tile/leaves")),
	Fader(6, new TileSprite("tile/fader"), TileBehavior.FADING),
	Spikes(7, new TileSprite("tile/spikes"), TileBehavior.DAMAGING, TileBehavior.SUPPORTED);
	
	private final byte id;
	private final TileSprite sprite;
	private final Set<TileBehavior> behaviors = new HashSet<TileBehavior>();
	
	private TileType(int id, TileSprite spr) {
		this.id = (byte) id;
		this.sprite = spr;
		this.behaviors.add(TileBehavior.NORMAL);
	}
	
	private TileType(int id, TileSprite spr, TileBehavior... behaviors) {
		this.id = (byte) id;
		this.sprite = spr;
		for (TileBehavior behavior : behaviors) {
			this.behaviors.add(behavior);
		}
	}
	
	public byte getId() {
		return id;
	}
	
	public TileSprite getSprite() {
		return this.sprite;
	}
	
	public Set<TileBehavior> getBehaviors() {
		return behaviors;
	}
	
	public boolean updateBehaviors(Tile tile) {
		for (TileBehavior behavior : behaviors) {
			if (!behavior.update(tile)) return false;
		}
		return true;
	}
	
	public static TileType getTypeById(byte id) {
		for (TileType type : values()) {
			if (type.id == id) return type;
		}
		return null;
	}

}
