package fr.iutvalence.info.dut.m2107.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.models.Atlas;
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
	private Map<Atlas, Map<EntitySprite, List<Entity>>> layer = new HashMap<Atlas, Map<EntitySprite, List<Entity>>>();
	
	
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
		Map<EntitySprite, List<Entity>> newMap = layer.get(ent.getSprite().getAtlas());
		if (newMap == null) {
			newMap = new HashMap<EntitySprite, List<Entity>>();
			layer.put(ent.getSprite().getAtlas(), newMap);
		}
		List<Entity> newList = layer.get(ent.getSprite().getAtlas()).get(ent.getSprite());
		if (newList == null) {
			newList = new ArrayList<Entity>();
			layer.get(ent.getSprite().getAtlas()).put(ent.getSprite(), newList);
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
		Map<EntitySprite, List<Entity>> rMap = layer.get(ent.getSprite().getAtlas());
		if (rMap != null) {
			List<Entity> rList = layer.get(ent.getSprite().getAtlas()).get(ent.getSprite());
			if (rList != null) {
				rList.remove(ent);
				if (rList.size() == 0) {
					layer.get(ent.getSprite().getAtlas()).remove(ent.getSprite());
				}
			}
			if (rMap.size() == 0) {
				layer.remove(ent.getSprite().getAtlas());
			}
		}
	}
	
	/**
	 * Returns a set of all atlases of the layer
	 * @return a set of all atlases of the layer
	 */
	public Set<Atlas> atlases() {
		return this.layer.keySet();
	}
	
	/**
	 * Returns a set of all sprites that use a given atlas
	 * @return a set of all sprites that use a given atlas
	 */
	public Set<EntitySprite> sprites(Atlas atlas) {
		return this.layer.get(atlas).keySet();
	}
	
	/**
	 * Returns a list of entities that have the given sprite in his layer
	 * @param spr the sprite to look for
	 * @return a list of entities that have the given sprite in his layer
	 */
	public List<Entity> getEntities(Atlas atlas, EntitySprite spr) {
		return this.layer.get(atlas).get(spr);
	}
	
	////ITERABLE \\\\
	@Override
	public Iterator<Entity> iterator() {
		List<Entity> ents = new ArrayList<Entity>();
		for (Atlas atl : layer.keySet()) {
			for (EntitySprite spr : layer.get(atl).keySet()) {
				for (Entity ent : layer.get(atl).get(spr)) {
					ents.add(ent);
				}
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
