package fr.iutvalence.info.dut.m2107.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.events.EventManager;
import fr.iutvalence.info.dut.m2107.events.GUIMouseLeavedEvent;
import fr.iutvalence.info.dut.m2107.events.Listener;
import fr.iutvalence.info.dut.m2107.gui.GUIElement;
import fr.iutvalence.info.dut.m2107.models.Atlas;

/**
 * Layer optimised for GUI.
 * @author Xenation
 *
 */
public class GUILayer extends Layer implements Listener {
	
	/**
	 * The layer map. Groups a list of entities under a same atlas
	 */
	private Map<Atlas, List<Entity>> layer = new HashMap<Atlas, List<Entity>>();
	
	public void update() {
		for (Atlas atl : layer.keySet()) {
			for (Entity ent : layer.get(atl)) {
				ent.update(this);
			}
		}
	}
	
	@Override
	public void add(Entity ent) {
		List<Entity> newList = layer.get(ent.getSprite().getAtlas());
		if (newList == null) {
			newList = new ArrayList<Entity>();
			layer.put(ent.getSprite().getAtlas(), newList);
		}
		newList.add(ent);
	}
	
	@Override
	public void remove(Entity ent) {
		List<Entity> rList = layer.get(ent.getSprite().getAtlas());
		if (rList != null) {
			rList.remove(ent);
			if (ent instanceof GUIElement) {
				GUIElement elem = (GUIElement) ent;
				if (elem.isMouseHover()) {
					elem.setMouseHover(false);
					EventManager.sendEvent(new GUIMouseLeavedEvent(elem));
				}
			}
			if (rList.size() == 0) {
				layer.remove(ent.getSprite().getAtlas());
			}
		}
	}
	
	@Override
	public Set<Atlas> atlases() {
		return this.layer.keySet();
	}
	
	/**
	 * Returns a list of entities that use the given atlas in this layer
	 * @param atlas the atlas to look for
	 * @return a list of entities that use the given atlas in this layer
	 */
	public List<Entity> getEntities(Atlas atlas) {
		return this.layer.get(atlas);
	}
	
}
