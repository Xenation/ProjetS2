package fr.iutvalence.info.dut.m2107.launcher;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class LLaunchBtn extends JButton implements MouseListener {
	
	/**
	 * Default UID
	 */
	private static final long serialVersionUID = 1L;

	public LLaunchBtn() {
		super("Launch Game");
		this.setSize(300, 100);
		this.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Launcher.launchGame();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
//		System.out.println("Enter");
	}

	@Override
	public void mouseExited(MouseEvent e) {
//		System.out.println("Exit");
	}

	@Override
	public void mousePressed(MouseEvent e) {
//		System.out.println("Press");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
//		System.out.println("Release");
	}
	
}
