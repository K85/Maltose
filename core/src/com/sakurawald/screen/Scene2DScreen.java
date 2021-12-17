package com.sakurawald.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.stripe.scenecomposer.SceneComposerStageBuilder;
import com.sakurawald.manager.ApplicationAssetManager;
import lombok.Getter;

public abstract class Scene2DScreen extends ApplicationScreen {

    /* Constants */
    private static final String SKIN_ROOT_PATH = "skin/";
    private static final String SCENES_ROOT_PATH = SKIN_ROOT_PATH + "scenes/";
    private static final SceneComposerStageBuilder stageBuilder = new SceneComposerStageBuilder();

    /* Common Props */
    @Getter
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
        this.stage = Scene2DScreen.buildStage(scene_path);

        // Initialize the stage
        initializeStageActors();

        // Auto register the events of this stage
        registerStageEvents();
    }

    public static Stage buildStage(String jsonHandlePath) {
        Stage stage = new Stage(new ScreenViewport());
        Skin skin = ApplicationAssetManager.getInstance().getSkin();
        stageBuilder.build(stage, skin, Gdx.files.internal(SCENES_ROOT_PATH + jsonHandlePath));
        return stage;
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

    @Override
    public void dispose() {
        // Call the dispose method of the stage. (clear Actions and Listeners)
        this.getStage().clear();
    }

    @Override
    public void hide() {
        // Let hide equals close !
        this.dispose();
    }

    protected abstract void initializeStageActors();

    protected abstract void registerStageEvents();

}
