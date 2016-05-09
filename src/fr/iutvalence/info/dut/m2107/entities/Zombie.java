package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public class Zombie extends Character {

	public Zombie() {
		// TODO Auto-generated constructor stub
	}

	public Zombie(Vector2f pos, Sprite spr) {
		super(pos, spr);
		// TODO Auto-generated constructor stub
	}

	public Zombie(Vector2f pos, Sprite spr, Collider col) {
		super(pos, spr, col);
		// TODO Auto-generated constructor stub
	}

	public Zombie(Vector2f pos, float rot, Sprite spr, Collider col, Vector2f vel, float spd, int health, int armor,
			int jumpHeight) {
		super(pos, rot, spr, col, vel, spd, health, armor, jumpHeight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Layer layer) {
		float moveX = GameWorld.player.pos.x - this.pos.x;
		if(moveX < 40 && moveX > -40) {
			if(moveX > 1) this.vel.x = this.spd;
			else if(moveX < -1) this.vel.x = -this.spd;
			else this.vel.x = 0;
		} else this.vel.x = 0;			
		super.update(layer);
	}
	
	

}
