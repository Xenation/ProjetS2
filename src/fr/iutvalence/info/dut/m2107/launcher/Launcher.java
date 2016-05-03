package fr.iutvalence.info.dut.m2107.launcher;

import fr.iutvalence.info.dut.m2107.enginetest.MainGameTester;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;

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
		MainGameTester.main(args);
	}
	
}
