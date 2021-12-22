package com.sakurawald.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
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
    /** scene_path will be null if you design the Stage by codes. */
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


    /**
     * Constructor for code-based scene.
     */
    public Scene2DScreen(){
        this.scene_path = null;

        /* the NoArgsConstructor new Stage() will use ScalingViewport, if the ApplicationWindow is too small, then nothing can be seen clearly.
         * Here we need to use new Stage(new ScreenViewport()) to avoid this */
        this.stage = new Stage(new ScreenViewport());

        // Initialize the stage
        initializeStageActors();

        // Auto register the events of this stage
        registerStageEvents();
    }

    public static Stage buildStage(String jsonHandlePath) {
        Stage stage = new Stage(new ScreenViewport());
        Skin skin = ApplicationAssetManager.getSkin();
        stageBuilder.build(stage, skin, Gdx.files.internal(SCENES_ROOT_PATH + jsonHandlePath));
        return stage;
    }

    public void renderStage() {
        // act and draw the stage
        this.stage.act(Gdx.graphics.getDeltaTime());
        this.stage.draw();
        // set this Screen's Stage as the input processor of gdx
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
        this.stage.clear();
    }

    @Override
    public void resize(int width, int height) {
        // Update the viewport after resize. (or the Scene2D Screen may be stretched)
        System.out.println("scene2d screen resize() ==================");

        this.stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        // Let hide equals close() !
        this.dispose();
    }

    public Skin getSkin() {
        return ApplicationAssetManager.getSkin();
    }


    protected abstract void initializeStageActors();

    protected abstract void registerStageEvents();
}
