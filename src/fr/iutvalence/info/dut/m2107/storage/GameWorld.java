package fr.iutvalence.info.dut.m2107.storage;

import fr.iutvalence.info.dut.m2107.entities.Camera;
import fr.iutvalence.info.dut.m2107.entities.Player;

/**
 * The GameWorld
 * contains the player, camera, chunkMap and layerMap
 * @author Xenation
 *
 */
public class GameWorld {
	
	/**
	 * The gravity modifier
	 */
	public static float modGravity = 2f;
	/**
	 * The strength of gravity
	 */
	public static float gravity = 9.81f * modGravity;
	
	/**
	 * The player
	 */
	public static Player player;
	/**
	 * The camera
	 */
	public static Camera camera;
	/**
	 * The map of all chunks
	 */
	public static ChunkMap chunkMap;
	/**
	 * The map of all layers of entities
	 */
	public static LayerMap layerMap;
	/**
	 * The map of all layers of gui elements
	 */
	public static LayerMap guiLayerMap;
	
	public static void init() {
		chunkMap = new ChunkMap();
		layerMap = new LayerMap();
		guiLayerMap = new LayerMap();
		guiLayerMap.addEmpty(1);
		
		camera = new Camera();
		
		player = new Player();
	}
	
	/**
	 * Updates the camera and every entity and tiles of the world
	 */
	public static void update() {
		camera.update();
		layerMap.update();
		chunkMap.update();
	}
}
