package com.github.eagl.render;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * Static class which controls the Display
 * @author Xenation
 *
 */
public class DisplayManager {
	
	/**
	 * the width of the screen
	 */
	private static final int WIDTH = 1280;
	/**
	 * the height of the screen
	 */
	private static final int HEIGHT = 720;
	/**
	 * the maximum number of frames per second
	 */
	private static final int FPS_CAP = 120;
	
	/**
	 * the aspect ratio of the screen at initialisation
	 */
	public static final float ASPECT_RATIO = ((float) WIDTH)/((float) HEIGHT);
	
	/**
	 * the absolute time at which the last frame was loaded
	 */
	private static long lastFrameTime;
	/**
	 * the delta time
	 */
	private static float delta;
	
	/**
	 * Creates a new display window
	 */
	public static void createDisplay() {
		
		// Display creation
		try {
			
			DisplayMode current = new DisplayMode(WIDTH, HEIGHT);
			
			// Temporary Code to put the display in fullscreen
			/*DisplayMode[] modes = Display.getAvailableDisplayModes();
			
			for (int i=0;i<modes.length;i++) {
				current = modes[i];
				System.out.println(current.getWidth() + "x" + current.getHeight() + "x" +
						current.getBitsPerPixel() + " " + current.getFrequency() + "Hz");
				if (current.getWidth() == 1920 && current.getHeight() == 1080 && current.getBitsPerPixel() == 32) {
					break;
				}
			}*/
			
			Display.setDisplayModeAndFullscreen(current);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		// OpenGL Configuration
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		// delta time initialisation
		lastFrameTime = getCurrentTime();
	}
	
	/**
	 * Updates the display (delta time also)
	 */
	public static void updateDisplay() {
		// FPS syncing and screen update
		Display.sync(FPS_CAP);
		Display.update();
		
		// delta time update
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFrameTime;
		
		if (delta > 1) {
			delta = 0;
		}
		
		Display.setTitle("FPS: "+1/delta);
	}
	
	/**
	 * Returns the delta time meaning the amount of time the current frame took to be loader
	 * @return the current delta time
	 */
	public static float deltaTime() {
		return delta;
	}
	
	/**
	 * Closes the display window
	 */
	public static void closeDisplay() {
		Display.destroy();
	}
	
	/**
	 * Returns the current time in milliseconds
	 * @return the current time in milliseconds
	 */
	private static long getCurrentTime() {
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
}
