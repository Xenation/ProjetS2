package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;

public class Armor extends Item {

	protected final int defence;
	
	public Armor(Vector2f pos, float rot, Sprite spr, int id, String name, String description, Rarity rarity,
			int maxStack, int value, int defence) {
		super(pos, rot, spr, id, name, description, rarity, maxStack, value);
		this.defence = defence;
	}

	public int getDefence() {return defence;}
}
