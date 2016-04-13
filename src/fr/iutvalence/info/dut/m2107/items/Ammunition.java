package fr.iutvalence.info.dut.m2107.items;

public class Ammunition extends Weapon{

	Ammunition(int id, String name, String description, int maxStack, int value, Rarity rarity, int damage,
			int knockback, int range, int use_time, Effect effect) {
		super(id, name, description, maxStack, value, rarity, damage, knockback, range, use_time, effect);
	}

	public void shoot() {
		/*while( ammo havn't reach a target or lifetime not exceed) {
			ammo += velocity
		}*/
	}
	
}
