package fr.iutvalence.info.dut.m2107.tiles;

public class FallingTile extends Tile {
	
	public static final float DEF_FALLINGTIME = 0.025f;
	
	protected float fallingTime;
	
	public FallingTile(TileType type, int x, int y) {
		super(type, x, y);
		this.fallingTime = DEF_FALLINGTIME;
	}
	
	public FallingTile(TileType type, int x, int y, float fallingtime) {
		super(type, x, y);
		this.fallingTime = fallingtime;
	}

	public float getFallingTime() {
		return fallingTime;
	}

	public void setFallingTime(float fallingTime) {
		this.fallingTime = fallingTime;
	}
	
}
