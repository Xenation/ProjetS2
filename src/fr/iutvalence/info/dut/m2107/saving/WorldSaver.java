package fr.iutvalence.info.dut.m2107.saving;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
			
			byte[] data = new byte[gameWorld.getChunkMap().getTilesCount()*4];
			int index = 0;
			for (Chunk chk : gameWorld.getChunkMap()) {
				for (Tile tile : chk) {
					switch (tile.getType()) {
					case Dirt:
						data[index++] = 1;
						break;
					case Stone:
						data[index++] = 2;
						break;
					default:
						data[index++] = 0;
						break;
					}
					data[index++] = (byte) tile.x;
					data[index++] = (byte) tile.y;
					data[index++] = 11;
				}
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
