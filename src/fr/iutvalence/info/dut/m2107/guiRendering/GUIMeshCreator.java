package fr.iutvalence.info.dut.m2107.guiRendering;

import fr.iutvalence.info.dut.m2107.render.DisplayManager;

public class GUIMeshCreator {
	
	public static final float[] QUAD_POS = {
			-.5f, .5f,
			.5f, .5f,
			-.5f, -.5f,
			.5f, -.5f
	};
	public static final float[] QUAD_UVs = {
			0, 0,
			1, 0,
			0, 1,
			1, 1
	};
	
	/**
	 * Texture UVs for a frame of 16x16 pixels
	 */
	public static final float[] FRAME_UVs = {
			//TOP-LEFT
			0,		0,
			7f/16f,	0,
			0,		7f/16f,
			7f/16f,	7f/16f,
			
			//MID-LEFT
			0,		9f/16f,
			7f/16f,	9f/16f,
			
			//BOT-LEFT
			0,		1,
			7f/16f,	1,
			7f/16f, 9f/16f,
			
			//BOT-MID
			9f/16f,	1,
			9f/16f,	9f/16f,
			
			//BOT-RIGHT
			1,		1,
			1,		9f/16f,
			9f/16f,	9f/16f,
			
			//MID-RIGHT
			1,		7f/16f,
			9f/16f,	7f/16f,
			
			//TOP-RIGHT
			1,		0,
			9f/16f,	0,
			9f/16f,	7f/16f,
			
			//TOP-MID
			7f/16f, 0,
			7f/16f,	7f/16f,
			9f/16f,	7f/16f,
			
			//MID-MID
			7f/16f,	9f/16f,
			9f/16f,	9f/16f
	};
	
	public static GUIMeshData generateQuad(float width, float height) {
		float[] pos = new float[QUAD_POS.length];
		for (int i = 0; i < QUAD_POS.length; i++) {
			pos[i] = QUAD_POS[i]*width;
			i++;
			pos[i] = QUAD_POS[i]*height*DisplayManager.aspectRatio;
		}
		return new GUIMeshData(pos, QUAD_UVs.clone());
	}
	
	public static GUIMeshData generateFrame16(float anglesSize, float width, float height) {
		float mb = anglesSize;			//Middle Top
		float ml = anglesSize;			//Middle Left
		float mt = height-anglesSize;	//Middle Bottom
		float mr = width-anglesSize;	//Middle Right
		float t = height;
		float r = width;
		float[] pos = {
				//TOP-LEFT
				0,	t,
				ml,	t,
				0,	mt,
				ml,	mt,
				
				//MID-LEFT
				0,	mb,
				ml,	mb,
				
				//BOT-LEFT
				0,	0,
				ml, 0,
				ml,	mb,
				
				//BOT-MID
				mr,	0,
				mr,	mb,
				
				//BOT-RIGHT
				r,	0,
				r,	mb,
				mr,	mb,
				
				//MID-RIGHT
				r,	mt,
				mr,	mt,
				
				//TOP-RIGHT
				r,	t,
				mr,	t,
				mr,	mt,
				
				//TOP-MID
				ml,	t,
				ml,	mt,
				mr,	mt,
				
				//MID-MID
				ml,	mb,
				mr,	mb
		};
		return new GUIMeshData(pos, FRAME_UVs.clone());
	}
	
}
