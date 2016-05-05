package fr.iutvalence.info.dut.m2107.launcher;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lwjgl.opengl.DisplayMode;

import fr.iutvalence.info.dut.m2107.render.DisplayManager;

public class LauncherWindow extends JFrame {

	/**
	 * Default UID
	 */
	private static final long serialVersionUID = 1L;
	
	private LDisplayList list_modes;
	private JCheckBox check_fullscreen;
	private JCheckBox check_vsync;
	private LIntegerField field_fps;
	private LIntegerField field_render;
	
	private JCheckBox check_debug;
	
	public LauncherWindow() {
		this.setTitle("EAGL Game Launcher");
		this.setSize(400, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		JPanel panel_main = new JPanel();
		this.setContentPane(panel_main);
		this.setLayout(new GridLayout(4, 1));
		
		GridLayout layout_graphics = new GridLayout(5, 2);
		layout_graphics.setVgap(4);
		
		JPanel panel_graphics = new JPanel();
//		panel_graphics.setBackground(Color.RED);
		panel_graphics.setLayout(layout_graphics);
		panel_graphics.add(new JLabel("Resolution: "));
		list_modes = new LDisplayList(DisplayManager.getDisplayModes());
		panel_graphics.add(list_modes);
		panel_graphics.add(new JLabel("Fullscreen: "));
		check_fullscreen = new JCheckBox();
		check_fullscreen.setSelected(true);
		panel_graphics.add(check_fullscreen);
		panel_graphics.add(new JLabel("Vsync: "));
		check_vsync = new JCheckBox();
		check_vsync.setSelected(true);
		panel_graphics.add(check_vsync);
		panel_graphics.add(new JLabel("FPS cap: "));
		field_fps = new LIntegerField(60);
		panel_graphics.add(field_fps);
		panel_graphics.add(new JLabel("Render height: "));
		field_render = new LIntegerField(20);
		panel_graphics.add(field_render);
		
		GridLayout layout_others = new GridLayout(1, 2);
		layout_others.setVgap(4);
		
		JPanel panel_others = new JPanel();
//		panel_others.setBackground(Color.ORANGE);
		panel_others.setLayout(layout_others);
		panel_others.add(new JLabel("Debug Text: "));
		check_debug = new JCheckBox();
		panel_others.add(check_debug);
		
		JPanel panel_empty = new JPanel();
//		panel_empty.setBackground(Color.CYAN);
		
		JPanel panel_launch = new JPanel();
//		panel_launch.setBackground(Color.GREEN);
		panel_launch.add(new LLaunchBtn());
		
		panel_main.add(panel_graphics);
		panel_main.add(panel_others);
		panel_main.add(panel_empty);
		panel_main.add(panel_launch);
		
		this.setVisible(true);
	}
	
	public DisplayMode getDisplayMode() {
		return list_modes.getSelectedDisplayMode();
	}
	
	public boolean getFullscreen() {
		return check_fullscreen.isSelected();
	}
	
	public boolean getVSync() {
		return check_vsync.isSelected();
	}
	
	public int getFPSCap() {
		return field_fps.parseValue();
	}
	
	public int getRenderHeight() {
		return field_render.parseValue();
	}
	
	public boolean getDebug() {
		return check_debug.isSelected();
	}
	
}
