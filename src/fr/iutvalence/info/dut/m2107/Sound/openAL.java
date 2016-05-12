package fr.iutvalence.info.dut.m2107.Sound;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

import static org.lwjgl.openal.AL10.*;

import org.newdawn.slick.openal.WaveData;

import fr.iutvalence.info.dut.m2107.storage.Input;

public class openAL {

	static int source;
	static int buffer;

	public static void init() {
		try {
			AL.create();
		} catch (LWJGLException e1) {
			e1.printStackTrace();
		}
		InputStream audioSrc = null;
		try {
			audioSrc = new FileInputStream("res/audio/audio.wav");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		//add buffer for mark/reset support
		InputStream bufferedIn = new BufferedInputStream(audioSrc);
		AudioInputStream audioStream = null;
		try {
			audioStream = AudioSystem.getAudioInputStream(bufferedIn);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		WaveData data = WaveData.create(audioStream);
		buffer = alGenBuffers();
		alBufferData(buffer, data.format, data.data, data.samplerate);
		data.dispose();
		source = alGenSources();
		alSourcei(source, AL_BUFFER, buffer);
	}
	
	public static void update() {
		if (Input.isJumping())
			alSourcePlay(source);
	}
	
	public static void delete() {
		alDeleteBuffers(buffer);
		AL.destroy();
	}
	
}