package fr.iutvalence.info.dut.m2107.items;

public class Bow extends Weapon{
	
	Bow(int id, String name, String description, int maxStack, int value, Rarity rarity, int damage,
			int knockback, int range, int use_time, Effect effect) {
		super(id, name, description, maxStack, value, rarity, damage, knockback, range, use_time, effect);
	}

	public void use(Weapon ammo) {
		// Instantiate an ammo with the Bow range + the ammo range
	}
}
