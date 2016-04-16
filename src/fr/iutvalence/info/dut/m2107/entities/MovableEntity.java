package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public class MovableEntity extends Entity {
	
	private final Vector2f DEF_VEL = new Vector2f(0, 0);
	private final float DEF_SPD = 4;
	
	protected Vector2f vel;
	
	protected float spd;
	
	protected Collider col;

	public MovableEntity(Vector2f pos, float rot, Sprite spr, Collider col, Vector2f vel, float spd) {
		super(pos, rot, spr);
		this.vel = vel;
		this.spd = spd;
		this.col = new Collider();
		col.setEnt(this);
		col.updateColPos();
	}
	
	public MovableEntity(Vector2f pos, Sprite spr) {
		super(pos, spr);
		this.vel = DEF_VEL;
		this.spd = DEF_SPD;
		this.col = new Collider();
		col.setEnt(this);
		col.updateColPos();
	}
	
	public MovableEntity() {
		super();
		this.vel = DEF_VEL;
		this.spd = DEF_SPD;
		this.col = new Collider();
		col.setEnt(this);
		col.updateColPos();
	}
	
	@Override
	public void update(Layer layer) {
		this.pos.x += this.vel.x * DisplayManager.deltaTime();
		this.pos.y += this.vel.y * DisplayManager.deltaTime();
	}

	public Vector2f getVelocity() {
		return vel;
	}
	
	public float getSpeed() {
		return spd;
	}
}