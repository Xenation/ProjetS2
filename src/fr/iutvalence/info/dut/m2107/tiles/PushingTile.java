package fr.iutvalence.info.dut.m2107.tiles;

public class PushingTile extends Tile {
	
	public static float DEF_INTERVAL = .5f;
	
	protected boolean isPushing;
	protected float pushinginterval;
	
	public PushingTile(TileType type, int x, int y) {
		super(type, x, y);
		this.isPushing = false;
		this.pushinginterval = DEF_INTERVAL;
	}

}
