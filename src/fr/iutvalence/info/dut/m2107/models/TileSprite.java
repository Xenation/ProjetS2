package fr.iutvalence.info.dut.m2107.models;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.gui.GUISprite;
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
	
	public GUISprite getGUISprite() {
		return new GUISprite(Atlas.TILE_ATLAS, atlasIndex, new Vector2f(1, 1));
	}
	
}
