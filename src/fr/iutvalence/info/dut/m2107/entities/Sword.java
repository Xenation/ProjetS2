package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;

public class Sword extends Weapon {

	public Sword(Vector2f pos, float rot, Sprite spr,
				int id, String name, String description, Rarity rarity, int maxStack, int value,
				int damage, int range, float useTime, int knockback) {
		super(pos, rot, spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback);
	}
	
	public Sword(Sprite spr,
				int id, String name, String description, Rarity rarity, int maxStack, int value,
				int damage, int range, float useTime, int knockback) {
		super(spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback);
	}

	public Sword(Sword sword) {
		super(sword);
	}

	@Override
	public void use(Character owner) {
//		Collider tmpCol = new Collider(owner.col.getMin(), owner.col.getMax());
//		tmpCol.extendRight(range);
//		Entity ent = tmpCol.isCollidingWithEntity(GameWorld.layerMap.getLayer(1));
//		if(ent != this.owner)
//			if(ent instanceof LivingEntity) {
//				((LivingEntity) ent).takeDamage(this.damage);
//				System.out.println(this.damage + " damage to " + ent + "\t" + ((LivingEntity) ent).health + " hp left");
//			}
		super.use(owner);
	}

	
}
