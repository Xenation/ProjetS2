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
	 * The map of all background chunks
	 */
	public static ChunkMap backChunkMap;
	/**
	 * The map of all layers of entities
	 */
	public static LayerMap layerMap;
	/**
	 * The map of all layers of gui elements
	 * <ul>
	 *  <li><b>0: </b> Debug Text</li>
	 *  <li><b>1: </b> ?</li>
	 *  <li><b>2: </b> ?</li>
	 *  <li><b>3: </b> ?</li>
	 * </ul>
	 */
	public static GUILayerMap guiLayerMap;
	
	/**
	 * Initialises the chunk maps, layer maps, camera and player
	 */
	public static void init() {
		chunkMap = new ChunkMap();
		
		backChunkMap = new ChunkMap(-.5f);
		
		layerMap = new LayerMap();
		layerMap.addEmpty(4);
		
		guiLayerMap = new GUILayerMap();
		guiLayerMap.add(new GUILayer());
		guiLayerMap.add(new GUILayer());
		guiLayerMap.add(new GUILayer());
		guiLayerMap.add(new GUILayer());
		
		camera = new Camera();
		
		player = new Player();
	}
	
	/**
	 * Updates the camera and every entity and tiles of the world
	 */
	public static void update() {
		camera.update();
		layerMap.update();
		guiLayerMap.update();
		chunkMap.update();
	}
}
