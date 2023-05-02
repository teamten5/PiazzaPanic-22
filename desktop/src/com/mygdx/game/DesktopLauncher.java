package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Config.loadConfig();
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(Config.fpsLimit);
		config.setWindowedMode(Config.resolutionWidth, Config.resolutionHeight);
		config.setTitle("Piazza Panic");
		new Lwjgl3Application(new PiazzaPanic(), config);
	}
}
