package fr.iutvalence.info.dut.m2107.storage;

import fr.iutvalence.info.dut.m2107.entities.Camera;
import fr.iutvalence.info.dut.m2107.entities.Player;

public class GameWorld {
	
	public static float modGravity = 2f;
	public static float gravity = 9.81f * modGravity;
	
	public static final Player player = new Player();
	public static final Camera camera = new Camera();
	public static final ChunkMap chunkMap = new ChunkMap();
	public static final LayerMap layerMap = new LayerMap();
	
	public static void update() {
		layerMap.update();
		chunkMap.update();
		camera.update();
	}
}
