package fr.iutvalence.info.dut.m2107.tiles;

public class CreatingTile extends TimedTile {
	
	public static final float DEF_INTERVAL = 1f;
	
	protected TileType createdType;
	protected boolean isCreating;
	
	public CreatingTile(TileType type, int x, int y) {
		super(type, x, y, DEF_INTERVAL);
		this.createdType = TileType.Dirt;
		this.isCreating = false;
	}
	
	public CreatingTile(TileType type, int x, int y, TileType created) {
		super(type, x, y, DEF_INTERVAL);
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
