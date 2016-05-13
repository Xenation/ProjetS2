package fr.iutvalence.info.dut.m2107.models;

import fr.iutvalence.info.dut.m2107.render.Loader;
import fr.iutvalence.info.dut.m2107.tiles.Tile;

public class TileSprite extends AtlasSprite {
	
	public static final float[] POSITIONS = {
			0, Tile.TILE_SIZE,
			Tile.TILE_SIZE, Tile.TILE_SIZE, 
			Tile.TILE_SIZE, 0,
			0, 0
	};
	
	public TileSprite(int atlasIndex) {
		super(Atlas.TILE_ATLAS, atlasIndex, Loader.TILE_LOADER.loadtoVao(POSITIONS, Atlas.TILE_ATLAS.getUVs(atlasIndex)));
	}

	@Override
	public void updateAtlasIndex(int atlasIndex) {
		this.atlasIndex = atlasIndex;
		Loader.TILE_LOADER.updateVao(vaoID, POSITIONS, atlas.getUVs(atlasIndex));
	}
	
}
