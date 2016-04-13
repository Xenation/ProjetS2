package fr.iutvalence.info.dut.m2107.items;

public class Sword extends Weapon{

	Sword(int id, String name, String description, int maxStack, int value, Rarity rarity, int damage,
			int knockback, int range, int use_time, Effect effect) {
		super(id, name, description, maxStack, value, rarity, damage, knockback, range, use_time, effect);
	}

	public void use() {
		// Attack the mobs facing you in a "range" by "damage" HP and push them with "knockback" units;
	}
}
