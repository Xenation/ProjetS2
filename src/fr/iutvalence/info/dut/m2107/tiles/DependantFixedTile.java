package fr.iutvalence.info.dut.m2107.tiles;

public class DependantFixedTile extends DependantTile {
	
	protected int fixX;
	protected int fixY;
	protected TileOrientation fixOri;
	
	public DependantFixedTile(TileType type, int x, int y) {
		super(type, x, y);
		this.fixX = x;
		this.fixY = y;
		this.fixOri = this.orientation;
	}
	
	public DependantFixedTile(TileType type, int x, int y, Tile depending) {
		super(type, x, y, depending);
		this.fixX = x;
		this.fixY = y;
		this.fixOri = this.orientation;
	}
	
	public void resetFixed() {
		this.fixX = this.x;
		this.fixY = this.y;
		this.fixOri = this.orientation;
	}
	
}
