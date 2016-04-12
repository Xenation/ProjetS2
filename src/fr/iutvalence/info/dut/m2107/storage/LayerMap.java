package fr.iutvalence.info.dut.m2107.storage;

import java.util.ArrayList;
import java.util.List;

public class LayerMap {
	
	private List<Layer> layerMap = new ArrayList<Layer>();
	
	public void update() {
		for (Layer layer : layerMap) {
			layer.update();
		}
	}
	
	public void add(Layer lay) {
		layerMap.add(lay);
	}
	
	public void addEmpty(int count) {
		for (int i = 0; i < count; i++) {
			add(new Layer());
		}
	}
	
	public void set(int index, Layer lay) {
		this.layerMap.set(index, lay);
	}
	
	public void moveUp(int index) {
		if (index > 0 && index < this.layerMap.size()) {
			Layer toMove = this.layerMap.get(index);
			this.layerMap.set(index, this.layerMap.get(index-1));
			this.layerMap.set(index-1, toMove);
		}
	}
	
	public void moveDown(int index) {
		if (index >= 0 && index < this.layerMap.size()-1) {
			Layer toMove = this.layerMap.get(index);
			this.layerMap.set(index, this.layerMap.get(index+1));
			this.layerMap.set(index+1, toMove);
		}
	}
	
	public List<Layer> getLayers() {
		return this.layerMap;
	}
	
	public int getLayersCount() {
		return this.layerMap.size();
	}
	
	public Layer getLayer(int index) {
		return this.layerMap.get(index);
	}
	
}
