package fr.iutvalence.info.dut.m2107.enginetest;

import java.io.PrintStream;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.core.GameManager;
import fr.iutvalence.info.dut.m2107.render.*;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;
import fr.iutvalence.info.dut.m2107.toolbox.TrackingPrintStream;

public class MainGameTester {
	
	private static float addScale = 0.002f;
	private static Vector2f titleScale = new Vector2f(1, 1);
	
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
			
			if(GameManager.mainMenu.text_title != null) {
				Vector2f scale = GameManager.mainMenu.text_title.getScale();
				if(titleScale.x > 1.15f || titleScale.x < .85f) addScale *= -1;
				titleScale.x += addScale;
				titleScale.y += addScale;
				scale.x = Maths.lerp(scale.x, titleScale.x, .01f);
				scale.y = Maths.lerp(scale.y, titleScale.y, .01f);
			}
		}
		GameManager.cleanUp();
		DisplayManager.closeDisplay();
		
	}
	
}
