package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public abstract class Entity {

	private final Vector2f DEF_POS = new Vector2f(0, 0);
	private final float DEF_ROT = 0;
	protected final Sprite DEF_SPR = new Sprite("item/default", new Vector2f(2, 4));
	
	protected Vector2f pos;
	
	protected float rot;
	
	protected Sprite spr;
	
	public Entity(Vector2f pos, float rot, Sprite spr) {
		this.pos = pos;
		this.rot = rot;
		this.spr = spr;
	}
	
	public Entity(Vector2f pos, Sprite spr) {
		this.pos = pos;
		this.rot = DEF_ROT;
		this.spr = spr;
	}
	
	public Entity() {
		this.pos = DEF_POS;
		this.rot = DEF_ROT;
		this.spr = DEF_SPR;
	}
	
	public void update(Layer layer) {
		return;
	}

	public Sprite getSprite() {
		return this.spr;
	}

	public Vector2f getPosition() {
		return pos;
	}

	public void setPosition(Vector2f pos) {
		this.pos = pos;
	}

	public float getRotation() {
		return rot;
	}

	public void setRotation(float rot) {
		this.rot = rot;
	}	
}