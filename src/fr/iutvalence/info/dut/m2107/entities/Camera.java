package fr.iutvalence.info.dut.m2107.entities;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.render.Renderer;
import fr.iutvalence.info.dut.m2107.saving.WorldLoader;
import fr.iutvalence.info.dut.m2107.saving.WorldSaver;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Vector2i;
import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.tiles.TileBuilder;
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
	
	private Vector2i drawStart;
	private Vector2i drawEnd;
	private boolean isSelecting = false;
	private boolean isRemoving = false;
	
	private Entity preview;
	
	/**
	 * A new Camera at 0,0 and with a rotation of 0
	 */
	public Camera() {
		this.position = new Vector2f();
		this.rotation = 0;
		this.debugText = new GUIText("", .8f, 0, 0, .5f, false);
		debugText.setColour(0, 1, 0);
		this.type = TileType.Dirt;
	}
	
	/**
	 * Checks for inputs and moves the camera according to them
	 */
	public void update() {
		
		if (this.preview == null) {
			Sprite spr = new Sprite("entities/selection", new Vector2f(1, 1));
			spr.setAlpha(0.5f);
			this.preview = new MovableEntity(new Vector2f(0, 0), spr);
			GameWorld.layerMap.getLayer(1).add(preview);
		}
		GameWorld.chunkMap.generateSurroundingChunks(Renderer.BOUNDARY_LEFT, Renderer.BOUNDARY_RIGHT, Renderer.BOUNDARY_TOP, Renderer.BOUNDARY_BOTTOM, position);
		
		//// Lerp to target
		if (target != null) {
			this.position.x = Maths.lerp(this.position.x, target.pos.x, 0.1f);
			this.position.y = Maths.lerp(this.position.y, target.pos.y, 0.1f);
		}
		
		//// Movement
		if (target == null) {
			if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
				this.position.y += 20 * DisplayManager.deltaTime();
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				this.position.y -= 20 * DisplayManager.deltaTime();
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				this.position.x += 20 * DisplayManager.deltaTime();
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
				this.position.x -= 20 * DisplayManager.deltaTime();
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0)) {
				setPosition(0, 0);
			}
		}
		
		//// Switches
		while(Keyboard.next()){
			if (Keyboard.getEventKeyState()) {
				//// Player target bind/unbind
				if (Keyboard.getEventKey() == Keyboard.KEY_TAB) {
					if (target == null) {
						target = GameWorld.player;
						GameWorld.player.isInControl = true;
					} else {
						target = null;
						GameWorld.player.isInControl = false;
					}
				}
				//// VSync
				if (Keyboard.getEventKey() == Keyboard.KEY_V) {
					if (DisplayManager.vSyncTracker) {
						DisplayManager.vSyncTracker = false;
					} else {
						DisplayManager.vSyncTracker = true;
					}
					Display.setVSyncEnabled(DisplayManager.vSyncTracker);
				}
				//// Load/Save
				if (Keyboard.getEventKey() == Keyboard.KEY_DIVIDE) {
					WorldSaver.writeWorld();
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_MULTIPLY) {
					WorldLoader.loadWorld();
				}
				//// Tile Choice
				if (Keyboard.getEventKey() == Keyboard.KEY_1) {
					this.type = TileType.Dirt;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_2) {
					this.type = TileType.Stone;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_3) {
					this.type = TileType.Grass;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_4) {
					this.type = TileType.Log;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_5) {
					this.type = TileType.Leaves;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_6) {
					this.type = TileType.Fader;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_7) {
					this.type = TileType.Spikes;
				}
			}
		}
		
		this.preview.pos.x = Maths.fastFloor(getMouseWorldX()) + Tile.TILE_SIZE/2;
		this.preview.pos.y = Maths.fastFloor(getMouseWorldY()) + Tile.TILE_SIZE/2;
		
		//// Drawing
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && (Mouse.isButtonDown(0) || Mouse.isButtonDown(1))) {
			if (drawStart == null) {
				if (Mouse.isButtonDown(0)) {
					isRemoving = false;
				} else if (Mouse.isButtonDown(1)) {
					isRemoving = true;
				}
				drawStart = new Vector2i(Maths.fastFloor(getMouseWorldX()), Maths.fastFloor(getMouseWorldY()));
				isSelecting = true;
			} else {
				drawEnd = new Vector2i(Maths.fastFloor(getMouseWorldX()), Maths.fastFloor(getMouseWorldY()));
				calculateSelectionCenter();
			}
		} else if (isSelecting) {
			isSelecting = false;
			if (!isRemoving) {
				GameWorld.chunkMap.fillZone(type, drawStart, drawEnd);
			} else {
				GameWorld.chunkMap.emptyZone(drawStart, drawEnd);
			}
			preview.setScale(1, 1);
			isRemoving = false;
			drawStart = null;
		}
		if (!isSelecting) {
			if (Mouse.isButtonDown(0)) {
				GameWorld.chunkMap.setTile(TileBuilder.buildTile(type, Maths.fastFloor(getMouseWorldX()), Maths.fastFloor(getMouseWorldY())));
			}
			if (Mouse.isButtonDown(1)) {
				GameWorld.chunkMap.removeTileAt(Maths.fastFloor(getMouseWorldX()), Maths.fastFloor(getMouseWorldY()));
			}
		}
		
		
		String updateStr = "Mouse: "+Maths.roundDecim(getMouseWorldX(), 3)+", "+Maths.roundDecim(getMouseWorldY(), 3) 
		+ "\nFPS: "+DisplayManager.getFPS()
		+ "\nVSync = "+DisplayManager.vSyncTracker
		+ "\nSelecting = "+isSelecting;
		
		Tile pointed = GameWorld.chunkMap.getTileAt(Maths.fastFloor(getMouseWorldX()), Maths.fastFloor(getMouseWorldY()));
		if (pointed != null) {
			updateStr += "\nTile:";
			ArrayList<String> stats = TileBuilder.getStats(pointed);
			for (String stat : stats) {
				updateStr += "\n"+stat;
			}
		}
		
		debugText.updateText(updateStr);
		
	}
	
	private void calculateSelectionCenter() {
		int absDifX = (int) Maths.fastAbs(drawEnd.x - drawStart.x);
		int absDifY = (int) Maths.fastAbs(drawEnd.y - drawStart.y);
		preview.setScale(absDifX + 1, absDifY + 1);
		float addX = 0;
		float addY = 0;
		if (drawStart.x <= drawEnd.x) {
			addX += 0.5f;
		}
		if (drawStart.y <= drawEnd.y) {
			addY += 0.5f;
		}
		if (absDifX % 2 == 0) {
			addX -= Tile.TILE_SIZE/2;
			if (drawStart.x > drawEnd.x) {
				addX += 0.5f;
			}
		} else if (drawStart.x > drawEnd.x) {
			addX -= 0.5f;
		}
		if (absDifY % 2 == 0) {
			addY -= Tile.TILE_SIZE/2;
			if (drawStart.y > drawEnd.y) {
				addY += 0.5f;
			}
		} else if (drawStart.y > drawEnd.y) {
			addY -= 0.5f;
		}
		this.preview.pos.x -= (drawEnd.x - drawStart.x)/2 + addX;
		this.preview.pos.y -= (drawEnd.y - drawStart.y)/2 + addY;
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
		return this.position.x + (Mouse.getX() - Display.getDisplayMode().getWidth()/2) / ((float) Display.getDisplayMode().getHeight() / Renderer.UNITS_Y);
	}
	
	public float getMouseWorldY() {
		return this.position.y + (Mouse.getY() - Display.getDisplayMode().getHeight()/2) / ((float) Display.getDisplayMode().getHeight() / Renderer.UNITS_Y);
	}
	
}
