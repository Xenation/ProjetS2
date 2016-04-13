package fr.iutvalence.info.dut.m2107.items;

public class Armor extends Item{

	private int defense;
	private ArmorType type;
	
	Armor(int id, String name, String description, Rarity rarity, int maxStack, int value, int defense, ArmorType type) {
		super(id, name, description, rarity, maxStack, value);
		this.defense = defense;
		this.type = type;
	}
}