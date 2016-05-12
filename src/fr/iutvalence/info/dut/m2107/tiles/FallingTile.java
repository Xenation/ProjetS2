package fr.iutvalence.info.dut.m2107.tiles;

public class FallingTile extends TimedTile {
	
	public static final float DEF_FALLINGTIME = 0.025f;
	
	public FallingTile(TileType type, int x, int y) {
		super(type, x, y, DEF_FALLINGTIME);
	}
	
	public FallingTile(TileType type, int x, int y, float fallingtime) {
		super(type, x, y, fallingtime);
	}
	
}
