package fr.iutvalence.info.dut.m2107.models;

import org.newdawn.slick.opengl.Texture;

import fr.iutvalence.info.dut.m2107.render.Loader;

public class Atlas {
	
	public static final Atlas TILE_ATLAS = new Atlas("tile/atlas", 8, 32, Loader.TILE_LOADER);
	public static final Atlas TEXT_ATLAS = new Atlas("fonts/Pixel", 1, 1, Loader.TEXT_LOADER);
	
	private Texture texture;
	
	private int horizIndexLength;
	private int vertIndexLength;
	
	public Atlas(String fileName, int horizontalIndexLength, int verticalIndexLength, Loader loader) {
		this.texture = loader.loadAtlas(fileName);
		this.horizIndexLength = horizontalIndexLength;
		this.vertIndexLength = verticalIndexLength;
	}
	
	public float[] getUVs(int index) {
		
		int textureHeight = texture.getImageHeight();
		int textureWidth = texture.getImageWidth();
		
		int indexY = index / horizIndexLength;
		int indexX = index % horizIndexLength;
		
		int texHeight = textureHeight/vertIndexLength;
		int texWidth = textureWidth/horizIndexLength;
		
		float[] uvs = {
				toUVTexel(texWidth * indexX, textureWidth), toUVTexel(texHeight * indexY, textureHeight),
				toUVTexel((texWidth * (indexX+1))-1, textureWidth), toUVTexel(texHeight * indexY, textureHeight),
				toUVTexel((texWidth * (indexX+1))-1, textureWidth), toUVTexel((texHeight * (indexY+1))-1, textureHeight),
				toUVTexel(texWidth * indexX, textureWidth), toUVTexel((texHeight * (indexY+1))-1, textureHeight)
		};
		
		return uvs;
	}
	
	public static float toUVTexel(int pos, int size) {
		return (float) ((pos + 0.5) / size);
	}
	
	public int getTextureID() {
		return texture.getTextureID();
	}
	
}
