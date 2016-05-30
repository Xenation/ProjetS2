package fr.iutvalence.info.dut.m2107.saving;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.lwjgl.BufferUtils;

import fr.iutvalence.info.dut.m2107.storage.Chunk;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.tiles.Tile;

/**
 * Used to Save a world.
 * Only chunkMap for now.
 * @author Xenation
 *
 */
public class WorldSaver {
	
	/**
	 * The save file
	 */
	private static File file;
	
	/**
	 * The size of a tile in the save file
	 */
	public static final int tileByteSize = 11;
	
	public static final int propertiesCount = 5;
	
	/**
	 * Sets the path to the new save file
	 * @param path the path to the new save file
	 */
	public static void setFilePath(String path) {
		file = new File(path);
	}
	
	/**
	 * Writes the save file with the data from the chunkMap of GameWorld
	 */
	public static void writeWorld() {
		
		double start = System.currentTimeMillis()/1000;
		
		if (file != null) {
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("Failed to create file");
					return;
				}
			}
			FileOutputStream fs;
			try {
				fs = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.err.println("Failed to create output stream");
				return;
			}
			
			ByteBuffer buffer = BufferUtils.createByteBuffer(GameWorld.chunkMap.getTilesCount()*tileByteSize + GameWorld.backChunkMap.getTilesCount()*tileByteSize + Long.BYTES*2);
			buffer.order(ByteOrder.BIG_ENDIAN);
			buffer.rewind();
			buffer.putLong(GameWorld.chunkMap.getTilesCount());
			buffer.putLong(GameWorld.backChunkMap.getTilesCount());
			for (Chunk chk : GameWorld.chunkMap) {
				for (Tile tile : chk) {
					buffer.put(tile.getType().getId());
					buffer.put(tile.getVariant().id);
					buffer.put(tile.getOrientation().getId());
					buffer.putInt(tile.x);
					buffer.putInt(tile.y);
				}
			}
			for (Chunk chk : GameWorld.backChunkMap) {
				for (Tile tile : chk) {
					buffer.put(tile.getType().getId());
					buffer.put(tile.getVariant().id);
					buffer.put(tile.getOrientation().getId());
					buffer.putInt(tile.x);
					buffer.putInt(tile.y);
				}
			}
			
			buffer.flip();
			
			byte[] data = new byte[buffer.capacity()];
			for (int i = 0; i < data.length; i++) {
				data[i] = buffer.get();
			}
			
			try {
				fs.write(data);
			} catch (IOException e1) {
				e1.printStackTrace();
				System.err.println("Failed to write to file");
			}
			
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Failed to close strem");
				return;
			}
		}
		
		System.out.println("World Saved ("+GameWorld.chunkMap.getTilesCount()+" tiles) in: "+(System.currentTimeMillis()-start)+"ms");
		
	}
	
}
