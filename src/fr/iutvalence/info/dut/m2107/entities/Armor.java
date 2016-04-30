package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;

/**
 * An armor item
 * @author Voxelse
 *
 */
public class Armor extends Item {

	/**
	 * The defense of the armor
	 */
	protected final int defense;
	
	/**
	 * Constructor of an armor
	 * @param pos The position of the armor
	 * @param rot The rotation of the armor
	 * @param spr The sprite of the armor
	 * @param id The id of the armor
	 * @param name The name of the armor
	 * @param description The description of the armor
	 * @param rarity The rarity of the armor
	 * @param maxStack The maximum stack of the armor
	 * @param value The value of the armor
	 * @param defense The defense of the armor
	 */
	public Armor(Vector2f pos, float rot, Sprite spr, int id, String name, String description, Rarity rarity,
			int maxStack, int value, int defense) {
		super(pos, rot, spr, id, name, description, rarity, maxStack, value);
		this.defense = defense;
	}

	/**
	 * Return the defense of the armor
	 * @return the defense of the armor
	 */
	public int getDefense() {return defense;}
}
