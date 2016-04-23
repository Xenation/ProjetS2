package fr.iutvalence.info.dut.m2107.items;

import fr.iutvalence.info.dut.m2107.models.Sprite;

public class ArmorItem extends Item{

	public enum ArmorType {
		HEAD,
		CHEST,
		LEG,
	}
	
	protected int defense;
	
	protected ArmorType subtype;
	
	ArmorItem(int id, Sprite spr, ArmorType subtype, String name, String description, Rarity rarity, int maxStack, int value, int defense) {
		super(id, spr, ItemType.ARMOR, name, description, rarity, maxStack, value);
		this.defense = defense;
		this.subtype = subtype;
	}

	public int getDefense() {return defense;}
	public ArmorType getSubtype() {return subtype;}
}