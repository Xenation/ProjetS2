package com.github.eagl.items;

/**
 * A specification class of the item class
 * @author boureaue
 *
 */

public class Weapon extends Item {

	
	private int damage;
	private int range;
	private int knockback;
	
	
	Weapon(int _id, String _name, String _description, int _maxStack, int _value, Rarity _rarity, int _damage, int _knockback, int _range) {
		
		super( _id, _name, _description, _rarity, _maxStack, _value);
		
		this.damage = _damage;
		this.knockback = _knockback;
		this.range = _range;
	}
	
}
