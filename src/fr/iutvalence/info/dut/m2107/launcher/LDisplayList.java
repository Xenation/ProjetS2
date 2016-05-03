package fr.iutvalence.info.dut.m2107.launcher;

import java.util.List;

import javax.swing.JComboBox;

import org.lwjgl.opengl.DisplayMode;

public class LDisplayList extends JComboBox<String> {

	/**
	 * Default UID
	 */
	private static final long serialVersionUID = 1L;
	
	private final List<DisplayMode> displayModes;
	
	public LDisplayList(List<DisplayMode> modes) {
		this.displayModes = modes;
		putBestDisplayAtFirst();
		for (DisplayMode mode : displayModes) {
			this.addItem(mode.getWidth()+"x"+mode.getHeight()+" "+mode.getFrequency()+"Hz "+mode.getBitsPerPixel()+"bit");
		}
	}
	
	private void putBestDisplayAtFirst() {
		DisplayMode best = displayModes.get(0);
		for (DisplayMode mode : displayModes) {
			if (mode.getWidth() >= best.getWidth() && mode.getHeight() >= best.getHeight() && mode.getBitsPerPixel() >= best.getBitsPerPixel()) {
				best = mode;
			}
		}
		displayModes.remove(best);
		displayModes.add(0, best);
	}
	
	public DisplayMode getSelectedDisplayMode() {
		return displayModes.get(this.getSelectedIndex());
	}
	
}
