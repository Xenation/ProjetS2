package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

public class Sword extends Weapon {

	/**
	 * Constructor of a sword
	 * @param pos The position of the sword
	 * @param rot The rotation of the sword
	 * @param spr The sprite of the sword
	 * @param id The id of the sword
	 * @param name The name of the sword
	 * @param description The description of the sword
	 * @param rarity The rarity of the sword
	 * @param maxStack The max stack of the sword
	 * @param value The value of the sword
	 * @param damage The damage of the sword
	 * @param range The range of the sword
	 * @param useTime The use time of the sword
	 * @param knockback The knockback of the sword
	 */
	public Sword(Vector2f pos, float rot, EntitySprite spr,
				int id, String name, String description, Rarity rarity, int maxStack, int value,
				int damage, int range, float useTime, int knockback) {
		super(pos, rot, spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback);
	}
	
	/**
	 * Constructor of a sword
	 * @param spr The sprite of the sword
	 * @param id The id of the sword
	 * @param name The name of the sword
	 * @param description The description of the sword
	 * @param rarity The rarity of the sword
	 * @param maxStack The max stack of the sword
	 * @param value The value of the sword
	 * @param damage The damage of the sword
	 * @param range The range of the sword
	 * @param useTime The use time of the sword
	 * @param knockback The knockback of the sword
	 */
	public Sword(EntitySprite spr,
				int id, String name, String description, Rarity rarity, int maxStack, int value,
				int damage, int range, float useTime, int knockback) {
		super(spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback);
	}

	/**
	 * Constructor of a sword
	 * @param sword The sword to copy
	 */
	public Sword(Sword sword) {
		super(sword);
	}

	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.Weapon#use(fr.iutvalence.info.dut.m2107.entities.Character)
	 */
	@Override
	public void use(Character owner) {
		if(remainingTime <= 0) {
			Collider tmpCol = new Collider(owner.col.getMin(), owner.col.getMax());
			if(owner.scale.x == 1) tmpCol.extendRight(range);
			else tmpCol.extendLeft(range);
			Entity ent = tmpCol.isCollidingWithEntity(GameWorld.layerMap.getLayer(0));
			if(ent != owner && ent instanceof LivingEntity) {
				((LivingEntity) ent).takeKnockback(this.knockback * (int)owner.scale.x);
				((LivingEntity) ent).takeDamage(this.damage);
			}
			this.remainingTime = this.useTime;
		}
		super.use(owner);
	}
}