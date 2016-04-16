package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public abstract class Entity {

	private final Vector2f DEF_POS = new Vector2f(0, 0);
	private final float DEF_ROT = 0;
	protected final Sprite DEF_SPR = new Sprite("item/default", new Vector2f(2, 4));
	
	protected Vector2f pos;
	
	protected Vector2f scale;
	
	protected float rot;
	
	protected Sprite spr;
	
	public Entity(Vector2f pos, float rot, Sprite spr) {
		this.pos = pos;
		this.rot = rot;
		this.spr = spr;
		this.scale = new Vector2f(1, 1);
	}
	
	public Entity(Vector2f pos, Vector2f scale, Sprite spr) {
		this.pos = pos;
		this.rot = DEF_ROT;
		this.spr = spr;
		this.scale = scale;
	}
	
	public Entity(Vector2f pos, Sprite spr) {
		this.pos = pos;
		this.rot = DEF_ROT;
		this.spr = spr;
		this.scale = new Vector2f(1, 1);
	}
	
	
	public Entity() {
		this.pos = DEF_POS;
		this.rot = DEF_ROT;
		this.spr = DEF_SPR;
		this.scale = new Vector2f(1, 1);
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

	public Vector2f getScale() {
		return scale;
	}

	public void setScale(Vector2f scale) {
		this.scale = scale;
	}
	
	public void setScale(float w, float h) {
		this.scale.x = w;
		this.scale.y = h;
	}

	public float getRotation() {
		return rot;
	}

	public void setRotation(float rot) {
		this.rot = rot;
	}	
}