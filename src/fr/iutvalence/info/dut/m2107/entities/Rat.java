package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public class Rat extends TerrestrialCreature {

	public boolean wallWalk = false;
	public boolean previousWalk = false;
	
	public Rat(Vector2f pos, float rot, EntitySprite spr, Collider col, Vector2f vel, float spd, int health, int armor,
			int jumpHeight) {
		super(pos, rot, spr, col, vel, spd, health, armor, jumpHeight);
	}

	public Rat(Vector2f pos, EntitySprite spr) {
		super(pos, spr);
	}

	public Rat(Vector2f pos, EntitySprite spr, Collider col) {
		super(pos, spr, col);
	}

	@Override
	public void update(Layer layer) {
		if(!wallWalk && previousWalk)
			this.vel.y = -this.spd * this.scale.x;
		
		previousWalk = wallWalk;
		
		if(wallWalk) {
			this.rot = -90;
			this.vel.y += this.spd * this.scale.x;
			this.vel.y += GameWorld.gravity * DisplayManager.deltaTime();
		} else {
			this.rot = 0;
		}
		this.vel.x += this.spd * this.scale.x;
		super.update(layer);
	}
}
