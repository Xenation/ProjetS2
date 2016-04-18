package fr.iutvalence.info.dut.m2107.tiles;

public class DependantTile extends Tile {
	
	protected Tile depending;
	protected TileType dependingType;
	protected TileOrientation dependingOri;
	
	public DependantTile(TileType type, int x, int y) {
		super(type, x, y);
		this.depending = this;
		this.dependingType = this.type;
	}
	
	public DependantTile(TileType type, int x, int y, Tile depending) {
		super(type, x, y);
		this.depending = depending;
		this.dependingType = depending.type;
		this.dependingOri = depending.orientation;
	}

	public Tile getDepending() {
		return depending;
	}

	public void setDepending(Tile depending) {
		this.depending = depending;
		this.dependingType = depending.type;
		this.dependingOri = depending.orientation;
	}
	
}
