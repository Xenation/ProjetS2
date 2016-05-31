package fr.iutvalence.info.dut.m2107.tiles;

/**
 * Defines a Damaging Tile
 * damages living entities that touch it
 * @author Xenation
 *
 */
public class DamagingSupportedTile extends DependantTile {
	
	/**
	 * The default damage
	 */
	public static final int DEF_DAMAGE = 1;
	
	public static final int DEF_KNOCKBACK = 1;
	
	/**
	 * The damage dealt to the living entities that touches this block
	 */
	protected int damage;
	
	protected int knockback;
	
	/**
	 * A DamagingTile with the given type and coordinates
	 * @param type the type of the tile
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public DamagingSupportedTile(TileType type, int x, int y) {
		super(type, x, y);
		this.setDamage(DEF_DAMAGE);
		this.setKnockback(DEF_KNOCKBACK);
	}
	
	/**
	 * A DamagingTile with the given type, coordinate and damage
	 * @param type the type of the tile
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param dmg the damage
	 */
	public DamagingSupportedTile(TileType type, int x, int y, int dmg, int knock) {
		super(type, x, y);
		this.setDamage(dmg);
		this.setKnockback(knock);
	}
	
	/**
	 * Returns the damage of this tile
	 * @return the damage of this tile
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * Sets the damage of this tile
	 * @param damage the new damage of this tile
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getKnockback() {
		return knockback;
	}
	
	public void setKnockback(int knockback) {
		this.knockback = knockback;
	}
}
