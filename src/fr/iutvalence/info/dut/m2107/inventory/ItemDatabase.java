package fr.iutvalence.info.dut.m2107.inventory;

import java.util.*;

import org.lwjgl.util.vector.Vector3f;

import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.entities.SpriteDatabase;

/**
 * This class create and initialize a List of item which is the ItemDatabase of the game
 * @author boureaue
 *
 */

public class ItemDatabase {
	
	/**
	 * A list of Item which contain all the items of the game
	 */
	private static List<Item> itemDatabase = new ArrayList<Item>();

	/**
	 * Create the ItemDatabase
	 */
	public static void create() {
		
		// AMMUNIITON
		//					TYPE				SPR				 							COL								  SPEED			  ID		NAME					DESC					RARITY					  MAX			  VAL			  DMG			  KNO 		COLOR						    	BOUND_COUNT
/*0*/	itemDatabase.add(new Arrow	(SpriteDatabase.getArrowSpr(),			new Collider(-.02f, -.02f, .02f, .02f), 	(short)25,		(short)0, 		"Arrow",				"A basic arrow", 		Rarity.POOR,		(short)50,		(short)1,		(short)1,		(short)1, 		null));
/*1*/	itemDatabase.add(new Arrow	(SpriteDatabase.getLightningArrowSpr(),	new Collider(-.02f, -.02f, .02f, .02f),		(short)50,		(short)1,		"Lightning Arrow", 		"A lightning arrow",	Rarity.UNCOMMON,	(short)25,		(short)5,		(short)2,		(short)20, 		new Vector3f(1, 1, 1)));
		
		// WEAPON
		//					TYPE				SPR						  ID	NAME		DESC				RARITY			  		MAX		  		VAL		  		DMG 	 		RNG			USE		  KNOC	  		 HAND_ROT
/*2*/	itemDatabase.add(new Bow	(SpriteDatabase.getBowSpr(),	(short)2,	"Bow",		"A simple bow",		Rarity.POOR,		(short)1,		(short)3, 		(short)3, 		(short)0, 		2,		(short)3, 		(short)20));
/*3*/	itemDatabase.add(new Sword	(SpriteDatabase.getSwordSpr(),	(short)3,	"Sword",	"A simple Sword",	Rarity.POOR,		(short)1,		(short)3, 		(short)5, 		(short)3, 		1,		(short)7, 		(short)-20));

		// AMMUNIITON
		//					TYPE				SPR				 							COL								  SPEED			  ID		NAME					DESC					RARITY					  MAX			  VAL			  DMG			  KNO 		COLOR						    	BOUND_COUNT
/*4*/	itemDatabase.add(new Arrow	(SpriteDatabase.getFrozenArrowSpr(),	new Collider(-.02f, -.02f, .02f, .02f),		(short)38,		(short)4,		"Frozen Arrow", 		"A frozen arrow",		Rarity.UNCOMMON,	(short)25,		(short)5,		(short)2,		(short)3, 		new Vector3f(.68f, .84f, .90f)));
/*5*/	itemDatabase.add(new Arrow	(SpriteDatabase.getBurningArrowSpr(),	new Collider(-.02f, -.02f, .02f, .02f),		(short)38,		(short)5,		"Burning Arrow", 		"A burning arrow",		Rarity.UNCOMMON,	(short)25,		(short)5,		(short)2,		(short)3, 		new Vector3f(.91f, .58f, .48f)));

		// WEAPON
		//					TYPE				SPR						  ID	NAME		DESC				RARITY			  		MAX		  		VAL		  		DMG 	 		RNG			USE		  KNOC	  		 HAND_ROT
/*6*/	itemDatabase.add(new Gun	(SpriteDatabase.getGunSpr(), 	(short)6, 	"Gun", 		"Basic gun", 		Rarity.COMMON, 		(short)1, 		(short)5, 		(short)5, 		(short)0,	 	1, 		(short)5, 		(short)20));
		
		//AMMUNIITON
		//					TYPE				SPR				 							COL								  SPEED			  ID		NAME					DESC					RARITY					  MAX			  VAL			  DMG			  KNO 		COLOR						    	BOUND_COUNT
/*7*/	itemDatabase.add(new Bullet	(SpriteDatabase.getBulletSpr(), 		new Collider(-.02f, -.02f, .02f, .02f), 	(short)75, 		(short)7, 		"Bullet", 				"A bullet", 			Rarity.COMMON, 		(short)100, 	(short)1, 		(short)2, 		(short)5, 		null, 								(byte) 0));

		// WEAPON
		//					TYPE				SPR						  ID	NAME		DESC				RARITY			  		MAX		  		VAL		  		DMG 	 		RNG			USE		  KNOC	  		 HAND_ROT
/*8*/	itemDatabase.add(new Staff	(SpriteDatabase.getStaffSpr(),	(short)8,	"Staff", 	"A staff", 			Rarity.RARE, 		(short)1, 		(short)50, 		(short)10, 		(short)0, 		5, 		(short)1, 		(short)-80));

		//AMMUNIITON
		//					TYPE				SPR				 							COL								  SPEED			  ID		NAME					DESC					RARITY					  MAX			  VAL			  DMG			  KNO 		COLOR						    	BOUND_COUNT
/*9*/	itemDatabase.add(new Orb	(SpriteDatabase.getAirOrbSpr(), 		new Collider(-.25f, -.25f, .25f, .25f), 	(short)5, 		(short)9, 		"Air Orb", 				"An air orb", 			Rarity.POOR, 		(short)1, 		(short)1, 		(short)5, 		(short)10, 		new Vector3f(1,1,1)));
/*10*/	itemDatabase.add(new Bullet	(SpriteDatabase.getBulletSpr(), 		new Collider(-.02f, -.02f, .02f, .02f), 	(short)75,	 	(short)10, 		"Another bullet", 		"An another bullet", 	Rarity.COMMON, 		(short)100, 	(short)1, 		(short)2, 		(short)5, 		new Vector3f(.7f, .13f, .13f),		(byte) 3));
		
	}
	
	/**
	 * Create the ItemDatabase from a JSON file
	 * @throws JSONException
	 * @throws IOException
	 */
	/*public static void createItemDatabase() throws JSONException, IOException {
		//
		double start = (double)System.currentTimeMillis();
		
		File file = new File("json/item.json");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		String str = bufferedReader.readLine();
		String tmp = bufferedReader.readLine();
		while(tmp != null) {
			str += tmp;
			tmp = bufferedReader.readLine();
		}
		bufferedReader.close();
		
		JSONArray arr = new JSONArray(str);
		
		for (int i = 0; i < arr.length(); i++) {
			itemDatabase.add(new Item(arr.getJSONObject(i).getInt("id"),
											arr.getJSONObject(i).getString("name"),
											arr.getJSONObject(i).getString("description"),
											Rarity.valueOf(arr.getJSONObject(i).getString("rarity")),
											arr.getJSONObject(i).getInt("maxStack"),
											arr.getJSONObject(i).getInt("value")));
		}
		//
		double end = (double)System.currentTimeMillis();
		System.out.print("Read : " + (end - start) + '\t');
		//
		double startWrite = (double)System.currentTimeMillis();
		
		File fileToWrite = new File("json/itemWrite.json");
		fileToWrite.createNewFile();
		FileWriter fileWriter = new FileWriter(fileToWrite.getAbsoluteFile());
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		JSONArray array = new JSONArray();
		for (Item item : itemDatabase) {
			JSONObject obj = new JSONObject();
			obj.put("id", item.id());
			obj.put("name", item.name());
			obj.put("description", item.description());
			obj.put("rarity", item.rarity());
			obj.put("maxStack", item.max_stack());
			obj.put("value", item.value());
			array.put(item.id(), obj);
		}
		bufferedWriter.write(array.toString());
		bufferedWriter.close();
		//
		double endWrite = (double)System.currentTimeMillis();
		System.out.println("Write : " + (endWrite - startWrite));
		
	}*/
	
	/**
	 * Return an item from the ItemDatabase with a certain name
	 * @param name The name you want to find
	 * @return the item with the specified name
	 */
	public static Item findItemName(String name) {
		for (Item item : itemDatabase) {
			if(item.getName().equals(name))
				return item;
		}
		return null;
	}
	
	/**
	 * Return a list which contain all the items of the ItemDatabase with a specified Rarity
	 * @param rarity The Rarity you want to filter
	 * @return a list of items with a Rarity filter from the ItemDatabase
	 */
	public static List<Item> findRarity(Rarity rarity) {
		List<Item> rarityList = new ArrayList<Item>();
		for (Item item : itemDatabase) {
			if(item.getRarity() == rarity)
				rarityList.add(item);
		}
		return rarityList;
	}
	
	/**
	 * Get an item from the itemDatabase
	 * @param index The index of the item
	 * @return A new instance of the item desired
	 */
	public static Item get(int index) {
		return itemDatabase.get(index).getClass().cast(itemDatabase.get(index)).copy();
	}
	
	public static Item get(int index, short stack) {
		Item item = get(index);
		item.changeStack((short) (stack-1));
		return item;
	}

	public static Item getItem(int index) {
		return itemDatabase.get(index).copy();
	}
}