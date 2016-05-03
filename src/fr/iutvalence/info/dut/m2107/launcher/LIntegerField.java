package fr.iutvalence.info.dut.m2107.launcher;

import javax.swing.JTextField;

public class LIntegerField extends JTextField {

	/**
	 * Default UID
	 */
	private static final long serialVersionUID = 1L;
	
	public LIntegerField(int def) {
		this.setText(String.valueOf(def));
	}
	
	public boolean checkValue() {
		try {
			Integer.parseInt(this.getText());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public int parseValue() {
		try {
			return Integer.parseUnsignedInt(this.getText());
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
}
