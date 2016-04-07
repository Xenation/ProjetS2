package com.github.eagl.enginetest;

import org.lwjgl.opengl.Display;

import com.github.eagl.models.TileSprite;
import com.github.eagl.render.*;

public class MainGameTester {
	
	public static void main(String[] args) {
		// Display Window initialization
		DisplayManager.createDisplay();
		
		Renderer renderer = new Renderer();
		//Loader loader = new Loader();
		
		TileSprite tilSpr = new TileSprite("tile_dirt");
		
		// Game Loop
		while (!Display.isCloseRequested()) {
			renderer.prepare();
			renderer.render(tilSpr);
			
			DisplayManager.updateDisplay();
		}
		
		DisplayManager.closeDisplay();
		
	}
	
}
