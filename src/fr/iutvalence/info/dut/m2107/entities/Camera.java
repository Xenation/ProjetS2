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
	 * The rotation of the camera
	 */
	private float rotation;
	
	private Entity target;
	
	private GUIText debugText;
	
	private TileType type;
	
	/**
	 * A new Camera at 0,0 and with a rotation of 0
	 */
	public Camera() {
		this.position = new Vector2f();
		this.rotation = 0;
		this.debugText = new GUIText("", 1, 0, 0, .5f, false);
		debugText.setColour(0, 1, 0);
		this.type = TileType.Dirt;
	}
	
	/**
	 * Checks for inputs and moves the camera according to them
	 */
	public void update() {
		

		GameWorld.chunkMap.generateSurroundingChunks(Renderer.BOUNDARY_LEFT, Renderer.BOUNDARY_RIGHT, Renderer.BOUNDARY_TOP, Renderer.BOUNDARY_BOTTOM, position);

		this.position.x = Maths.lerp(this.position.x, target.pos.x, 0.1f);
		this.position.y = Maths.lerp(this.position.y, target.pos.y, 0.1f);
		
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			this.position.y += 20 * DisplayManager.deltaTime();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			this.position.y -= 20 * DisplayManager.deltaTime();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			this.position.x += 20 * DisplayManager.deltaTime();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			this.position.x -= 20 * DisplayManager.deltaTime();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0)) {
			setPosition(0, 0);
		}
		
		//// Load/Save
		if (Keyboard.isKeyDown(Keyboard.KEY_DIVIDE)) {
			WorldSaver.writeWorld();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_MULTIPLY)) {
			WorldLoader.loadWorld();
		}
		
		//// Vsync ON/OFF
		if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
			Display.setVSyncEnabled(true);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
			Display.setVSyncEnabled(false);
		}
		
		//// Drawing
		if (Mouse.isButtonDown(0)) {
			GameWorld.chunkMap.setTile(new Tile(TileType.Dirt, Maths.fastFloor(getMouseWorldX()), Maths.fastFloor(getMouseWorldY())));
		}
		if (Mouse.isButtonDown(1)) {
			GameWorld.chunkMap.removeTileAt(Maths.fastFloor(getMouseWorldX()), Maths.fastFloor(getMouseWorldY()));
		}
		
		//// Tile Choice
		if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
			this.type = TileType.Dirt;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
			this.type = TileType.Stone;
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
	
	public void setTarget(Entity target) {
		this.target = target;
	}
	
	public float getMouseWorldX() {
		return this.position.x + (Mouse.getX() - Display.getWidth()/2) / ((float) Display.getHeight() / Renderer.UNITS_Y);
	}
	
	public float getMouseWorldY() {
		return this.position.y + (Mouse.getY() - Display.getHeight()/2) / ((float) Display.getHeight() / Renderer.UNITS_Y);
	}
	
}
