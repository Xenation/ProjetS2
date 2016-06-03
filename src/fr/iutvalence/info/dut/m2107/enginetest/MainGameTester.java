package fr.iutvalence.info.dut.m2107.enginetest;

import java.io.PrintStream;

import org.lwjgl.opengl.Display;

import fr.iutvalence.info.dut.m2107.core.GameManager;
import fr.iutvalence.info.dut.m2107.inventory.Item;
import fr.iutvalence.info.dut.m2107.inventory.ItemDatabase;
import fr.iutvalence.info.dut.m2107.render.*;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Input;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;
import fr.iutvalence.info.dut.m2107.toolbox.TrackingPrintStream;

public class MainGameTester {
	
	/**
	 * Launches the game (test version)
	 * arguments:
	 * <ul>
	 * <li>0: anything - won't create the window</li>
	 * </ul>
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.setOut(new TrackingPrintStream(new PrintStream(System.out)));
		
		// Display Window initialisation
		if (args.length == 0) {
			DisplayManager.createDisplay();
			DisplayManager.updateDisplay();
		}
		
		// Initialises the game components
		GameManager.init();
		
		// Main Menu Initialisation
		GameManager.loadMainMenu();
		
		// Game Loop
		while (!Display.isCloseRequested() && !GameManager.isQuitting) {
			GameManager.update();
			
			if(Input.isKeyWater() && GameWorld.camera.isFocusing()) {
				Item item = Item.copyDropableItem(ItemDatabase.get(0));
				GameWorld.layerMap.getStoredLayer(LayerStore.DECORATION).add(item);
				
			}
			//GameWorld.layerMap.getStoredLayer(LayerStore.MOBS).add(new Zombie(new Vector2f(0, 0), 0, SpriteDatabase.getZombieSpr(), new Collider(-.5f, -1.55f, .5f, 1.55f), new Vector2f(), (short) 2, 10, 0));
			
			GameManager.render();
			
			DisplayManager.updateDisplay();
		}
		GameManager.cleanUp();
		DisplayManager.closeDisplay();
		
	}
	
}
