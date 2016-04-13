package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public class Collider {

	private float x, y;
	private float w, h;
	
	private Entity ent;
	
	public Collider() {
		this.x = this.y = 0;
		this.w = this.h = 1;
	}
	
	public Collider(Vector2f pos, int w, int h) {
		this.x = pos.x;
		this.y = pos.y;
		this.w = w;
		this.h = h;
	}
	
	public Collider(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void checkCollision(Player thisEntity, Entity entity) {
		if (thisEntity.col.isCollidingNext(entity.col, new Vector2f(thisEntity.pos.x + thisEntity.vel.x * thisEntity.spd * DisplayManager.deltaTime(),
				thisEntity.pos.y + thisEntity.vel.y * thisEntity.spd * DisplayManager.deltaTime()))) {
			if(!this.isCollidingLeft(entity.col)) {
				thisEntity.vel.x = 0;
				thisEntity.pos.x = entity.pos.x + entity.col.getW();
			}
			if(!this.isCollidingRight(entity.col)) {
				thisEntity.vel.x = 0;
				thisEntity.pos.x = entity.pos.x - this.getW();
			}
			if(!this.isCollidingTop(entity.col)) {
				thisEntity.vel.y = 0;
				thisEntity.pos.y = entity.pos.y - this.getH();
			}
			if(!this.isCollidingBot(entity.col)) {
				thisEntity.vel.y = 0;
				thisEntity.pos.y = entity.pos.y + entity.col.getH();
				thisEntity.isGrounded = true;
			}
		}
	}
	
	public boolean isCollidingNext(Collider col, Vector2f nextThis){
		if ((col.getWX() < nextThis.x + this.w)
				&& (col.getWX() + col.w > nextThis.x)
					&& (col.getWY() < nextThis.y + this.h)
						&& (col.getWY() + col.h > nextThis.y)) {
				return true;
			}
		return false;
	}
	
	public boolean isCollidingLeft(Collider col) {
		if (col.getWX() + col.w > this.getWX()) return true;
		return false;
	}
	
	public boolean isCollidingRight(Collider col) {
		if (col.getWX() < this.getWX() + this.w) return true;
		return false;
	}
	
	public boolean isCollidingTop(Collider col) {
		if (col.getWY() < this.getWY() + this.h) return true;
		return false;
	}
	
	public boolean isCollidingBot(Collider col) {
		if (col.getWY() + col.h > this.getWY()) return true;
		return false;
	}
	
	public float getX() {return x;}

	public float getY() {return y;}

	public float getW() {return w;}

	public float getH() {return h;}
	
	public float getWX() {return ent.pos.x + this.x;}
	
	public float getWY() {return ent.pos.y + this.y;}
	
	public void setEnt(Entity ent) {this.ent = ent;}
}
