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

public class WorldSaver {
	
	private static File file;
	
	public static void setFilePath(String path) {
		file = new File(path);
	}
	
	public static void writeWorld(GameWorld gameWorld) {
		
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
			
			ByteBuffer buffer = BufferUtils.createByteBuffer(gameWorld.getChunkMap().getTilesCount()*9);
			buffer.order(ByteOrder.BIG_ENDIAN);
			buffer.rewind();
			for (Chunk chk : gameWorld.getChunkMap()) {
				for (Tile tile : chk) {
					switch (tile.getType()) {
					case Dirt:
						buffer.put((byte) 1);
						break;
					case Stone:
						buffer.put((byte) 2);
						break;
					default:
						buffer.put((byte) 0);
						break;
					}
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
		
		System.out.println("World Saved ("+gameWorld.getChunkMap().getTilesCount()+" tiles) in: "+(System.currentTimeMillis()-start)+"ms");
		
	}
	
	public static void writeTest() {
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
			
			byte[] txt = {16, 17, 18};
			
			try {
				fs.write(txt);
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
	}
	
}
