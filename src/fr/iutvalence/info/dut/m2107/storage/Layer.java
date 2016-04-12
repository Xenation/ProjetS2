package fr.iutvalence.info.dut.m2107.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.models.Sprite;

public class Layer implements Iterable<Entity> {
	
	private Map<Sprite, List<Entity>> layer = new HashMap<Sprite, List<Entity>>();
	
	public void update() {
		for (Entity ent : this) {
			ent.update();
		}
	}
	
	public void add(Entity ent) {
		List<Entity> newList = layer.get(ent.getSprite());
		if (newList == null) {
			newList = new ArrayList<Entity>();
			layer.put(ent.getSprite(), newList);
		}
		newList.add(ent);
	}
	
	public void addAll(List<Entity> entities) {
		for (Entity entity : entities) {
			add(entity);
		}
	}
	
	public void remove(Entity ent) {
		List<Entity> ents = layer.get(ent.getSprite());
		if (ents != null) {
			ents.remove(ent);
			if (ents.size() == 0) {
				layer.remove(ents);
			}
		}
	}
	
	public Set<Sprite> sprites() {
		return this.layer.keySet();
	}
	
	public List<Entity> getEntities(Sprite spr) {
		return this.layer.get(spr);
	}
	
	////ITERABLE \\\\
	@Override
	public Iterator<Entity> iterator() {
		List<Entity> ents = new ArrayList<Entity>();
		for (Sprite spr : layer.keySet()) {
			for (Entity ent : layer.get(spr)) {
				ents.add(ent);
			}
		}
		return ents.iterator();
	}
	
}
