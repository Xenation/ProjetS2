package fr.iutvalence.info.dut.m2107.sound;

public class AudioDataBase {
	
	public static int arrow() {
		return OpenAL.loadSound("arrow");
	}
	
	public static int sword() {
		return OpenAL.loadSound("sword" + (int) (Math.random()*2));
	}
	
}
