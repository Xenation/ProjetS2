package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;

public class MovableEntity extends Entity {
	
	protected Vector2f velocity;
	
	protected float speed;

	public MovableEntity(Vector2f pos, float rotation, Sprite sprite, Vector2f velocity, float speed) {
		super(pos, rotation, sprite);
		this.velocity = velocity;
		this.speed = speed;
	}
	
	public MovableEntity() {
		super();	
	}
	
	@Override
	public void update() {
		this.rotation += 1;
		if(rotation >= 360)
			rotation = 0;
		this.pos.x = (float) Math.cos(rotation* this.velocity.x * Math.PI/180)*speed;
		this.pos.y = (float) Math.sin(rotation* this.velocity.y * Math.PI/180)*speed;
		//this.pos.x += this.velocity.x * speed * DisplayManager.deltaTime();
		//this.pos.y += this.velocity.y * speed * DisplayManager.deltaTime();
	}

	public Vector2f getVelocity() {
		return velocity;
	}
	
	public float getSpeed() {
		return speed;
	}
}