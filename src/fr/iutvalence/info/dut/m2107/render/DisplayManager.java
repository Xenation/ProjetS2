package fr.iutvalence.info.dut.m2107.render;

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
	 * The default displayMode to be used
	 */
	private static final DisplayMode DEF_DISPLAYMODE = new DisplayMode(1280, 720);
	/**
	 * the maximum number of frames per second
	 */
	private static int fpsCap = Integer.MAX_VALUE;
	
	/**
	 * the aspect ratio of the screen at initialization
	 */
	public static float aspectRatio;
	
	/**
	 * the absolute time at which the last frame was loaded
	 */
	private static long lastFrameTime;
	/**
	 * the delta time
	 */
	private static float delta;
	
	/**
	 * the last time the fps counter has been display
	 */
	private static long lastFPS;
	
	/**
	 * the number of frame per second display on the screen
	 */
	private static int fps;
	
	/**
	 * Creates a new display window
	 */
	public static void createDisplay() {
		// Display creation
		try {
			
			DisplayMode finalMode = DEF_DISPLAYMODE;
			
			// Temporary Code to put the display in full screen
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			
			for (int i = 0; i < modes.length; i++) {
				DisplayMode current = modes[i];
				if (current.getWidth() >= finalMode.getWidth() && current.getHeight() >= finalMode.getHeight() && current.getBitsPerPixel() >= finalMode.getBitsPerPixel()) {
					finalMode = current;
				}
			}
			
			System.out.println(finalMode.getWidth() + "x" + finalMode.getHeight() + "x" + finalMode.getBitsPerPixel() + " " + finalMode.getFrequency() + "Hz");
			
			fpsCap = finalMode.getFrequency();
			aspectRatio = ((float) finalMode.getWidth() /(float) finalMode.getHeight());
			
			Display.setDisplayModeAndFullscreen(finalMode);
			Display.setVSyncEnabled(true);
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

		updateDelta();
		lastFPS = getCurrentTime();
	}
	
	/**
	 * Updates the display (delta time also)
	 */
	public static void updateDisplay() {
		updateDelta();
		updateFPS();
		
		// FPS syncing and screen update
		Display.update();		
		Display.sync(fpsCap);
	}
	
	/**
	 * Updates delta
	 */
    public static void updateDelta() {
		long currentFrameTime = getCurrentTime();
		delta = currentFrameTime - lastFrameTime;
		lastFrameTime = currentFrameTime;
	}

    /**
     * Updates the number of frame per second
     */
	public static void updateFPS() {
		if (getCurrentTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }
	
	/**
	 * Returns the delta time meaning the amount of time the current frame took to be loader
	 * @return the current delta time
	 */
	public static float deltaTime() {
		return (delta/1000);
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
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
}
