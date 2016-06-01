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

public class Gun extends Weapon {

	public Gun(Vector2f pos, float rot, EntitySprite spr, int id, String name, String description, Rarity rarity,
			int maxStack, int value, int damage, int range, float useTime, int knockback) {
		super(pos, rot, spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback);
		handRotation = 0;
	}

	public Gun(EntitySprite spr, int id, String name, String description, Rarity rarity, int maxStack, int value,
			int damage, int range, float useTime, int knockback) {
		super(spr, id, name, description, rarity, maxStack, value, damage, range, useTime, knockback);
		handRotation = 0;
	}

	public Gun(Weapon weapon) {
		super(weapon);
		handRotation = 0;
	}

	@Override
	public void use(Character owner) {
		if(this.remainingTime <= 0) {
			if(owner instanceof Player) {
				Bullet bullet = null;
				for (int i = 0; i < ((Player)owner).getQuickBarLength() ; i++) {
					if(((Player)owner).getQuickBarItem(i) instanceof Bullet) {
						bullet = new Bullet ((Bullet)((Player)owner).getQuickBarItem(i));
						((Player)owner).removeQuickBarItem(i, 1);
						this.launch(bullet, owner);
						break;
					}
				}
				if(bullet == null) {
					bullet = GameWorld.player.getInventory().getBullet();
					if(bullet != null) {
						GameWorld.player.getInventory().remove(bullet, 1);
						this.launch(bullet, owner);
					} else System.out.println("No more bullet in inventory");
				}
			}

			this.remainingTime = this.useTime;
		}
		super.use(owner);
	}

	private void launch(Bullet bullet, Character owner) {
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
		bullet.addWeaponStats(this);
		bullet.initLaunch(owner);
		GameWorld.layerMap.getStoredLayer(LayerStore.AMMUNITION).add(bullet);
		// Audio to add
	}
	
	
}
