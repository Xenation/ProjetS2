package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;

public abstract class Weapon extends Item {
	
	protected final int damage;
	protected final int range;
	protected final float useTime;
	protected final int knockback;

	public Weapon(Vector2f pos, float rot, Sprite spr,
				int id, String name, String description, Rarity rarity, int maxStack, int value,
				int damage, int range, float useTime, int knockback) {
		super(pos, rot, spr, id, name, description, rarity, maxStack, value);
		this.damage = damage;
		this.range = range;
		this.useTime = useTime;
		this.knockback = knockback;
	}
	
	public Weapon(Sprite spr,
				int id, String name, String description, Rarity rarity, int maxStack, int value,
				int damage, int range, float useTime, int knockback) {
		super(spr, id, name, description, rarity, maxStack, value);
		this.damage = damage;
		this.range = range;
		this.useTime = useTime;
		this.knockback = knockback;
	}
	
	public Weapon(Weapon weapon) {
		super(weapon);
		this.damage = weapon.damage;
		this.range = weapon.range;
		this.useTime = weapon.useTime;
		this.knockback = weapon.knockback;
	}

	public void use(Character owner) {
		return;
	}

	public int getDamage() {return damage;}
	public int getRange() {return range;}
	public float getUseTime() {return useTime;}
	public int getKnockback() {return knockback;}
}
