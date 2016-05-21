package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Atlas;
import fr.iutvalence.info.dut.m2107.render.Loader;

public class TextSprite extends GUISprite {

	public TextSprite() {
		super(Atlas.TEXT_ATLAS, 0, new Vector2f(0, 0), Loader.TEXT_LOADER, 0);
	}

	@Override
	public void updateAtlasIndex(int atlasIndex) {
		// Not happening
	}
	
	public void setVaoID(int vaoID) {
		this.vaoID = vaoID;
	}

}
