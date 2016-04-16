package fr.iutvalence.info.dut.m2107.tiles;

public class CreatingTile extends Tile {
	
	protected TileType createdType;
	protected boolean isCreating;
	protected float creatingTime = 1;
	
	public CreatingTile(TileType type, int x, int y) {
		super(type, x, y);
		this.createdType = TileType.Dirt;
		this.isCreating = false;
	}
	
	public CreatingTile(TileType type, int x, int y, TileType created) {
		super(type, x, y);
		this.createdType = created;
		this.isCreating = false;
	}

	public TileType getCreatedType() {
		return createdType;
	}

	public void setCreatedType(TileType createdType) {
		this.createdType = createdType;
	}
	
	public void create() {
		this.isCreating = true;
	}
	
}
