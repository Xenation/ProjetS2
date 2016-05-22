package fr.iutvalence.info.dut.m2107.sound;

import static org.lwjgl.openal.AL10.*;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector2f;

public class Source {

	private int sourceId;
	
	public Source(){
		sourceId = alGenSources();
		alSource3f(sourceId, AL_POSITION, 0, 0, 0);
	}
	
	public Source(Vector2f pos){
		sourceId = alGenSources();
		alSource3f(sourceId, AL_POSITION, pos.x, pos.y, 0);
	}
	
	public void play(int buffer){
		stop();
		alSourcei(sourceId, AL10.AL_BUFFER, buffer);
		continuePlaying();
	}
	
	public void delete(){
		stop();
		alDeleteSources(sourceId);
	}
	
	public void setLooping(boolean loop){
		alSourcei(sourceId, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
	}
	
	public boolean isPLaying (){
		return alGetSourcei(sourceId, AL_SOURCE_STATE) == AL_PLAYING;
	}
	
	public void pause(){
		alSourcePause(sourceId);
	}
	
	public void stop(){
		alSourceStop(sourceId);
	}
	
	public void continuePlaying(){
		alSourcePlay(sourceId);
	}
}
