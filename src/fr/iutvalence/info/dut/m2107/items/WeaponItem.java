package fr.iutvalence.info.dut.m2107.items;

import fr.iutvalence.info.dut.m2107.models.Sprite;

/**
 * A specification class of the item class
 * @author boureaue
 *
 */

public class WeaponItem extends Item {

	public enum WeaponType {
		SWORD,
		BOW;
	}
	
	protected int damage;
	protected int range;
	protected int useTime;
	protected int knockback;
	protected Effect effect;
	
	protected WeaponType subtype;
	
	
	public WeaponItem(int id, Sprite spr, WeaponType subtype, String name, String description, int maxStack, int value, Rarity rarity, int damage, int knockback, int range, int useTime, Effect effect) {
		super(id, spr, ItemType.WEAPON, name, description, rarity, maxStack, value);
		
		this.damage = damage;
		this.range = range;
		this.useTime = useTime;
		this.knockback = knockback;
		this.effect = effect;
		this.subtype = subtype;
	}

	public WeaponItem(WeaponItem item) {
		super(item);
		this.damage = item.damage;
		this.range = item.range;
		this.useTime = item.useTime;
		this.knockback = item.knockback;
		this.effect = item.effect;
		this.subtype = item.subtype;
	}

	public int getDamage() {return damage;}
	public int getRange() {return range;}
	public int getUseTime() {return useTime;}
	public int getKnockback() {return knockback;}
	public Effect getEffect() {return effect;}
	public WeaponType getSubtype() {return subtype;}
}
