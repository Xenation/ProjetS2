package fr.iutvalence.info.dut.m2107.launcher;

import fr.iutvalence.info.dut.m2107.enginetest.MainGameTester;
import fr.iutvalence.info.dut.m2107.gui.GUIMaster;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.render.Renderer;

public class Launcher {
	
	public static LauncherWindow window;
	
	public static void runLauncher() {
		window = new LauncherWindow();
	}
	
	public static void launchGame() {
		window.setVisible(false);
		window.dispose();
		String[] args = {"launcher"};
		System.out.println("Launching Game with:\nresolution = "+window.getDisplayMode().toString()+"\nfullscreen = "+window.getFullscreen()+"\nvsync = "+window.getVSync()+"\nfps = "+window.getFPSCap());
		DisplayManager.createDisplay(window.getDisplayMode(), window.getFullscreen(), window.getVSync(), window.getFPSCap());
		DisplayManager.updateDisplay();
		Renderer.init(window.getRenderHeight());
		MainGameTester.main(args);
	}
	
}
