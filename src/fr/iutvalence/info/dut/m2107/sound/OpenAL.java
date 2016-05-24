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
		} catch (LWJGLException e1) {
			e1.printStackTrace();
		}
		try {
			int arrow = OpenAL.loadSound("arrow");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		source = new Source();
//		InputStream audioSrc = null;
//		try {
//			audioSrc = new FileInputStream("res/audio/arrow.wav");
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//		}
//		//add buffer for mark/reset support
//		InputStream bufferedIn = new BufferedInputStream(audioSrc);
//		AudioInputStream audioStream = null;
//		try {
//			audioStream = AudioSystem.getAudioInputStream(bufferedIn);
//		} catch (UnsupportedAudioFileException | IOException e) {
//			e.printStackTrace();
//		}
//		WaveData data = WaveData.create(audioStream);
//		buffer = alGenBuffers();
//		alBufferData(buffer, data.format, data.data, data.samplerate);
//		data.dispose();
//		source = alGenSources();
//		alSourcei(source, AL_BUFFER, buffer);
	}
	
	
	public static void delete() {
		source.delete();
		for (int buffer : buffers)	
			alDeleteBuffers(buffer);
		AL.destroy();
	}
	
	public static int loadSound (String file) throws FileNotFoundException{
		int buffer = alGenBuffers();
		buffers.add(buffer);
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File("res/audio/" + file + ".wav")));
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