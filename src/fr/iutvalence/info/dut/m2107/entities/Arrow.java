package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public class Arrow extends Ammunition {

	protected boolean isPierce = false;
	
	public Arrow(Vector2f pos, float rot, Sprite spr,
				int id, String name, String description, Rarity rarity, int maxStack, int value,
				int damage, int knockback, Vector2f vel, int speed) {
		super(pos, rot, spr, id, name, description, rarity, maxStack, value, damage, knockback, vel, speed);
	}
	
	public Arrow(Sprite spr,
				int id, String name, String description, Rarity rarity, int maxStack, int value,
				int damage, int knockback, int speed) {
		super(spr, id, name, description, rarity, maxStack, value, damage, knockback, speed);
	}

	public Arrow(Arrow arrow) {
		super(arrow);
	}

	@Override
	public void update(Layer layer) {
		if(!isPierce) {
			this.vel.y -= GameWorld.gravity * DisplayManager.deltaTime();
			
			if(this.vel.y >= 0) this.rot = (float) (Math.atan(this.vel.x / this.vel.y)*180/Math.PI-45);
			else this.rot = (float) (Math.atan(this.vel.x / this.vel.y)*180/Math.PI+135);
			
			this.col.updateColPos();
			if(this.col.isContinuousCollidingWithMap()) this.isPierce = true;
			
			// temporary code for entity collision detection
			/*for (Entity entity : layer) {
				if(entity != this && GameWorld.player != entity && !this.col.isColliding(this.col, entity.col)) {
					this.isPierce = true;
					this.vel = new Vector2f();
				}
			}*/
		}
		super.update(layer);
	}

	public void initLaunch(Weapon weapon) {
		this.pos = new Vector2f(weapon.pos.x, weapon.pos.y);
		this.rot = weapon.rot;
		this.vel = new Vector2f(GameWorld.player.getShoot().x, GameWorld.player.getShoot().y);
		this.vel.scale(this.speed);
	}

}
