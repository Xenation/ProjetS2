package fr.iutvalence.info.dut.m2107.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import fr.iutvalence.info.dut.m2107.storage.Input;

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
	 * The time at the current frame
	 */
	private static long currentFrameTime;
	
	/**
	 * the delta time
	 */
	private static float delta;
	
	/**
	 * The deltas recorded during the last second linked to the time of their calculation
	 */
	private static Map<Long, Float> deltasMap = new HashMap<Long, Float>();
	
	/**
	 * A smooth version of the delta (average of deltasMap)
	 * Used to avoid the non-constant movements in-game
	 */
	private static float smoothDelta;
	
	/**
	 * the last time the fps counter has been display
	 */
	private static long lastFPS;
	
	/**
	 * the number of frame per second display on the screen
	 */
	private static int fps;
	
	/**
	 * The current fps (calculated every second)
	 */
	public static int currentFPS;
	
	/**
	 * Allows to track the activation of Vsync since there is no getVSyncEnabled() method in Display.
	 * Do not change until you use setVSyncEnable().
	 */
	public static boolean vSyncTracker = true;
	
	public static boolean isPaused = false;
	
	private static int startTime;

	/**
	 * Creates a new display window
	 */
	public static void createDisplay() {
		// Display creation
		try {
			
			DisplayMode finalMode = DEF_DISPLAYMODE;
			
			// Temporary Code to put the display in full screen
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			
			System.out.println("Available Displays:");
			for (int i = 0; i < modes.length; i++) {
				DisplayMode current = modes[i];
				System.out.println(current.getWidth()+"x"+current.getHeight()+" "+current.getFrequency());
				if (current.getWidth() >= finalMode.getWidth() && current.getHeight() >= finalMode.getHeight() && current.getBitsPerPixel() >= finalMode.getBitsPerPixel()) {
					finalMode = current;
				}
			}
			
			System.out.println("Selected Display:\n"+finalMode.getWidth() + "x" + finalMode.getHeight() + "x" + finalMode.getBitsPerPixel() + " " + finalMode.getFrequency() + "Hz\n");
			
			Display.setDisplayModeAndFullscreen(finalMode);
			aspectRatio = ((float) Display.getDisplayMode().getWidth() /(float) Display.getDisplayMode().getHeight());
			fpsCap = Display.getDisplayMode().getFrequency();
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
		for (int i = 0; i < 60; i++)
		{
			deltasMap.put(currentFrameTime, (float) fpsCap);
		}
		lastFPS = getCurrentTime();
	}
	
	public static void createDisplay(DisplayMode mode, boolean fullscreen, boolean vsync, int cap) {
		// Display creation
				try {
					if (fullscreen)
						Display.setDisplayModeAndFullscreen(mode);
					else
						Display.setDisplayMode(mode);
					aspectRatio = ((float) Display.getDisplayMode().getWidth() /(float) Display.getDisplayMode().getHeight());
					if (cap == 0)
						fpsCap = Display.getDisplayMode().getFrequency();
					else
						fpsCap = cap;
					vSyncTracker = vsync;
					Display.setVSyncEnabled(vsync);
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
				updateDeltaMap();
				lastFPS = getCurrentTime();
	}
	
	/**
	 * Updates the display (delta time also)
	 */
	public static void updateDisplay() {
		updateDelta();
		updateFPS();
		updateDeltaMap();
		
		if(Input.isEscape()) closeDisplay();
		
		isPaused = Input.isPaused();
		
		// FPS syncing and screen update
		Display.update();
		Display.sync(fpsCap);
	}
	
	/**
	 * Updates delta
	 */
    public static void updateDelta() {
		currentFrameTime = getCurrentTime();
		delta = currentFrameTime - lastFrameTime;
		lastFrameTime = currentFrameTime;
		deltasMap.put(currentFrameTime, delta);
	}
    
    /**
     * Updates the deltasMap and calculates smoothDelta
     */
	public static void updateDeltaMap() {
		List<Long> toRemove = new LinkedList<Long>(); 
		for (long time : deltasMap.keySet()) {
			if (time < currentFrameTime - 1000) {
				toRemove.add(time);
			}
		}
		for (long time : toRemove) {
			deltasMap.remove(time);
		}
		smoothDelta = 0;
		for (float delt : deltasMap.values()) {
			smoothDelta += delt;
		}
		smoothDelta /= deltasMap.size();
	}

    /**
     * Updates the number of frame per second
     */
	public static void updateFPS() {
		if (getCurrentTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            currentFPS = fps;
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
		if(isPaused || (int)(Sys.getTime()/1000) < startTime+1.5f) return 0;
		else return (smoothDelta/1000);
	}
	
	/**
	 * Closes the display window
	 */
	public static void closeDisplay() {
		Display.destroy();
		System.exit(0);
	}
	
	/**
	 * Returns the current FPS
	 * @return the current FPS
	 */
	public static int getFPS() {
		return currentFPS;
	}
	
	/**
	 * Returns a list of all available display modes
	 * @return a list of all available display modes
	 */
	public static List<DisplayMode> getDisplayModes() {
		DisplayMode[] modesArray = null;
		try {
			modesArray = Display.getAvailableDisplayModes();
		} catch (LWJGLException e) {
			return null;
		}
		List<DisplayMode> modes = new ArrayList<DisplayMode>();
		for (DisplayMode displayMode : modesArray) {
			modes.add(displayMode);
		}
		return modes;
	}
	
	/**
	 * Returns the current time in milliseconds
	 * @return the current time in milliseconds
	 */
	private static long getCurrentTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public static void setStartTime(int startTime) { DisplayManager.startTime = startTime; }
	
}
