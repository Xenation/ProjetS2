package fr.iutvalence.info.dut.m2107.models;

public abstract class AtlasSprite {
	
	protected final Atlas atlas;

	protected int vaoID;
	
	protected int atlasIndex;
	
	public AtlasSprite(Atlas atlas, int atlasIndex, int vaoID) {
		this.atlas = atlas;
		this.atlasIndex = atlasIndex;
		this.vaoID = vaoID;
	}
	
	public AtlasSprite(Atlas atlas, int atlasIndex) {
		this.atlas = atlas;
		this.atlasIndex = atlasIndex;
		this.vaoID = 0;
	}
	
	public int getAtlasIndex() {
		return atlasIndex;
	}
	
	public abstract void updateAtlasIndex(int atlasIndex);

	public Atlas getAtlas() {
		return atlas;
	}

	public int getVaoID() {
		return vaoID;
	}
	
	public int getTextureID() {
		return atlas.getTextureID();
	}
	
}
