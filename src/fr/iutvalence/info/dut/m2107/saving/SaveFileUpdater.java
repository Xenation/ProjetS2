package fr.iutvalence.info.dut.m2107.saving;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.lwjgl.BufferUtils;

public class SaveFileUpdater {
	
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
	 * Updates the save file from the given format to the current format.<br>
	 * The format is given using the properties present in the save file.
	 * <ul>
	 * 	<li><b>t</b> (byte): type</li>
	 * 	<li><b>v</b> (byte): variant</li>
	 * 	<li><b>o</b> (byte): orientation</li>
	 * 	<li><b>x</b> (int): pos x</li>
	 * 	<li><b>y</b> (int): pos y</li>
	 * </ul>
	 * @param savedProperties the properties of the file
	 * @param oldTileByteSize the size of a tile in bytes in the old save file
	 */
	public static void updateFileFormat(String savedProperties, int oldTileByteSize) {
		System.out.println("Updating save file ...");
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
			FileInputStream inFS;
			try {
				inFS = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.err.println("Failed to create input stream");
				return;
			}
			
			byte[] oldData = new byte[(int) file.length()];
			
			try {
				inFS.read(oldData);
			} catch (IOException e1) {
				e1.printStackTrace();
				System.err.println("Failed to read from file");
			}
			
			int missingFieldsCount = WorldSaver.propertiesCount - savedProperties.length();
			
			ByteBuffer oldBuffer = ByteBuffer.wrap(oldData);
			ByteBuffer newBuffer = BufferUtils.createByteBuffer(oldBuffer.capacity() + missingFieldsCount*(oldBuffer.capacity()/oldTileByteSize));
			newBuffer.order(ByteOrder.BIG_ENDIAN);
			newBuffer.rewind();
			
			while (oldBuffer.hasRemaining()) {
				byte t = oldBuffer.get();
				newBuffer.put(t);
				byte v = 0;
				if (savedProperties.contains("v"))
					v = oldBuffer.get();
				newBuffer.put(v);
				byte o = 1;
				if (savedProperties.contains("o"))
					o = oldBuffer.get();
				newBuffer.put(o);
				int x = oldBuffer.getInt();
				newBuffer.putInt(x);
				int y = oldBuffer.getInt();
				newBuffer.putInt(y);
				
				System.out.println("t"+t+" v"+v+" o"+o+" x"+x+" y"+y);
			}
			
			newBuffer.flip();
			
			FileOutputStream outFS;
			try {
				outFS = new FileOutputStream(file);
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
				System.err.println("Failed to create output stream");
				try {
					inFS.close();
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("Failed to close input stream");
				}
				return;
			}

			byte[] newData = new byte[newBuffer.capacity()];
			for (int i = 0; i < newData.length; i++) {
				newData[i] = newBuffer.get();
			}
			
			try {
				outFS.write(newData);
			} catch (IOException e1) {
				e1.printStackTrace();
				System.err.println("Failed to write to file");
			}
			
			try {
				inFS.close();
				outFS.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Failed to close streams");
				return;
			}
		}
		
		System.out.println("Save File Updated ("+file.length()/WorldSaver.tileByteSize+" tiles) in: "+(System.currentTimeMillis()-start)+"ms");
		
	}
	
}
