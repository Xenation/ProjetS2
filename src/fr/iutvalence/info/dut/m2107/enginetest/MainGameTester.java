package fr.iutvalence.info.dut.m2107.enginetest;

import java.io.PrintStream;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.entities.Rat;
import fr.iutvalence.info.dut.m2107.entities.SpriteDatabase;
import fr.iutvalence.info.dut.m2107.entities.Zombie;
import fr.iutvalence.info.dut.m2107.events.EventManager;
import fr.iutvalence.info.dut.m2107.events.ListenersScanner;
import fr.iutvalence.info.dut.m2107.gui.GUI;
import fr.iutvalence.info.dut.m2107.gui.GUIMaster;
import fr.iutvalence.info.dut.m2107.inventory.ItemDatabase;
import fr.iutvalence.info.dut.m2107.listeners.TileListener;
import fr.iutvalence.info.dut.m2107.render.*;
import fr.iutvalence.info.dut.m2107.saving.SaveFileUpdater;
import fr.iutvalence.info.dut.m2107.saving.WorldLoader;
import fr.iutvalence.info.dut.m2107.saving.WorldSaver;
import fr.iutvalence.info.dut.m2107.sound.OpenAL;
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
		
		// GUI Initialisation
		GUIMaster.init();
		
		OpenAL.init();
		
		// Database Initialisation
		ItemDatabase.create();
		
		Renderer renderer = new Renderer();
		
		// GameWorld Initialisation
		GameWorld.init();
		
		// Loader/Saver Initialisation
		WorldSaver.setFilePath("res/test.sav");
		WorldLoader.setFilePath("res/test.sav");
		SaveFileUpdater.setFilePath("res/test.sav");
		
		// World Loading
		WorldLoader.loadWorld();
		Collider chestCollider = new Collider(-SpriteDatabase.getChestSpr().getSize().x/2, -SpriteDatabase.getChestSpr().getSize().y/2, SpriteDatabase.getChestSpr().getSize().x/2, SpriteDatabase.getChestSpr().getSize().y/2 - 0.5f);
		Entity chest = new Entity(new Vector2f(6.5f, -3f), 0, SpriteDatabase.getChestSpr(), chestCollider);
		GameWorld.layerMap.getStoredLayer(LayerStore.DECORATION).add(chest);
		
		for (int i = 5; i < 100; i++) {
			GameWorld.layerMap.getStoredLayer(LayerStore.MOBS).add(new Rat(new Vector2f(i/2f, i/2f), SpriteDatabase.getRatSpr() , new Collider(-.25f, -.25f, .25f, .25f)));
		}
		
		// Events Initialisation
		EventManager.init();
		for (Class<?> cla : ListenersScanner.listenersClasses) {
			System.out.println("LISTENER: "+cla.getSimpleName());
		}
		
		EventManager.register(new TileListener());
		
		GUI gui = new GUI();
		
		DisplayManager.setStartTime((int)(Sys.getTime()/1000));
		
		// Game Loop
		while (!Display.isCloseRequested()) {
			Input.input();
			
			if(Input.isKeyWater() && GameWorld.camera.isFocusing())
				GameWorld.layerMap.getStoredLayer(LayerStore.MOBS).add(new Zombie(new Vector2f(0, 0), SpriteDatabase.getZombieSpr(), new Collider(-.5f, -1.55f, .5f, 1.55f)));
			
			GameWorld.update();
			
			gui.update();
			
			renderer.prepare();
			renderer.render();
			
			DisplayManager.updateDisplay();
		}
		OpenAL.delete();
		renderer.cleanUp();
		Loader.TILE_LOADER.unloadAll();
		Loader.SPRITE_LOADER.unloadAll();
		Loader.TEXT_LOADER.unloadAll();
		Loader.GUI_LOADER.unloadAll();
		DisplayManager.closeDisplay();
		
	}
	
}
