package fr.iutvalence.info.dut.m2107.tiles;

public class FadingTile extends Tile {
	
	public static final float DEF_TIME = 5;
	
	protected float timeRemaining;
	
	public FadingTile(TileType type, int x, int y) {
		super(type, x, y);
		this.timeRemaining = DEF_TIME;
	}
	
	public FadingTile(TileType type, int x, int y, float time) {
		super(type, x, y);
		this.timeRemaining = time;
	}

	public float getTimeRemaining() {
		return timeRemaining;
	}

	public void setTimeRemaining(float timeRemaining) {
		this.timeRemaining = timeRemaining;
	}
	
}
