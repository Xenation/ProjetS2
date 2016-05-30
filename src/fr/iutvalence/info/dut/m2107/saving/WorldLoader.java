package fr.iutvalence.info.dut.m2107.saving;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.tiles.TileBuilder;
import fr.iutvalence.info.dut.m2107.tiles.TileOrientation;
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
			long chunkMapSize = buffer.getLong();
			long backChunkMapSize = buffer.getLong();
			for (int i = 0; i < chunkMapSize && buffer.hasRemaining(); i++) {
				byte t = buffer.get();
				byte v = buffer.get();
				byte o = buffer.get();
				int x = buffer.getInt();
				int y = buffer.getInt();
				
				typ = TileType.getTypeById(t);
				Tile tile = TileBuilder.buildTile(typ, x, y);
				if (v != 0)
					tile.setVariantUnsafe(TileVariant.getVariantById(v));
				tile.setOrientation(TileOrientation.getOrientationById(o));
				GameWorld.chunkMap.setTilenChunk(tile);
			}
			for (int i = 0; i < backChunkMapSize && buffer.hasRemaining(); i++) {
				byte t = buffer.get();
				byte v = buffer.get();
				byte o = buffer.get();
				int x = buffer.getInt();
				int y = buffer.getInt();
				
				typ = TileType.getTypeById(t);
				Tile tile = TileBuilder.buildTile(typ, x, y);
				if (v != 0)
					tile.setVariantUnsafe(TileVariant.getVariantById(v));
				tile.setOrientation(TileOrientation.getOrientationById(o));
				GameWorld.backChunkMap.setTilenChunk(tile);
			}
			
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Failed to close stream");
				return;
			}
		}
		
		System.out.println("World Loaded ("+file.length()/WorldSaver.tileByteSize+" tiles) in: "+(System.currentTimeMillis()-start)+"ms");
		
	}
}
