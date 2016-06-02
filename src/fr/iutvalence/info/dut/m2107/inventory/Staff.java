package fr.iutvalence.info.dut.m2107.inventory;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Character;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.sound.AudioDataBase;
import fr.iutvalence.info.dut.m2107.sound.OpenAL;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;

public class Staff extends Weapon {

	public Staff(Vector2f pos, float rot, EntitySprite spr, short id, String name, String description, Rarity rarity,
			short maxStack, short value, short damage, short range, float useTime, short knockback, short handRotation) {
		super(pos, rot, spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback, handRotation);
	}

	public Staff(EntitySprite spr, short id, String name, String description, Rarity rarity, short maxStack, short value,
			short damage, short range, float useTime, short knockback, short handRotation) {
		super(spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback, handRotation);
	}

	public Staff(Weapon weapon) {
		super(weapon);
	}

	@Override
	public void use(Character owner) {
		if(remainingTime <= 0) {
			Orb orb = (Orb) ItemDatabase.get(9);
			orb.addWeaponStats(this);
			orb.initLaunch(owner);
			GameWorld.layerMap.getStoredLayer(LayerStore.AMMUNITION).add(orb);
			// Add sound
			this.remainingTime = this.useTime;
		}
		
		super.use(owner);
	}

}
