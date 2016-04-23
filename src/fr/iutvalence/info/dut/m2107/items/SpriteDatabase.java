package fr.iutvalence.info.dut.m2107.items;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;

public class SpriteDatabase {

	private static Sprite playerSpr = new Sprite("entities/player", new Vector2f(2, 4));
	
	private static Sprite swordSpr = new Sprite("item/iron_sword", new Vector2f(1, 1));
	private static Sprite bowSpr = new Sprite("item/bow", new Vector2f(1, 1));
	
	private static Sprite arrowSpr = new Sprite("item/arrow", new Vector2f(1, 1));
	private static Sprite spectralArrowSpr = new Sprite("item/spectral_arrow", new Vector2f(1, 1));
	
	
	public static Sprite getPlayerSpr() {return playerSpr;}
	
	public static Sprite getSwordSpr() {return swordSpr;}
	public static Sprite getBowSpr() {return bowSpr;}
	
	public static Sprite getArrowSpr() {return arrowSpr;}
	public static Sprite getSpectralArrowSpr() {return spectralArrowSpr;}
}
