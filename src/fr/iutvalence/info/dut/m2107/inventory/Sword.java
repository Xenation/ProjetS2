package fr.iutvalence.info.dut.m2107.inventory;

import org.lwjgl.Sys;

import fr.iutvalence.info.dut.m2107.entities.Character;
import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.entities.LivingEntity;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.sound.AudioDataBase;
import fr.iutvalence.info.dut.m2107.sound.OpenAL;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class Sword extends Weapon {
	
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
			short id, String name, String description, Rarity rarity, short maxStack, short value,
			short damage, short range, float useTime, short knockback, short handRotation) {
		super(spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback, handRotation);
	}

	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.Weapon#use(fr.iutvalence.info.dut.m2107.entities.Character)
	 */
	@Override
	public void use(Character owner) {
		if(remainingTime <= 0) {
			
			//if(!(owner instanceof Player) || ((owner instanceof Player) && !((Player)owner).getWallSlide())) {
			
				OpenAL.source.play(AudioDataBase.sword());
				owner.getPivot().setRotation(-90);
				this.handRotation = (short) Maths.fastAbs(this.handRotation); 
				this.lockTime = Sys.getTime()+500;
				if(GameWorld.player.getDegreeShoot() < 90 && GameWorld.player.getDegreeShoot() > -90)
					owner.getScale().x = 1;
				else owner.getScale().x = -1;
				if(owner.getVelocity().x < 0.1f && owner.getVelocity().x > -0.1f) owner.getVelocity().x = 0;
				if(owner.getPivot().getScale().x != owner.getScale().x) {
					owner.getPivot().getPosition().x = -owner.getPivot().getPosition().x; 
					owner.getPivot().getScale().x = owner.getScale().x;
				}
				
				Collider tmpCol = new Collider(owner.getCollider().getMin(), owner.getCollider().getMax());
				if(owner.getScale().x > 0) tmpCol.extendRight(range);
				else tmpCol.extendLeft(range);
				Entity ent = tmpCol.isCollidingWithEntity(new Layer[] {GameWorld.layerMap.getStoredLayer(LayerStore.MOBS), GameWorld.layerMap.getStoredLayer(LayerStore.FURNITURE)});
				if(ent != owner && ent instanceof LivingEntity)
					((LivingEntity) ent).doDamage(this.damage, this.knockback * (int)owner.getScale().x);
				
				this.remainingTime = this.useTime;
			//}
		}
		super.use(owner);
	}
	
	public Sword copy() {
		Weapon weapon = super.copy();
		Sword newSword = new Sword((EntitySprite)weapon.getSprite(),
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
		return newSword;
	}
}