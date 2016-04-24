package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.items.AmmunitionItem;
import fr.iutvalence.info.dut.m2107.items.Item;
import fr.iutvalence.info.dut.m2107.items.WeaponItem;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

public class EntityDatabase {
	
	public static Entity item(Item item, Character owner) {
		return new Entity(owner.pos, 0, item.getSpr(), null);
	}
	
	public static WeaponEntity weapon(WeaponItem weapon, Character owner) {
		return new WeaponEntity(weapon.getSubtype(), owner.pos, 0, weapon.getSpr(), null, weapon.getDamage(), weapon.getRange(), weapon.getUseTime(), weapon.getKnockback(), owner);
	}
	
	public static AmmunitionEntity ammo(AmmunitionItem ammo, float degreeShoot, float shootX, float shootY, WeaponEntity ownWeapon) {
		switch (ammo.getSubtype()) {
			case ARROW:
				return new ArrowEntity(new Vector2f(GameWorld.player.getPosition().x,GameWorld.player.getPosition().y), degreeShoot+45, ammo.getSpr(), new Collider(-.01f, -.01f, .01f, .01f), (Vector2f) new Vector2f(shootX, shootY).normalise(), ammo.getDamage(), ammo.getSpeed(), ammo.getKnockback(), ownWeapon);				
			case BULLET:
				return new AmmunitionEntity(new Vector2f(GameWorld.player.getPosition().x,GameWorld.player.getPosition().y), degreeShoot+45, ammo.getSpr(), new Collider(-.01f, -.01f, .01f, .01f), (Vector2f) new Vector2f(shootX, shootY).normalise(), ammo.getDamage(), ammo.getSpeed(), ammo.getKnockback(), ownWeapon);
			default :
				return null;
		}
	}
}