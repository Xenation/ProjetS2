package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public class Zombie extends Character {
	
	public Zombie() {
		// TODO Auto-generated constructor stub
	}

	public Zombie(Vector2f pos, EntitySprite spr) {
		super(pos, spr);
		// TODO Auto-generated constructor stub
	}

	public Zombie(Vector2f pos, EntitySprite spr, Collider col) {
		super(pos, spr, col);
		// TODO Auto-generated constructor stub
	}

	public Zombie(Vector2f pos, float rot, EntitySprite spr, Collider col, Vector2f vel, float spd, int health, int armor,
			int jumpHeight) {
		super(pos, rot, spr, col, vel, spd, health, armor, jumpHeight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Layer layer) {
		float moveX = GameWorld.player.pos.x - this.pos.x;
		if(moveX < 40 && moveX > -40 && this.recoil == 0) {
			if(moveX > 2) this.vel.x += this.spd/2;
			else if(moveX < -2) this.vel.x += -this.spd/2;
			else {
				Collider tmpCol = new Collider(this.col.getMin(), this.col.getMax());
				if(this.scale.x == 1) tmpCol.extendRight(2);
				else tmpCol.extendLeft(2);
				Entity ent = tmpCol.isCollidingWithEntity(GameWorld.layerMap.getLayer(1));
				if(ent == GameWorld.player) {
					((LivingEntity) ent).takeKnockback(10 * (int)this.scale.x);
					((LivingEntity) ent).takeDamage(1);
				}
			}
		}
		super.update(layer);
	}
	
	

}