package fr.iutvalence.info.dut.m2107.inventory;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Character;
import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;

public class Staff extends Weapon {

	public Staff(Vector2f pos, float rot, EntitySprite spr, Collider col, Vector2f vel, short spd,
			short id, String name, String description, Rarity rarity, short maxStack, short value,
			short damage, short range, float useTime, short knockback, short handRotation) {
		super(pos, rot, spr, col, vel, spd, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback, handRotation);
	}
	
	public Staff(EntitySprite spr, short id, String name, String description, Rarity rarity, short maxStack, short value,
			short damage, short range, float useTime, short knockback, short handRotation) {
		super(spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback, handRotation);
	}

	@Override
	public void use(Character owner) {
		if(remainingTime <= 0) {
			Orb orb = (Orb) ItemDatabase.get(9);
			orb.initLaunch(owner, this, .75f, 3f);
			GameWorld.layerMap.getStoredLayer(LayerStore.AMMUNITION).add(orb);
			// Add sound
			this.remainingTime = this.useTime;
		}
		
		super.use(owner);
	}
	
	public Staff copy() {
		Weapon weapon = super.copy();
		Staff newStaff = new Staff(weapon.getPosition(),
								weapon.getRotation(),
								(EntitySprite)weapon.getSprite(),
								weapon.getCollider(),
								weapon.getVelocity(),
								weapon.getSpeed(),
								weapon.getId(),
								weapon.name,
								weapon.description,
								weapon.rarity,
								weapon.MAX_STACK,
								weapon.value,
								weapon.damage,
								weapon.range,
								weapon.useTime,
								weapon.knockback,
								weapon.handRotation);
		return newStaff;
	}
	
}
