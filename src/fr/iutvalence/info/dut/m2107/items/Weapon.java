package fr.iutvalence.info.dut.m2107.items;

/**
 * A specification class of the item class
 * @author boureaue
 *
 */

public class Weapon extends Item {

	private int damage;
	private int range;
	private int use_time;
	private int knockback;
	private Effect effect;
	
	
	Weapon(int id, String name, String description, int maxStack, int value, Rarity rarity, int damage, int knockback, int range, int use_time, Effect effect) {
		
		super( id, name, description, rarity, maxStack, value);
		
		this.damage = damage;
		this.range = range;
		this.use_time = use_time;
		this.knockback = knockback;
		this.effect = effect;
	}
	
}
