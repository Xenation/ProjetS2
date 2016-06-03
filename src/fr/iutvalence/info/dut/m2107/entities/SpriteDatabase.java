package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Atlas;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.render.Loader;

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
	
	private final static EntitySprite playerSpr = new EntitySprite(new Atlas("entities/player_atlas", 16, 16, Loader.SPRITE_LOADER), new Vector2f(4, 4));
	public static EntitySprite getPlayerSpr() {return playerSpr;}
	
	private final static EntitySprite swordSpr = new EntitySprite("item/iron_sword", new Vector2f(2, 1));
	public static EntitySprite getSwordSpr() {return swordSpr;}
		
	private final static EntitySprite bowSpr = new EntitySprite("item/bow", new Vector2f(1, 2));
	public static EntitySprite getBowSpr() {return bowSpr;}
	
	private final static EntitySprite arrowSpr = new EntitySprite("item/arrow", new Vector2f(1, 1));
	public static EntitySprite getArrowSpr() {return arrowSpr;}
	
	private final static EntitySprite gunSpr = new EntitySprite("item/gun", new Vector2f(1, 1));
	public static EntitySprite getGunSpr() {return gunSpr;}
	
	private final static EntitySprite bulletSpr = new EntitySprite("item/bullet", new Vector2f(1.5f, 1.5f));
	public static EntitySprite getBulletSpr() {return bulletSpr;}
	
	private final static EntitySprite staffSpr = new EntitySprite("item/staff", new Vector2f(3, 1.5f));
	public static EntitySprite getStaffSpr() {return staffSpr;}
	
	private final static EntitySprite airOrbSpr = new EntitySprite("item/air_orb", new Vector2f(1.5f, 1.5f));
	public static EntitySprite getAirOrbSpr() {return airOrbSpr;}
	
	private final static EntitySprite lightningArrowSpr = new EntitySprite("item/lightning_arrow", new Vector2f(1, 1));
	public static EntitySprite getLightningArrowSpr() {return lightningArrowSpr;}
	
	private final static EntitySprite frozenArrowSpr = new EntitySprite("item/frozen_arrow", new Vector2f(1, 1));
	public static EntitySprite getFrozenArrowSpr() {return frozenArrowSpr;}
	
	private final static EntitySprite burningArrowSpr = new EntitySprite("item/burning_arrow", new Vector2f(1, 1));
	public static EntitySprite getBurningArrowSpr() {return burningArrowSpr;}
	
	private final static EntitySprite chestSpr = new EntitySprite("entities/chest", new Vector2f(2, 2));
	public static EntitySprite getChestSpr() {return chestSpr;}
	
	@SuppressWarnings("unused")
	private final static EntitySprite slime0Spr = new EntitySprite("entities/slime_0", new Vector2f(1.75f, 1.75f));
	@SuppressWarnings("unused")
	private final static EntitySprite slime1Spr = new EntitySprite("entities/slime_1", new Vector2f(1.75f, 1.75f));
	@SuppressWarnings("unused")
	private final static EntitySprite slime2Spr = new EntitySprite("entities/slime_2", new Vector2f(1.75f, 1.75f));
	@SuppressWarnings("unused")
	private final static EntitySprite slime3Spr = new EntitySprite("entities/slime_3", new Vector2f(1.75f, 1.75f));
	public static EntitySprite getSlimeSpr() {
		try {
			return (EntitySprite) SpriteDatabase.class.getDeclaredField("slime" + (int)(Math.random()*4) + "Spr").get(null);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private final static EntitySprite ratSpr = new EntitySprite(new Atlas("entities/rat_atlas", 2, 2, Loader.SPRITE_LOADER), new Vector2f(1, .5f));
	public static EntitySprite getRatSpr() {return ratSpr;}
	
	private final static String heartStr = "gui/heart";
	public static String getHeartStr() {return heartStr;}
	
	private final static String quickBarStr = "gui/quick_bar";
	public static String getQuickBarStr() {return quickBarStr;}
	
	private final static String selectQuickBarSlotStr = "gui/select_quick_bar_slot";
	public static String getSelectQuickBarSlotStr() {return selectQuickBarSlotStr;}
	
	private final static String inventoryGUI = "gui/inventoryGUI";
	public static String getInventoryGUIStr() {return inventoryGUI;}
}
