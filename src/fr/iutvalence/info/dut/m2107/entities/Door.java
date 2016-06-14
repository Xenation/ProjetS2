package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.AbstractSprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Input;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;

public class Door extends Entity {

	private final Collider openCol = new Collider(-.75f, -2, .75f, 2);
	private final Collider closedCol = new Collider(-.33f, -2, .33f, 2);
	
	private int count = 0;
	
	private boolean open = false;
	
	public Door(Vector2f pos, AbstractSprite spr) {
		super(pos, spr, new Collider(-.5f, -2, .5f, 2));
	}
	
	@Override
	public void update(Layer layer) {
		if(Input.isMouseRightUp() && GameWorld.camera.getMouseWorldX() > this.pos.x - this.col.getW()/2 && GameWorld.camera.getMouseWorldX() < this.pos.x + this.col.getW()/2 &&
				GameWorld.camera.getMouseWorldY() > this.pos.y - this.col.getH()/2 && GameWorld.camera.getMouseWorldY() < this.pos.y + this.col.getH()/2) {
			if(count != (int)Sys.getTime()) {
				layer.remove(this);
				if(open) {
					GameWorld.layerMap.getStoredLayer(LayerStore.FURNITURE).add(this);
					this.pos.x -= .75f * this.getScale().x;
					open = false;
					this.col = new Collider(closedCol);
					this.col.setEnt(this);
					this.col.updateColPos();
				} else {
					GameWorld.layerMap.getStoredLayer(LayerStore.DOOR).add(this);
					this.pos.x += .75f * this.getScale().x;
					open = true;
					this.col = new Collider(openCol);
					this.col.setEnt(this);
					this.col.updateColPos();
				}
				this.getSprite().updateAtlasIndex(this.getSprite().getAtlasIndex() + 1);
				if(this.getSprite().getAtlasIndex() > 1) this.getSprite().updateAtlasIndex(0);
			}
			count = (int) Sys.getTime();
		}
		super.update(layer);
	}

}
