package fr.iutvalence.info.dut.m2107.inventory;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Character;
import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.entities.LivingEntity;
import fr.iutvalence.info.dut.m2107.entities.Player;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.sound.AudioDataBase;
import fr.iutvalence.info.dut.m2107.sound.OpenAL;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;

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
			short id, String name, String description, Rarity rarity, short maxStack, short value,
			short damage, short range, float useTime, short knockback , short handRotation) {
		super(pos, rot, spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback, handRotation);
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
			short id, String name, String description, Rarity rarity, short maxStack, short value,
			short damage, short range, float useTime, short knockback, short handRotation) {
		super(spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback, handRotation);
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
			
			//if(!(owner instanceof Player) || ((owner instanceof Player) && !((Player)owner).getWallSlide())) {
			
				OpenAL.source.play(AudioDataBase.sword());
				owner.getPivot().setRotation(-75);
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
				Entity ent = tmpCol.isCollidingWithEntity(new Layer[] {GameWorld.layerMap.getStoredLayer(LayerStore.MOBS)});
				if(ent != owner && ent instanceof LivingEntity)
					((LivingEntity) ent).doDamage(this.damage, this.knockback * (int)owner.getScale().x);
				
				this.remainingTime = this.useTime;
			//}
		}
		super.use(owner);
	}
}