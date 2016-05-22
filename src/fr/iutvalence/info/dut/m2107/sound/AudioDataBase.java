package fr.iutvalence.info.dut.m2107.sound;

import java.io.FileNotFoundException;

public class AudioDataBase {
	
	public static int arrow() throws FileNotFoundException{
		return OpenAL.loadSound("arrow");
	}
	
	public static int sword() throws FileNotFoundException{
		return OpenAL.loadSound("sword" + (int) (Math.random()*2));
	}
	
}
