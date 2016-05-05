package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;

/**
 * Sprite Database
 * @author Voxelse
 *
 */
public class SpriteDatabase {

	private final static Sprite playerSpr = new Sprite("entities/player", new Vector2f(2, 4));
	public static Sprite getPlayerSpr() {return playerSpr;}
	
	private final static Sprite swordSpr = new Sprite("item/iron_sword", new Vector2f(2, 1));
	public static Sprite getSwordSpr() {return swordSpr;}
		
	private final static Sprite bowSpr = new Sprite("item/bow", new Vector2f(1, 2));
	public static Sprite getBowSpr() {return bowSpr;}
	
	private final static Sprite arrowSpr = new Sprite("item/arrow", new Vector2f(1, 1));
	public static Sprite getArrowSpr() {return arrowSpr;}
	
	private final static Sprite lightningArrowSpr = new Sprite("item/lightning_arrow", new Vector2f(1, 1));
	public static Sprite getLightningArrowSpr() {return lightningArrowSpr;}
	
	private final static Sprite chestSpr = new Sprite("entities/chest", new Vector2f(2, 2));
	public static Sprite getChestSpr() {return chestSpr;}
}
