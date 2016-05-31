package fr.iutvalence.info.dut.m2107.inventory;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Character;
import fr.iutvalence.info.dut.m2107.entities.Player;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.sound.AudioDataBase;
import fr.iutvalence.info.dut.m2107.sound.OpenAL;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;

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
	public Bow(Vector2f pos, float rot, EntitySprite spr,
				int id, String name, String description, Rarity rarity, int maxStack, int value,
				int damage, int range, float useTime, int knockback) {
		super(pos, rot, spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback);
		handRotation = 20;
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
	public Bow(EntitySprite spr,
				int id, String name, String description, Rarity rarity, int maxStack, int value,
				int damage, int range, float useTime, int knockback) {
		super(spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback);
		handRotation = 20;
	}

	/**
	 * Constructor of a bow
	 * @param bow The bow to copy
	 */
	public Bow(Bow bow) {
		super(bow);
		handRotation = 20;
	}

	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.Weapon#use(fr.iutvalence.info.dut.m2107.entities.Character)
	 */
	@Override
	public void use(Character owner) {
		if(this.remainingTime <= 0) {
			if(owner instanceof Player) {
				Arrow arrow = null;
				for (int i = 0; i < ((Player)owner).getQuickBarLength() ; i++) {
					if(((Player)owner).getQuickBarItem(i) instanceof Arrow) {
						arrow = new Arrow ((Arrow)((Player)owner).getQuickBarItem(i));
						((Player)owner).removeQuickBarItem(i, 1);
						this.launch(arrow, owner);
						break;
					}
				}
				if(arrow == null) {
					arrow = GameWorld.player.getInventory().getArrow();
					if(arrow != null) {
						GameWorld.player.getInventory().remove(arrow, 1);
						this.launch(arrow, owner);
					} else System.out.println("No more arrow in inventory");
				}
			}
			this.remainingTime = this.useTime;
		}
		super.use(owner);
	}
	
	private void launch(Arrow arrow, Character owner) {
		if(GameWorld.player.getDegreeShoot() < 90 && GameWorld.player.getDegreeShoot() > -90)
			owner.getPivot().setRotation(GameWorld.player.getDegreeShoot());
		else if(GameWorld.player.getDegreeShoot() > 90) owner.getPivot().setRotation(180 - GameWorld.player.getDegreeShoot());
		else owner.getPivot().setRotation(-(GameWorld.player.getDegreeShoot()+ 180));
		this.lockTime = Sys.getTime()+500;
		if(GameWorld.player.getDegreeShoot() < 90 && GameWorld.player.getDegreeShoot() > -90)
			owner.getScale().x = 1;
		else owner.getScale().x = -1;
		if(owner.getVelocity().x < 0.1f && owner.getVelocity().x > -0.1f) owner.getVelocity().x = 0;
		if(owner.getPivot().getScale().x != owner.getScale().x) {
			owner.getPivot().getPosition().x = -owner.getPivot().getPosition().x; 
			owner.getPivot().getScale().x = owner.getScale().x;
		}
		arrow.addWeaponStats(this);
		arrow.initLaunch(owner);
		GameWorld.layerMap.getStoredLayer(LayerStore.AMMUNITION).add(arrow);
		OpenAL.source.play(AudioDataBase.arrow());
	}
}