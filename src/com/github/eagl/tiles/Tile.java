package com.github.eagl.tiles;

import com.github.eagl.models.TileSprite;

public class Tile {
	
	public final int x, y;
	private TileSprite sprite;
	
	public Tile(TileSprite spr, int x, int y) {
		this.sprite = spr;
		this.x = x;
		this.y = y;
	}

	public TileSprite getSprite() {
		return sprite;
	}

	public void setSprite(TileSprite sprite) {
		this.sprite = sprite;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
