package com.github.eagl.tiles;

import com.github.eagl.models.TileSprite;

public enum TileType {
	Dirt(new TileSprite("tile_dirt")),
	Stone(new TileSprite("tile_stone"));
	
	private final TileSprite sprite;
	
	private TileType(TileSprite spr) {
		this.sprite = spr;
	}
	
	public TileSprite getSprite() {
		return this.sprite;
	}
}
