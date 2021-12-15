package com.sakurawald;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.collision._btMprSimplex_t;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sakurawald.manager.TextureAtlasManager;
import com.sakurawald.screen.MainMenuScreen;
import lombok.Getter;
import lombok.Setter;

public class Maltose extends Game {

    public static Maltose getInstance() {
        return (Maltose) Gdx.app.getApplicationListener();
    }

    @Override
    public void create() {
        this.setScreen(new MainMenuScreen());
    }

}
