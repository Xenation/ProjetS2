package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;

public abstract class Entity {

	private Vector2f pos;
	
	private float rotation;
	
	private Sprite sprite;
	
	public Entity(Vector2f pos, float rotation, Sprite sprite) {
		this.pos = pos;
		this.rotation = rotation;
		this.sprite = sprite;
	}
	
	public Entity() {
		this.pos = new Vector2f(0, 0);
		this.rotation = 0;
		this.sprite = null;
	}
	
	public void update() {
		
	}
}