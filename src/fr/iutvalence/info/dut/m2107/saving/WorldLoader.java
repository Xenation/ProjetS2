package fr.iutvalence.info.dut.m2107.saving;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.tiles.TileBuilder;
import fr.iutvalence.info.dut.m2107.tiles.TileType;
import fr.iutvalence.info.dut.m2107.tiles.TileVariant;

/**
 * Used to load data from a save file to the world
 * @author Xenation
 *
 */
public class WorldLoader {
	
	/**
	 * The file from which to load data
	 */
	private static File file;
	
	/**
	 * Sets the path of the new save file
	 * @param path the path of the new save file
	 */
	public static void setFilePath(String path) {
		file = new File(path);
	}
	
	/**
	 * Loads the data from the save file to the world
	 */
	public static void loadWorld() {
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
			
			ByteBuffer buffer = ByteBuffer.wrap(data);
			
			TileType typ = TileType.Dirt;
			while (buffer.hasRemaining()) {
				byte t = buffer.get();
				byte v = buffer.get();
				int x = buffer.getInt();
				int y = buffer.getInt();
				typ = TileType.getTypeById(t);
				Tile tile = TileBuilder.buildTile(typ, x, y);
				tile.setVariantUnsafe(TileVariant.getVariantById(v));
				GameWorld.chunkMap.setTilenChunk(tile);
			}
			
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Failed to close strem");
				return;
			}
		}
		
		System.out.println("World Loaded ("+file.length()/9+" tiles) in: "+(System.currentTimeMillis()-start)+"ms");
		
	}
}
