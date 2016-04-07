package com.github.eagl.toolbox;

public class Maths {
	
	public static float lerp(float a, float b, float d) {
		return (1-d)*a + d*b;
	}
	
	public static int fastFloor(float x) {
		int xi = (int) x;
		return x<xi ? xi-1 : xi;
	}
	
}
