package fr.iutvalence.info.dut.m2107.storage;

import java.util.ArrayList;
import java.util.List;

import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;

/**
 * Defines a map of layers.
 * Layers are ordered by an index (min 0)
 * The smaller the index the later the layer is rendered
 * @author Xenation
 *
 */
public class LayerMap {
	
	/**
	 * The list of layers
	 */
	private List<Layer> layerMap = new ArrayList<Layer>();
	
	/**
	 * Updates all the entities of each layer
	 */
	public void update() {
		for (int i = 0; i < layerMap.size(); i++) {
			layerMap.get(i).update();
		}
	}
	
	public void resetLights() {
		for (Layer layer : layerMap) {
			for (Entity entity : layer) {
				entity.resetLight();
			}
		}
	}
	
	/**
	 * Adds a layer to this layer map (on top of the list).
	 * @param lay the layer to add
	 */
	public void add(Layer lay) {
		layerMap.add(lay);
	}
	
	/**
	 * Adds count numbers of new empty layers to this map.
	 * @param count the number of layers to add
	 */
	public void addEmpty(int count) {
		for (int i = 0; i < count; i++) {
			add(new Layer());
		}
	}
	
	/**
	 * Sets the layer at the given index
	 * @param index the index of the layer to set
	 * @param lay the layer to set
	 */
	public void set(int index, Layer lay) {
		this.layerMap.set(index, lay);
	}
	
	/**
	 * Moves the layer at the specified index up in list (closer to 0)
	 * @param index the index of the layer to move
	 */
	public void moveUp(int index) {
		if (index > 0 && index < this.layerMap.size()) {
			Layer toMove = this.layerMap.get(index);
			this.layerMap.set(index, this.layerMap.get(index-1));
			this.layerMap.set(index-1, toMove);
		}
	}
	
	/**
	 * Moves the layer at the specified index down in list (far from 0)
	 * @param index the index of the layer to move
	 */
	public void moveDown(int index) {
		if (index >= 0 && index < this.layerMap.size()-1) {
			Layer toMove = this.layerMap.get(index);
			this.layerMap.set(index, this.layerMap.get(index+1));
			this.layerMap.set(index+1, toMove);
		}
	}
	
	/**
	 * Returns a list of all the layers in this map
	 * @return a list of all the layers in this map
	 */
	public List<Layer> getLayers() {
		return this.layerMap;
	}
	
	/**
	 * Returns the number of layers in this map
	 * @return the number of layers in this map
	 */
	public int getLayersCount() {
		return this.layerMap.size();
	}
	
	/**
	 * Returns the layer at the given index
	 * @param index the index of the layer to get
	 * @return the layer at the given index
	 */
	public Layer getLayer(int index) {
		return this.layerMap.get(index);
	}
	
	public Layer getStoredLayer(LayerStore index) {
		return this.layerMap.get(index.ordinal());
	}
	
	public void reset() {
		int size = layerMap.size();
		layerMap.clear();
		addEmpty(size);
	}
	
}
