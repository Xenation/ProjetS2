package fr.iutvalence.info.dut.m2107.tiles;

public class DamagingTile extends Tile {
	
	public static final float DEF_DAMAGE = 1;
	
	protected float damage;
	
	public DamagingTile(TileType type, int x, int y) {
		super(type, x, y);
		this.setDamage(DEF_DAMAGE);
	}
	
	public DamagingTile(TileType type, int x, int y, float dmg) {
		super(type, x, y);
		this.setDamage(dmg);
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}
	
}
