package fr.iutvalence.info.dut.m2107.junit;

import fr.iutvalence.info.dut.m2107.inventory.Arrow;
import fr.iutvalence.info.dut.m2107.inventory.Inventory;
import fr.iutvalence.info.dut.m2107.inventory.ItemDatabase;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class InventoryTest extends TestCase {
	
	public InventoryTest(String name) {
		super(name);
	}
	
	public void testAdd() {
		System.out.println("TestAdd :");
		Inventory inventory = new Inventory();
		
		System.out.println("Expected : true , Actual : " + inventory.add(ItemDatabase.get(0), (short) 1));
		System.out.println("Expected : true , Actual : " + inventory.add(ItemDatabase.get(0), (short)49));
		System.out.println("Expected : false , Actual : " + inventory.add(ItemDatabase.get(0), (short) 1));
		System.out.println("Expected : true , Actual : " + inventory.add(ItemDatabase.get(2), (short) 1));
		System.out.println("Expected : false , Actual : " + inventory.add(ItemDatabase.get(2), (short) 1));
		
		System.out.println();
	}
	
	public void testRemove() {
		System.out.println("TestRemove : ");
		Inventory inventory = new Inventory();
		
		inventory.add(ItemDatabase.get(0), (short) 1);
		System.out.println("Expected : true , Actual : " + inventory.remove(ItemDatabase.get(0), (short)1));
		System.out.println("Expected : false , Actual : " + inventory.remove(ItemDatabase.get(0), (short)1));
		
		inventory.add(ItemDatabase.get(1), (short) 25);
		System.out.println("Expected : true , Actual : " + inventory.remove(ItemDatabase.get(1), (short)20));
		System.out.println("Expected : false , Actual : " + inventory.remove(ItemDatabase.get(1), (short)20));
		
		System.out.println();
	}
	
	public void testArrow() {
		System.out.println("TestArrow : ");
		Inventory inventory = new Inventory();
		
		Arrow arrow;
		System.out.println("Expected : null , Actual : " + ((arrow = inventory.getArrow()) == null ? "null" : arrow.getClass().getSimpleName()));
		
		inventory.add(ItemDatabase.get(2), (short) 10);
		System.out.println("Expected : null , Actual : " + ((arrow = inventory.getArrow()) == null ? "null" : arrow.getClass().getSimpleName()));
		
		inventory.add(ItemDatabase.get(0), (short) 10);
		System.out.println("Expected : Arrow , Actual : " + ((arrow = inventory.getArrow()) == null ? "null" : arrow.getClass().getSimpleName()));
		
		System.out.println();
	}
	
	public static Test suite() {
		TestSuite suite = new TestSuite();
		
		suite.addTest(new InventoryTest("testAdd"));
		suite.addTest(new InventoryTest("testRemove"));
		suite.addTest(new InventoryTest("testArrow"));
		
		return suite;
	}
	
}
