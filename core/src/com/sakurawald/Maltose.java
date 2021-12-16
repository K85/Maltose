package com.sakurawald;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.sakurawald.screen.MainMenuScreen;

public class Maltose extends Game {

    public static Maltose getInstance() {
        return (Maltose) Gdx.app.getApplicationListener();
    }

    @Override
    public void create() {
        this.setScreen(new MainMenuScreen());
    }

}
