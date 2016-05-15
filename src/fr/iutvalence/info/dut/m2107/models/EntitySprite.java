package fr.iutvalence.info.dut.m2107.models;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.render.Loader;

public class EntitySprite extends AbstractSprite {
	
	public EntitySprite(Atlas atlas, int atlasIndex, Vector2f size) {
		super(atlas, atlasIndex, size, Loader.SPRITE_LOADER);
	}
	
	public EntitySprite(String fileName, Vector2f size) {
		this(new Atlas(fileName, 1, 1, Loader.SPRITE_LOADER), 0, size);
	}
	
	public EntitySprite(Atlas atlas, Vector2f size) {
		this(atlas, 0, size);
	}
	
}
