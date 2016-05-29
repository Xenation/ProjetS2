package fr.iutvalence.info.dut.m2107.sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.openal.AL10.*;

import org.newdawn.slick.openal.WaveData;

public class OpenAL {

	public static Source source;
	
	private static List<Integer> buffers = new ArrayList<Integer>();

	public static void init() {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		source = new Source();
	}
	
	
	public static void delete() {
		source.delete();
		for (int buffer : buffers)	
			alDeleteBuffers(buffer);
		AL.destroy();
	}
	
	public static int loadSound (String file) {
		int buffer = alGenBuffers();
		buffers.add(buffer);
		BufferedInputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(new File("res/audio/" + file + ".wav")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		WaveData waveFile = WaveData.create(is);
		alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();
		return buffer;
	}
	
	public static void setListenerPosition(float x, float y){
		alListener3f(AL_POSITION, x, y, 0);
	}
	
	public static void setListenerPosition(Vector2f pos){
		setListenerPosition(pos.x ,pos.y);
	}
}