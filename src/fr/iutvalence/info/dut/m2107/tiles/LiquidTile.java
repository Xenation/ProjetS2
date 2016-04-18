package fr.iutvalence.info.dut.m2107.tiles;

public class LiquidTile extends FallingTile {
	
	public static final float DEF_SPREADINGTIME = 0.05f;
	
	protected float spreadingTime;
	
	public LiquidTile(TileType type, int x, int y) {
		super(type, x, y);
		this.spreadingTime = DEF_SPREADINGTIME;
	}
	
	
	
}
