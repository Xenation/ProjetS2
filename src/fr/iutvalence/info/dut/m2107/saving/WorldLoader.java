package fr.iutvalence.info.dut.m2107.saving;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import fr.iutvalence.info.dut.m2107.storage.Chunk;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.tiles.TileType;

public class WorldLoader {
	
	private static File file;
	
	public static void setFilePath(String path) {
		file = new File(path);
	}
	
	public static void loadWorld(GameWorld gameWorld) {
		double start = System.currentTimeMillis();
		
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
			FileInputStream fs;
			try {
				fs = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.err.println("Failed to create output stream");
				return;
			}
			
			byte[] data = new byte[(int) file.length()];
			
			try {
				fs.read(data);
			} catch (IOException e1) {
				e1.printStackTrace();
				System.err.println("Failed to read from file");
			}
			
			TileType typ = TileType.Dirt;
			for (int i = 0; i < data.length; i++) {
				switch (data[i++]) {
				case 1:
					typ = TileType.Dirt;
					break;
				case 2:
					typ = TileType.Stone;
					break;
				default:
					typ = TileType.Dirt;
					break;
				}
				gameWorld.getChunkMap().addTilenChunk(new Tile(typ, data[i], data[i+1]));
				i += 2;
			}
			
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Failed to close strem");
				return;
			}
		}
		
		System.out.println("World Loaded ("+file.length()/4+" tiles) in: "+(System.currentTimeMillis()-start)+"ms");
	}
}
