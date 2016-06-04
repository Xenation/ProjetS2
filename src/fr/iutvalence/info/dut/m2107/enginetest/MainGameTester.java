package fr.iutvalence.info.dut.m2107.enginetest;

import java.io.PrintStream;

import org.lwjgl.opengl.Display;

import fr.iutvalence.info.dut.m2107.core.GameManager;
import fr.iutvalence.info.dut.m2107.render.*;
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
			
			GameManager.render();
			
			DisplayManager.updateDisplay();
		}
		GameManager.cleanUp();
		DisplayManager.closeDisplay();
		
	}
	
}
