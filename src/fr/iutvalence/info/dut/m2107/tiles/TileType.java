package fr.iutvalence.info.dut.m2107.tiles;

import fr.iutvalence.info.dut.m2107.models.TileSprite;

public enum TileType {
	Dirt(new TileSprite("tile/dirt")),
	Stone(new TileSprite("tile/stone"));
	
	private final TileSprite sprite;
	
	private TileType(TileSprite spr) {
		this.sprite = spr;
	}
	
	public TileSprite getSprite() {
		return this.sprite;
	}
}
