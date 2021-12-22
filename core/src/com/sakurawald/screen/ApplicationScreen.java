package com.sakurawald.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;


public abstract class ApplicationScreen implements Screen {

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        // the default hide() method means dispose() the screen
        this.dispose();
    }

    @Override
    public void dispose() {

    }
}
