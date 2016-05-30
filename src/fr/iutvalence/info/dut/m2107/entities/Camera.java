package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.render.Renderer;
import fr.iutvalence.info.dut.m2107.saving.WorldLoader;
import fr.iutvalence.info.dut.m2107.saving.WorldSaver;
import fr.iutvalence.info.dut.m2107.storage.ChunkMap;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Input;
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
	
	/**
	 * The target to follow
	 */
	private Entity target;
	
	/**
	 * The tile that is pointed by the mouse
	 */
	private Tile pointed;
	
	/**
	 * The type of tile to draw
	 */
	private TileType type;
	
	/**
	 * The starting position of the selection
	 */
	private Vector2i drawStart;
	/**
	 * The ending position of the selection
	 */
	private Vector2i drawEnd;
	/**
	 * Whether the selection is in progress (true) or not (false)
	 */
	private boolean isSelecting = false;
	/**
	 * Whether the selection in progress is a removal (true) or not (false)
	 */
	private boolean isRemoving = false;
	/**
	 * The chunk map to draw and debug on.
	 */
	private ChunkMap targetChunkMap;
	
	private Entity preview;
	
	private boolean isFree = true;
	
	/**
	 * A new Camera at 0,0 and with a rotation of 0
	 */
	public Camera() {
		this.position = new Vector2f();
		this.rotation = 0;
		this.type = TileType.Dirt;
		this.targetChunkMap = GameWorld.chunkMap;
	}
	
	/**
	 * Updates the state of the camera.
	 * Uses Inputs for movements when not bound to a target.
	 * When bound uses lerp to follow the target.
	 * Manages the position and scale of the preview entity.
	 * Generates the chunks in screen.
	 * Updates the debug display (fps, mouse pos, Vsync...)
	 * Inputs :
	 *  - ZQSD: movement
	 *  - Tab: bind/unbind player as target
	 *  - V: Vsync On/Off
	 *  - Divide(/): Save current chunkMap
	 *  - Multiply(*): Load saved chunkMap
	 *  - 1-0: Select TileType to draw
	 *  - LShift+Click (drag): Select a zone to draw(Left click) or delete (right click)
	 *  - Left click: draw a single tile of the selected tile type
	 *  - Right click: remove a single tile
	 */
	public void update() {
		
		float mWorldX = getMouseWorldX();
		float mWorldY = getMouseWorldY();
		
		if (this.preview == null) {
			EntitySprite spr = new EntitySprite("entities/selection", new Vector2f(1, 1));
			this.preview = new Entity(spr);
			this.preview.setAlpha(0.5f);
		}
		
		GameWorld.chunkMap.generateSurroundingChunks(Renderer.BOUNDARY_LEFT, Renderer.BOUNDARY_RIGHT, Renderer.BOUNDARY_TOP, Renderer.BOUNDARY_BOTTOM, position);
		GameWorld.backChunkMap.generateSurroundingChunks(Renderer.BOUNDARY_LEFT, Renderer.BOUNDARY_RIGHT, Renderer.BOUNDARY_TOP, Renderer.BOUNDARY_BOTTOM, position);
		
		//// Lerp to target
		if (target != null) {
			this.position.x = Maths.lerp(this.position.x, target.pos.x, 0.1f);
			this.position.y = Maths.lerp(this.position.y, target.pos.y, 0.1f);
		}
		
		//// Player target bind/unbind
		if (Input.isFocusOnPlayer()) {
			if (target == null) {
				target = GameWorld.player;
				this.isFree = true;
				GameWorld.layerMap.getLayer(1).remove(preview);
			} else {
				target = null;
				this.isFree = false;
				GameWorld.layerMap.getLayer(1).add(preview);
			}
		}
		
		pointed = targetChunkMap.getTileAt(mWorldX, mWorldY);
		
		//// Build Mode
		if (target == null && preview != null) {
			if (pointed != null && Input.isKeyU()) {
				pointed.toUpdate(true);
			}
			if (Input.isKeyB()) {
				if (targetChunkMap == GameWorld.chunkMap) {
					targetChunkMap = GameWorld.backChunkMap;
				} else {
					targetChunkMap = GameWorld.chunkMap;
				}
			}
			
			//// Movement
			if (Input.isMoveUp()) 	this.position.y += 20 * DisplayManager.deltaTime();
			if (Input.isMoveDown()) this.position.y -= 20 * DisplayManager.deltaTime();
			if (Input.isMoveRight())this.position.x += 20 * DisplayManager.deltaTime();
			if (Input.isMoveLeft())	this.position.x -= 20 * DisplayManager.deltaTime();
			if (Input.isNumPad0()) setPosition(0, 0);
			
			//// Tile Choice
			if (Input.isKey1())	this.type = TileType.Dirt;
			if (Input.isKey2())	this.type = TileType.Stone;
			if (Input.isKey3()) this.type = TileType.Grass;
			if (Input.isKey4()) this.type = TileType.Log;
			if (Input.isKey5())	this.type = TileType.Leaves;
			if (Input.isKey6())	this.type = TileType.Fader;
			if (Input.isKey7())	this.type = TileType.Spikes;
			if (Input.isKey8())	this.type = TileType.Sand;
			if (Input.isKey9())	this.type = TileType.Creator;
			if (Input.isKey0())	this.type = TileType.Piston;
			if (Input.isKeyWater()) this.type = TileType.Water;
			
			//// Load/Save
			if (Input.isWriteWorld()) WorldSaver.writeWorld();
			if (Input.isLoadWorld())  WorldLoader.loadWorld();
			
			//// Tile Rotation
			if (Input.isTileRotate() && pointed != null)
				targetChunkMap.rotateTileAt(pointed.x, pointed.y, pointed.getOrientation().getNext());
			
			this.preview.pos.x = targetChunkMap.toTileCenterVisualPosition(mWorldX) + Tile.TILE_SIZE/2;
			this.preview.pos.y = targetChunkMap.toTileCenterVisualPosition(mWorldY) + Tile.TILE_SIZE/2;
			
			//// Drawing
			if (Input.isLShift() && (Input.isMouseLeftDown() || Input.isMouseRightDown())) {
				if (drawStart == null) {
					if (Input.isMouseLeftDown()) isRemoving = false;
					else if (Input.isMouseRightDown()) isRemoving = true;
					
					drawStart = new Vector2i(targetChunkMap.toTilePosition(mWorldX), targetChunkMap.toTilePosition(mWorldY));
					isSelecting = true;
				} else {
					drawEnd = new Vector2i(targetChunkMap.toTilePosition(mWorldX), targetChunkMap.toTilePosition(mWorldY));
					calculateSelectionCenter();
				}
			} else if (isSelecting) {
				isSelecting = false;
				if (!isRemoving) {
					targetChunkMap.fillZone(type, drawStart, drawEnd);
				} else {
					targetChunkMap.emptyZone(drawStart, drawEnd);
				}
				preview.setScale(1, 1);
				isRemoving = false;
				drawStart = null;
			}
			if (!isSelecting) {
				if (Input.isMouseLeftDown()) {
					targetChunkMap.setTile(TileBuilder.buildTile(type, targetChunkMap.toTilePosition(mWorldX), targetChunkMap.toTilePosition(mWorldY)));
				}
				if (Input.isMouseRightDown()) {
					targetChunkMap.removeTileAt(targetChunkMap.toTilePosition(mWorldX), targetChunkMap.toTilePosition(mWorldY));
				}
			}
		} else {
//			if (Input.isNumPad0()) {
//				float pow = Maths.pow(2, 23);
//				this.target.pos = new Vector2f(pow, 0);
//				setPosition(pow, 0);
//				for (int i = -10; i <= 10; i++) {
//					GameWorld.chunkMap.setTilenChunk(TileBuilder.buildTile(type, Maths.fastFloor(pow+i), -20));
//				}
//			}
		}
		
		//// VSync
		if (Input.isVSync()) {
			if (DisplayManager.vSyncTracker) DisplayManager.vSyncTracker = false;
			else DisplayManager.vSyncTracker = true;
			
			Display.setVSyncEnabled(DisplayManager.vSyncTracker);
		}
		
	}
	
	/**
	 * Calcultes the position of the preview entity using drawStart and drawEnd
	 */
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
	
	/**
	 * Binds the camera to the target
	 * @param target the entity to follow
	 */
	public void setTarget(Entity target) {
		this.target = target;
	}
	
	public Tile getPointed() {return pointed;}
	
	/**
	 * Returns the X coordinate of the mouse in the world
	 * @return the X coordinate of the mouse in the world
	 */
	public float getMouseWorldX() {
		return this.position.x + (Mouse.getX() - Display.getDisplayMode().getWidth()/2) / ((float) Display.getDisplayMode().getHeight() / Renderer.UNITS_Y);
	}
	
	/**
	 * Returns the Y coordinate of the mouse in the world
	 * @return the Y coordinate of the mouse in the world
	 */
	public float getMouseWorldY() {
		return this.position.y + (Mouse.getY() - Display.getDisplayMode().getHeight()/2) / ((float) Display.getDisplayMode().getHeight() / Renderer.UNITS_Y);
	}
	
	/**
	 * Return whether the camera is free or not
	 * @return whether the camera is free or not
	 */
	public boolean isFree() {return isFree;}
	
	public boolean isSelecting() {return isSelecting;}
	
}
