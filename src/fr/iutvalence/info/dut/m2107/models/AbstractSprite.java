package fr.iutvalence.info.dut.m2107.models;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.render.Loader;

public abstract class AbstractSprite extends AtlasSprite {
	
	protected Vector2f size;
	
	protected final float[] positions;
	protected int vertexCount;
	
	private Loader loader;
	
	public AbstractSprite(Atlas atlas, int atlasIndex, Vector2f size, Loader loader) {
		super(atlas, atlasIndex);
		this.size = size;
		this.loader = loader;
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
		
		this.vaoID = loader.loadtoVao(positions, atlas.getUVs(atlasIndex));
	}

	public Vector2f getSize() {
		return this.size;
	}

	@Override
	public void updateAtlasIndex(int atlasIndex) {
		this.atlasIndex = atlasIndex;
		this.loader.updateVao(vaoID, this.positions, atlas.getUVs(atlasIndex));
	}
	
	public int getVertexCount() {
		return vertexCount;
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}
	
}
