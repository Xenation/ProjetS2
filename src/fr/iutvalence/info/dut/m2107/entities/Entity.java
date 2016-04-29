package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public class Entity {
	
	private static final Vector2f DEF_POS = new Vector2f(0, 0);
	private static final float DEF_ROT = 0;
	private static final Sprite DEF_SPR = new Sprite("item/default", new Vector2f(1, 1));
	private static final Vector2f DEF_SCALE = new Vector2f(1, 1);
	
	protected Vector2f pos;
	
	protected Vector2f scale;
	
	protected float rot;
	
	protected final Sprite spr;
	
	protected final Collider col;
	
	public Entity(Vector2f pos, float rot, Sprite spr) {
		this.pos = pos;
		this.rot = rot;
		this.spr = spr;
		this.scale = new Vector2f(DEF_SCALE.x, DEF_SCALE.y);
		this.col = new Collider(spr);
		col.setEnt(this);
		col.updateColPos();
	}

	public Entity(Vector2f pos, float rot, Sprite spr, Collider col) {
		this.pos = pos;
		this.rot = rot;
		this.spr = spr;
		this.scale = new Vector2f(DEF_SCALE.x, DEF_SCALE.y);
		this.col = col;
		if(col != null) {
			col.setEnt(this);
			col.updateColPos();
		}
	}
	
	public Entity(Vector2f pos, Vector2f scale, Sprite spr) {
		this.pos = pos;
		this.rot = DEF_ROT;
		this.spr = spr;
		this.scale = scale;
		this.col = new Collider(spr);
		col.setEnt(this);
		col.updateColPos();
	}
	
	public Entity(Vector2f pos, Sprite spr) {
		this.pos = pos;
		this.rot = DEF_ROT;
		this.spr = spr;
		this.scale = new Vector2f(DEF_SCALE.x, DEF_SCALE.y);
		this.col = new Collider(spr);
		col.setEnt(this);
		col.updateColPos();
	}
	
	public Entity(Vector2f pos, Sprite spr, Collider col) {
		this.pos = pos;
		this.rot = DEF_ROT;
		this.spr = spr;
		this.scale = new Vector2f(DEF_SCALE.x, DEF_SCALE.y);
		this.col = col;
		col.setEnt(this);
		col.updateColPos();
	}
	
	public Entity(Sprite spr) {
		this.pos = new Vector2f(DEF_POS.x, DEF_POS.y);
		this.rot = DEF_ROT;
		this.spr = spr;
		this.scale = new Vector2f(DEF_SCALE.x, DEF_SCALE.y);
		this.col = new Collider(spr);
		col.setEnt(this);
		col.updateColPos();
	}
	
	public Entity(Sprite spr, Collider col) {
		this.pos = new Vector2f(DEF_POS.x, DEF_POS.y);
		this.rot = DEF_ROT;
		this.spr = spr;
		this.scale = new Vector2f(DEF_SCALE.x, DEF_SCALE.y);
		this.col = col;
		col.setEnt(this);
		col.updateColPos();
	}
	
	
	public Entity() {
		this.pos = new Vector2f(DEF_POS.x, DEF_POS.y);
		this.rot = DEF_ROT;
		this.spr = DEF_SPR;
		this.scale = new Vector2f(DEF_SCALE.x, DEF_SCALE.y);
		this.col = new Collider(spr);
		col.setEnt(this);
		col.updateColPos();
	}
	
	public void update(Layer layer) {
		return;
	}
	
	public Sprite getSprite() {return this.spr;}
	
	public Vector2f getPosition() {return pos;}
	public void setPosition(Vector2f pos) {this.pos = pos;}
	public void setPosition(float x, float y) {
		this.pos.x = pos.x;
		this.pos.y = pos.y;
	}
	
	public Vector2f getScale() {return scale;}
	public void setScale(Vector2f scale) {this.scale = scale;}
	public void setScale(float w, float h) {
		this.scale.x = w;
		this.scale.y = h;
	}

	public float getRotation() {return rot;}
	public void setRotation(float rot) {this.rot = rot;}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		if (spr == null) {
			if (other.spr != null)
				return false;
		} else if (!spr.equals(other.spr))
			return false;
		return true;
	}
	
}