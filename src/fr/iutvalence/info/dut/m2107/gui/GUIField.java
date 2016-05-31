package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.events.GUIFieldSubmittedEvent;
import fr.iutvalence.info.dut.m2107.events.GUIMouseLeftDownEvent;
import fr.iutvalence.info.dut.m2107.events.GUIMouseLeftDownOutEvent;
import fr.iutvalence.info.dut.m2107.events.Listener;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.models.AbstractSprite;
import fr.iutvalence.info.dut.m2107.storage.Input;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public class GUIField extends GUIElement implements Listener {
	
	protected boolean isSelected;
	protected GUIText text;
	protected String effectiveText;
	
	public GUIField(AbstractSprite spr, Vector2f pos, float width, float height) {
		super(spr, pos, width, height);
		this.isSelected = false;
		this.text = new GUIText("", 1, -width/2, height/2, width, false);
		initLayer();
		getLayer().add(text);
		registerListener(this);
	}
	
	public void onGUIMouseLeftDown(GUIMouseLeftDownEvent event) {
		if (!isSelected) {
			select();
		}
	}
	
	public void onGUIMouseLeftDownOut(GUIMouseLeftDownOutEvent event) {
		if (isSelected) {
			deselect();
		}
	}
	
	public void onGUIFieldSubmitted(GUIFieldSubmittedEvent event) {
		deselect();
	}
	
	public void update(Layer layer) {
		super.update(layer);
		
		if (!mouseHover && Input.isMouseLeftDown()) {
			sendPreciseEvent(new GUIMouseLeftDownOutEvent(this));
		}
		
		char c = Input.getCharacter();
//		System.out.println((int) c);
		if (isSelected && Input.isKeyPressed()) {
			effectiveText = this.text.getTextString().substring(0, this.text.getTextString().length()-1);
			if (c >= 32 && c <= 126) {
				this.text.updateText(effectiveText + c + "|");
			} else if (c == 8 && effectiveText.length() != 0) {
				this.text.updateText(effectiveText.substring(0, effectiveText.length()-1) + "|");
			} else if (c == 13) {
				sendPreciseEvent(new GUIFieldSubmittedEvent(this));
			}
		} else {
			effectiveText = this.text.getTextString();
		}
	}
	
	private void select() {
		isSelected = true;
		Input.isWritingGUI = true;
		this.text.updateText(this.text.getTextString() + "|");
	}
	
	private void deselect() {
		isSelected = false;
		Input.isWritingGUI = false;
		this.text.updateText(this.text.getTextString().substring(0, this.text.getTextString().length()-1));
	}
	
	public String getEffectiveText() {return effectiveText;}
	
}
