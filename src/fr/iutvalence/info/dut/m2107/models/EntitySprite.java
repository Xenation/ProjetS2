package fr.iutvalence.info.dut.m2107.models;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.render.Loader;

public class EntitySprite extends AtlasSprite {
	
	protected Vector2f size;
	
	protected final float[] positions;
	
	public EntitySprite(Atlas atlas, int atlasIndex, Vector2f size) {
		super(atlas, atlasIndex);
		this.size = size;
		float halfWidth = this.size.x/2;
		float halfHeight = this.size.y/2;
		
		positions = new float[8];
		
		positions[0] = -halfWidth;
		positions[1] = halfHeight;
		
		positions[2] = halfWidth;
		positions[3] = halfHeight;
		
		positions[4] = halfWidth;
		positions[5] = -halfHeight;
		
		positions[6] = -halfWidth;
		positions[7] = -halfHeight;
		
		this.vaoID = Loader.SPRITE_LOADER.loadtoVao(positions, atlas.getUVs(atlasIndex));
	}
	
	public EntitySprite(String fileName, Vector2f size) {
		this(new Atlas(fileName, 1, 1, Loader.SPRITE_LOADER), 0, size);
	}
	
	public EntitySprite(Atlas atlas, Vector2f size) {
		this(atlas, 0, size);
	}
	

	public Vector2f getSize() {
		return this.size;
	}

	@Override
	public void updateAtlasIndex(int atlasIndex) {
		this.atlasIndex = atlasIndex;
		Loader.SPRITE_LOADER.updateVao(vaoID, this.positions, atlas.getUVs(atlasIndex));
	}
	
}
