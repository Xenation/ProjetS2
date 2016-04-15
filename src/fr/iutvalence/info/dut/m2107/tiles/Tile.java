package fr.iutvalence.info.dut.m2107.tiles;

import fr.iutvalence.info.dut.m2107.storage.Chunk;

public class Tile {
	
	public static final float TILE_SIZE = 1;
	
	public final int x, y;
	protected TileType type;
	
	public Tile(TileType type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public boolean update() {
		return type.updateBehaviors(this);
	}

	public TileType getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getRelX(Chunk chk) {
		return x-chk.getX()*Chunk.CHUNK_SIZE;
	}
	
	public int getRelY(Chunk chk) {
		return y-chk.getY()*Chunk.CHUNK_SIZE;
	}
	
}
