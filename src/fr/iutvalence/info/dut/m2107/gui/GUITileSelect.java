package fr.iutvalence.info.dut.m2107.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.core.GameManager;
import fr.iutvalence.info.dut.m2107.events.GUIMouseLeftDownEvent;
import fr.iutvalence.info.dut.m2107.events.GUIMouseRightDownEvent;
import fr.iutvalence.info.dut.m2107.events.Listener;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.tiles.TileType;

public class GUITileSelect implements Listener {
	
	private static final int SLOTS_NB_WIDTH = 9;
	private static final int SLOTS_NB_HEIGHT = 8;
	
	private static final int VARSLOTS_NB_WIDTH = 4;
	private static final int VARSLOTS_NB_HEIGHT = 2;
	
	private static final float SLOT_SIZE = .075f;
	
	private GUIElement background;
	
	private GUIElement backVariantSelect;
	
	private boolean isVariantSelectOn;
	
	private int rightClickedTypeIndex;
	
	private List<GUIButtonIcon> slots;
	
	private List<GUIButtonIcon> variantSlots;
	
	public GUITileSelect() {
		this.background = new GUIElement(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(0, .2f), 1, .8f);
		this.background.initLayer();
		this.slots = new ArrayList<GUIButtonIcon>(SLOTS_NB_WIDTH*SLOTS_NB_HEIGHT);
		float startX = -background.getScale().x/2 + SLOT_SIZE;
		float startY = background.getScale().y/2 - SLOT_SIZE*DisplayManager.aspectRatio;
		for (int i = 0; i < SLOTS_NB_WIDTH*SLOTS_NB_HEIGHT; i++) {
			int iX = i % SLOTS_NB_WIDTH;
			int iY = -i / SLOTS_NB_WIDTH;
			GUIButtonIcon slot = new GUIButtonIcon(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(startX + iX * (background.getScale().x-SLOT_SIZE/2)/SLOTS_NB_WIDTH, startY + iY * (background.getScale().y-SLOT_SIZE)/SLOTS_NB_HEIGHT), SLOT_SIZE, SLOT_SIZE, null);
			slot.registerListener(this);
			slots.add(slot);
			slot.setParent(background);
		}
		for (int i = 0; i < TileType.values().length; i++) {
			slots.get(i).setIcon(TileType.values()[i].getBaseVariant().sprite.getGUISprite());
		}
		this.backVariantSelect = new GUIElement(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(0, 0), .5f, .25f);
		this.backVariantSelect.initLayer();
		this.variantSlots = new ArrayList<GUIButtonIcon>(VARSLOTS_NB_WIDTH*VARSLOTS_NB_HEIGHT);
		startX = -backVariantSelect.getScale().x/2 + SLOT_SIZE;
		startY = backVariantSelect.getScale().y/2 - SLOT_SIZE*DisplayManager.aspectRatio;
		for (int i = 0; i < VARSLOTS_NB_WIDTH*VARSLOTS_NB_HEIGHT; i++) {
			int iX = i % VARSLOTS_NB_WIDTH;
			int iY = -i / VARSLOTS_NB_WIDTH;
			GUIButtonIcon vslot = new GUIButtonIcon(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(startX + iX * (backVariantSelect.getScale().x-SLOT_SIZE/2)/VARSLOTS_NB_WIDTH, startY + iY * (backVariantSelect.getScale().y-SLOT_SIZE)/VARSLOTS_NB_HEIGHT), SLOT_SIZE, SLOT_SIZE, null);
			vslot.registerListener(this);
			variantSlots.add(vslot);
			vslot.setParent(backVariantSelect);
		}
	}
	
	public void onGUIMouseLeftDown(GUIMouseLeftDownEvent event) {
		if (slots.contains(event.getElement())) {
			int index = slots.indexOf(event.getElement());
			if (index < 0 || index >= TileType.values().length) return;
			GameWorld.camera.setType(TileType.values()[index]);
			GameWorld.camera.setVariant(null);
			GameManager.unloadGUITileSelect();
		} else if (variantSlots.contains(event.getElement())) {
			int index = variantSlots.indexOf(event.getElement());
			if (index < 0 || index >= TileType.values()[rightClickedTypeIndex].getVariants().size()) return;
			GameWorld.camera.setType(TileType.values()[rightClickedTypeIndex]);
			GameWorld.camera.setVariant(TileType.values()[rightClickedTypeIndex].getVariants().get(index));
			GameManager.unloadGUITileSelect();
		}
	}
	
	public void onGUIMouseRightDown(GUIMouseRightDownEvent event) {
		int index = slots.indexOf(event.getElement());
		if (index < 0 || index >= TileType.values().length) return;
		backVariantSelect.setPosition(event.getElement().getScreenX() + backVariantSelect.getScale().x/2, event.getElement().getScreenY() - backVariantSelect.getScale().y/2);
		rightClickedTypeIndex = index;
		for (int i = 0; i < variantSlots.size(); i++) {
			if (i < TileType.values()[rightClickedTypeIndex].getVariants().size()) {
				variantSlots.get(i).setIcon(TileType.values()[rightClickedTypeIndex].getVariants().get(i).sprite.getGUISprite());
			} else {
				variantSlots.get(i).setIcon(null);
			}
		}
		if (!isVariantSelectOn)
			loadVariantSelect();
	}
	
	private void loadVariantSelect() {
		GameWorld.guiLayerMap.getLayer(1).add(backVariantSelect);
		isVariantSelectOn = true;
	}
	
	private void unloadVariantSelect() {
		GameWorld.guiLayerMap.getLayer(1).remove(backVariantSelect);
		isVariantSelectOn = false;
	}
	
	public void loadGUIElements() {
		GameWorld.guiLayerMap.getLayer(2).add(background);
	}
	
	public void unloadGUIElements() {
		GameWorld.guiLayerMap.getLayer(2).remove(background);
		if (isVariantSelectOn) {
			unloadVariantSelect();
		}
	}
	
}
