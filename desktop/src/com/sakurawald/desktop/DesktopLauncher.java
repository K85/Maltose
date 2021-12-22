package com.sakurawald.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sakurawald.Maltose;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Maltose";
        config.addIcon("icon.png", Files.FileType.Internal);
        config.width = 1280;
        config.height = 960;
        new LwjglApplication(new Maltose(), config);
    }
}
