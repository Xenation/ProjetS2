package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

public class Bow extends Weapon {

	public Bow(Vector2f pos, float rot, Sprite spr,
				int id, String name, String description, Rarity rarity, int maxStack, int value,
				int damage, int range, float useTime, int knockback) {
		super(pos, rot, spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback);
	}
	
	public Bow(Sprite spr,
				int id, String name, String description, Rarity rarity, int maxStack, int value,
				int damage, int range, float useTime, int knockback) {
		super(spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback);
	}

	public Bow(Bow bow) {
		super(bow);
	}

	@Override
	public void use(Character owner) {
		if(owner instanceof Player) {
			Arrow arrow = GameWorld.player.getInventory().getArrow();
			if(arrow != null) {
				GameWorld.player.getInventory().remove(arrow, 1);
				arrow.addWeaponStats(this);
				arrow.initLaunch(this);
				GameWorld.layerMap.getLayer(0).add(arrow);
			} else System.out.println("No more arrow in inventory");
		}
		super.use(owner);
	}

}
