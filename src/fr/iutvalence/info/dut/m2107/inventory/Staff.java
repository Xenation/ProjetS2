package fr.iutvalence.info.dut.m2107.inventory;

import fr.iutvalence.info.dut.m2107.entities.Character;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;

public class Staff extends Weapon {
	
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
		Staff newStaff = new Staff((EntitySprite)weapon.getSprite(),
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
