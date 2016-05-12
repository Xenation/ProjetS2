package fr.iutvalence.info.dut.m2107.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;

/**
 * Defines a layer that contains entities.
 * The entities are ordered by Sprite.
 * @author Xenation
 *
 */
public class Layer implements Iterable<Entity> {
	
	/**
	 * the map that gathers all the entities that have the same sprite under a same list
	 */
	private Map<EntitySprite, List<Entity>> layer = new HashMap<EntitySprite, List<Entity>>();
	
	/**
	 * The depth of this layer (only visual)
	 */
	private float depth = 0;
	
	/**
	 * Updates every entity of this layer
	 */
	public void update() {
		for (Entity ent : this) {
			ent.update(this);
		}
	}
	
	/**
	 * Adds an entity to this layer
	 * @param ent the entity to add
	 */
	public void add(Entity ent) {
		List<Entity> newList = layer.get(ent.getSprite());
		if (newList == null) {
			newList = new ArrayList<Entity>();
			layer.put(ent.getSprite(), newList);
		}
		newList.add(ent);
	}
	
	/**
	 * Adds a list of entities to this layer
	 * @param entities the list of entities to add
	 */
	public void addAll(List<Entity> entities) {
		for (Entity entity : entities) {
			add(entity);
		}
	}
	
	/**
	 * remove a given entity from this layer
	 * @param ent the entity to remove
	 */
	public void remove(Entity ent) {
		List<Entity> ents = layer.get(ent.getSprite());
		if (ents != null) {
			ents.remove(ent);
			if (ents.size() == 0) {
				layer.remove(ents);
			}
		}
	}
	
	/**
	 * Returns a set of all sprites present in this layer
	 * @return a set of all sprites present in this layer
	 */
	public Set<EntitySprite> sprites() {
		return this.layer.keySet();
	}
	
	/**
	 * Returns a list of entities that have the given sprite in his layer
	 * @param spr the sprite to look for
	 * @return a list of entities that have the given sprite in his layer
	 */
	public List<Entity> getEntities(EntitySprite spr) {
		return this.layer.get(spr);
	}
	
	////ITERABLE \\\\
	@Override
	public Iterator<Entity> iterator() {
		List<Entity> ents = new ArrayList<Entity>();
		for (EntitySprite spr : layer.keySet()) {
			for (Entity ent : layer.get(spr)) {
				ents.add(ent);
			}
		}
		return ents.iterator();
	}
	
	/**
	 * Returns the depth of this layer
	 * @return the depth of this layer
	 */
	public float getDepth() {
		return depth;
	}

	/**
	 * Sets the depth of this layer
	 * @param depth the new depth
	 */
	public void setDepth(float depth) {
		this.depth = depth;
	}
	
}
