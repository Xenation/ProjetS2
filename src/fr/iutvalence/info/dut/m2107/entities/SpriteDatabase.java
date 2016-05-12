package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.EntitySprite;

/**
 * Sprite Database
 * @author Voxelse
 *
 */
public class SpriteDatabase {

	private final static EntitySprite emptySpr = new EntitySprite("entities/empty", new Vector2f(1, 1));
	public static EntitySprite getEmptySpr() {return emptySpr;}
	
	private final static EntitySprite zombieSpr = new EntitySprite("entities/zombie", new Vector2f(2, 4));
	public static EntitySprite getZombieSpr() {return zombieSpr;}
	
	private final static EntitySprite playerSpr = new EntitySprite("entities/player", new Vector2f(2, 4));
	public static EntitySprite getPlayerSpr() {return playerSpr;}
	
	private final static EntitySprite swordSpr = new EntitySprite("item/iron_sword", new Vector2f(2, 1));
	public static EntitySprite getSwordSpr() {return swordSpr;}
		
	private final static EntitySprite bowSpr = new EntitySprite("item/bow", new Vector2f(1, 2));
	public static EntitySprite getBowSpr() {return bowSpr;}
	
	private final static EntitySprite arrowSpr = new EntitySprite("item/arrow", new Vector2f(1, 1));
	public static EntitySprite getArrowSpr() {return arrowSpr;}
	
	private final static EntitySprite lightningArrowSpr = new EntitySprite("item/lightning_arrow", new Vector2f(1, 1));
	public static EntitySprite getLightningArrowSpr() {return lightningArrowSpr;}
	
	private final static EntitySprite chestSpr = new EntitySprite("entities/chest", new Vector2f(2, 2));
	public static EntitySprite getChestSpr() {return chestSpr;}
	
	private final static String heartStr = "gui/heart";
	public static String getHeartStr() {return heartStr;}
	
	private final static String quickBarSlotStr = "gui/quick_bar_slot";
	public static String getQuickBarSlotStr() {return quickBarSlotStr;}
	
	private final static String selectQuickBarSlotStr = "gui/select_quick_bar_slot";
	public static String getSelectQuickBarSlotStr() {return selectQuickBarSlotStr;}
}
