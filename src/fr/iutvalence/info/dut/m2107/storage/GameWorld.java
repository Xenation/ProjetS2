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
	public static final Player player = new Player();
	/**
	 * The camera
	 */
	public static final Camera camera = new Camera();
	/**
	 * The map of all chunks
	 */
	public static final ChunkMap chunkMap = new ChunkMap();
	/**
	 * The map of all layers
	 */
	public static final LayerMap layerMap = new LayerMap();
	
	/**
	 * Updates the camera and every entity and tiles of the world
	 */
	public static void update() {
		camera.update();
		layerMap.update();
		chunkMap.update();
	}
}
