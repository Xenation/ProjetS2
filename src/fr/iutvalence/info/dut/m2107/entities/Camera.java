package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.render.Renderer;
import fr.iutvalence.info.dut.m2107.saving.WorldLoader;
import fr.iutvalence.info.dut.m2107.saving.WorldSaver;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.tiles.TileType;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

/**
 * A Camera with a position and rotation
 * @author Xenation
 *
 */
public class Camera {
	
	/**
	 * The position of the camera
	 */
	private Vector2f position;
	/**
	 * The roation of the camera
	 */
	private float rotation;
	
	private GUIText debugText;
	
	/**
	 * A new Camera at 0,0 and with a rotation of 0
	 */
	public Camera() {
		this.position = new Vector2f();
		this.rotation = 0;
		this.debugText = new GUIText("", 1, 0, 0, .5f, false);
		debugText.setColour(0, 1, 0);
	}
	
	/**
	 * Checks for inputs and moves the camera according to them
	 */
	public void update(GameWorld gameWorld) {
		
		gameWorld.getChunkMap().generateSurroundingChunks(-Renderer.UNITS_Y/2*DisplayManager.aspectRatio, Renderer.UNITS_Y/2*DisplayManager.aspectRatio, Renderer.UNITS_Y/2, -Renderer.UNITS_Y/2, position);
		
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)) {
			this.position.y += 20 * DisplayManager.deltaTime();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5)) {
			this.position.y -= 20 * DisplayManager.deltaTime();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6)) {
			this.position.x += 20 * DisplayManager.deltaTime();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4)) {
			this.position.x -= 20 * DisplayManager.deltaTime();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0)) {
			setPosition(0, 0);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_DIVIDE)) {
			WorldSaver.writeWorld(gameWorld);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_MULTIPLY)) {
			WorldLoader.loadWorld(gameWorld);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
			Display.setVSyncEnabled(true);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
			Display.setVSyncEnabled(false);
		}
		
		if (Mouse.isButtonDown(0)) {
			gameWorld.getChunkMap().addTile(new Tile(TileType.Dirt, Maths.fastFloor(getMouseWorldX()), Maths.fastFloor(getMouseWorldY())));
		}
		if (Mouse.isButtonDown(1)) {
			gameWorld.getChunkMap().removeTileAt(Maths.fastFloor(getMouseWorldX()), Maths.fastFloor(getMouseWorldY()));
		}
		
		debugText.updateText("Mouse: "+Maths.roundDecim(getMouseWorldX(), 3)+", "+Maths.roundDecim(getMouseWorldY(), 3));
		
	}
	
	/**
	 * Returns the position of the camera
	 * @return the position of the camera
	 */
	public Vector2f getPosition() {
		return position;
	}
	
	/**
	 * Changes the position vector of the camera
	 * @param position the new position vector of the camera
	 */
	public void setPosition(Vector2f position) {
		this.position.set(position);
	}
	
	/**
	 * Changes the components of the position vector of the camera
	 * @param x the new x component
	 * @param y the new y component
	 */
	public void setPosition(float x, float y) {
		this.position.set(x, y);
	}
	
	/**
	 * Increases the current position of the camera
	 * @param x the amount to add in x component
	 * @param y the amount to add in y component
	 */
	public void increasePosition(float x, float y) {
		this.position.x += x;
		this.position.y += y;
	}
	
	/**
	 * Returns the rotation of the camera
	 * @return the rotation of the camera
	 */
	public float getRotation() {
		return rotation;
	}
	
	/**
	 * Changes the rotation of the camera
	 * @param rotation the new rotation
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public float getMouseWorldX() {
		return this.position.x + (Mouse.getX() - Display.getWidth()/2) / ((float) Display.getHeight() / Renderer.UNITS_Y);
	}
	
	public float getMouseWorldY() {
		return this.position.y + (Mouse.getY() - Display.getHeight()/2) / ((float) Display.getHeight() / Renderer.UNITS_Y);
	}
	
}
