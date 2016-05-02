package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

/**
 * A bow weapon
 * @author Voxelse
 *
 */
public class Bow extends Weapon {

	/**
	 * Constructor of a bow
	 * @param pos The position of the bow
	 * @param rot The rotation of the bow
	 * @param spr The sprite of the bow
	 * @param id The id of the bow
	 * @param name The name of the bow
	 * @param description The description of the bow
	 * @param rarity The rarity of the bow
	 * @param maxStack The max stack of the bow
	 * @param value The value of the bow
	 * @param damage The damage of the bow
	 * @param range The range of the bow
	 * @param useTime The use time of the bow
	 * @param knockback The knockback of the bow
	 */
	public Bow(Vector2f pos, float rot, Sprite spr,
				int id, String name, String description, Rarity rarity, int maxStack, int value,
				int damage, int range, float useTime, int knockback) {
		super(pos, rot, spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback);
	}
	
	/**
	 * Constructor of a bow
	 * @param spr The sprite of the bow
	 * @param id The id of the bow
	 * @param name The name of the bow
	 * @param description The description of the bow
	 * @param rarity The rarity of the bow
	 * @param maxStack The max stack of the bow
	 * @param value The value of the bow
	 * @param damage The damage of the bow
	 * @param range The range of the bow
	 * @param useTime The use time of the bow
	 * @param knockback The knockback of the bow
	 */
	public Bow(Sprite spr,
				int id, String name, String description, Rarity rarity, int maxStack, int value,
				int damage, int range, float useTime, int knockback) {
		super(spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback);
	}

	/**
	 * Constructor of a bow
	 * @param bow The bow to copy
	 */
	public Bow(Bow bow) {
		super(bow);
	}

	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.Weapon#use(fr.iutvalence.info.dut.m2107.entities.Character)
	 */
	@Override
	public void use(Character owner) {
		if(this.remainingTime <= 0) {
			if(owner instanceof Player) {
				Arrow arrow = GameWorld.player.getInventory().getArrow();
				if(arrow != null) {
					GameWorld.player.getInventory().remove(arrow, 1);
					arrow.addWeaponStats(this);
					arrow.initLaunch();
					GameWorld.layerMap.getLayer(0).add(arrow);
				} else System.out.println("No more arrow in inventory");
			}
			this.remainingTime = this.useTime;
		}
		super.use(owner);
	}
}