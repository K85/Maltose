package com.sakurawald.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sakurawald.manager.SkinAtlasManager;
import lombok.Getter;

public abstract class Scene2DScreen extends ApplicationScreen {

    /* Common Props */
    private final String scene_path;

    @Getter
    private final Stage stage;

    /**
     * Load the scene from a json file, then create and initialize the stage.
     *
     * @param scene_path the scene file of Scene2D. (ex. "scene_main.json")
     */
    public Scene2DScreen(String scene_path) {
        this.scene_path = scene_path;
        this.stage = SkinAtlasManager.buildStage(scene_path);

        // Initialize the stage
        initializeStage();

        // Auto register the events of this stage
        registerStageEvents();
    }


    public void renderStage() {
        // act and draw the stage
        this.stage.act(1 / FPS);
        this.stage.draw();

        // set the input processor of gdx
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float delta) {
        Gdx.app.log("Scene2DScreen", "render");

        ScreenUtils.clear(1, 1, 1, 1);
        renderStage();
    }

    protected abstract void initializeStage();

    protected abstract void registerStageEvents();

}
