package fr.iutvalence.info.dut.m2107.tiles;

import fr.iutvalence.info.dut.m2107.models.TileSprite;

public enum TileType {
	Dirt(1, new TileSprite("tile/dirt")),
	Stone(2, new TileSprite("tile/stone"));
	
	private final byte id;
	private final TileSprite sprite;
	
	private TileType(int id, TileSprite spr) {
		this.id = (byte) id;
		this.sprite = spr;
	}
	
	public TileSprite getSprite() {
		return this.sprite;
	}

	public byte getId() {
		return id;
	}
	
	public static TileType getTypeById(byte id) {
		for (TileType type : values()) {
			if (type.id == id) return type;
		}
		return null;
	}
}
