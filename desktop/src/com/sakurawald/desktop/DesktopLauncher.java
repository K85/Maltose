package com.sakurawald.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sakurawald.Maltose;

public class DesktopLauncher {
	public static void main (String[] arg) {
		/* NODE
		*  1. default FPS is 60
		* */
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Maltose(), config);
	}
}
