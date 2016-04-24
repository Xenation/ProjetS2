package fr.iutvalence.info.dut.m2107.items;

import fr.iutvalence.info.dut.m2107.models.Sprite;

public class AmmunitionItem extends Item {

	public enum AmmoType{
		ARROW,
		BULLET,
	}
	
	protected int damage;
	
	protected float speed;
	
	protected int knockback;
	
	protected AmmoType subtype;
	
	public AmmunitionItem(int id, Sprite spr, AmmoType subtype, String name, String description, int damage, float speed, Rarity rarity, int maxStack, int value) {
		super(id, spr, ItemType.AMMO, name, description, rarity, maxStack, value);
		this.damage = damage;
		this.speed = speed;
		this.subtype = subtype;
	}
	
	public AmmunitionItem(AmmunitionItem item) {
		super(item);
		this.damage = item.damage;
		this.speed = item.speed;
		this.knockback = item.knockback;
		this.subtype = item.subtype;
	}

	public int getDamage() {return damage;}
	public float getSpeed() {return speed;}
	public int getKnockback() {return knockback;}
	public AmmoType getSubtype() {return subtype;}
	
}