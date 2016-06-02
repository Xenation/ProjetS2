package fr.iutvalence.info.dut.m2107.storage;

public class GUILayerMap extends LayerMap {
	
	public boolean layerCollided;
	
	/**
	 * Updates all the entities of each layer
	 */
	public void update() {
		layerCollided = false;
		for (int i = 0; i < getLayers().size(); i++) {
			if (!layerCollided) {
				getLayers().get(i).update();
			}
		}
	}
	
}
