package com.sakurawald.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sakurawald.logic.component.*;
import com.sakurawald.logic.entity.Tags;
import com.sakurawald.logic.script.BoundaryAutoDestroyScript;
import com.sakurawald.logic.script.PlayerScript;
import com.sakurawald.logic.stage.ScoreBoardHUD;
import com.sakurawald.logic.system.CameraSystem;
import com.sakurawald.manager.ApplicationAssetManager;
import com.sakurawald.manager.BoundaryManager;
import com.sakurawald.timer.SpawnStoneTask;
import com.sakurawald.timer.SpawnTokenTask;
import games.rednblack.editor.renderer.SceneConfiguration;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import games.rednblack.editor.renderer.utils.ItemWrapper;
import lombok.Getter;

import java.util.ArrayList;

public class GameScreen extends ApplicationScreen {

    /* Constants */
    protected static final float STEP_TIME = 1 / FPS;
    protected static final float VELOCITY_ITERATIONS = 6;
    protected static final float POSITION_ITERATIONS = 2;
    public final static float MAX_VELOCITY = 10;

    /* World Properties */
    public final static float VIRTUAL_RESOLUTION_WIDTH = 1280;
    public final static float VIRTUAL_RESOLUTION_HEIGHT = 720;
    public final static float PPWU = 80.0f;
    public final static float WORLD_WIDTH = VIRTUAL_RESOLUTION_WIDTH / PPWU;
    public final static float WORLD_HEIGHT = VIRTUAL_RESOLUTION_HEIGHT / PPWU;

    /* Common Props */
//    private final SpriteBatch spriteBatch = new SpriteBatch();
//    @Getter

    /* Camera and Viewport */
    private final OrthographicCamera camera = new OrthographicCamera();
    @Getter
    private Viewport viewport;

    /* Graphics */
    @Getter
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    /* Box2D */
    @Getter
    private final Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();

    @Getter
    private SceneLoader sceneLoader;
    @Getter
    private SceneConfiguration sceneConfiguration;

    /* Artemis */
    @Getter
    private ItemWrapper rootItemWrapper;

    /* ScoreBoard */
    private ScoreBoardHUD scoreBoard;

    /* BoundaryManager */
    @Getter
    BoundaryManager boundaryManager = new BoundaryManager(this);

    @Override
    public void show() {

        /* Init Box2D */
        Box2D.init();

        /* Camera and Viewport */
        viewport = new ExtendViewport(VIRTUAL_RESOLUTION_WIDTH / PPWU, VIRTUAL_RESOLUTION_HEIGHT / PPWU
                , camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        /* Load MainScene */
        CameraSystem cameraSystem = new CameraSystem(5, 16, 5, 12);
        sceneConfiguration = new SceneConfiguration();
        sceneConfiguration.setResourceRetriever(ApplicationAssetManager.getInstance().getAsyncResourceLoader());
        sceneConfiguration.addSystem(cameraSystem);

        sceneLoader = ApplicationAssetManager.getInstance().makeSceneLoader(sceneConfiguration);
        sceneLoader.loadScene("MainScene", viewport);
        rootItemWrapper = new ItemWrapper(sceneLoader.getRoot(), sceneLoader.getEngine());

        /* Add Components */
        ItemWrapper player = this.getPlayer();
        ComponentRetriever.addMapper(PlayerComponent.class);
        ComponentRetriever.create(player.getEntity(), PlayerComponent.class, sceneLoader.getEngine());

        ComponentRetriever.addMapper(DeadlyObstacleComponent.class);
        sceneLoader.addComponentByTagName(Tags.DEADLY_OBSTACLE, DeadlyObstacleComponent.class);
        sceneLoader.addComponentByTagName(Tags.STONE, StoneComponent.class);
        sceneLoader.addComponentByTagName(Tags.BOUNDARY, BoundaryComponent.class);

        ComponentRetriever.addMapper(TokenComponent.class);
        sceneLoader.addComponentByTagName(Tags.TOKEN, TokenComponent.class);

        /* Add Scripts */
        PlayerScript playerScript = new PlayerScript(this);
        player.addScript(playerScript);

        player.addScript(new BoundaryAutoDestroyScript(this));

        cameraSystem.setFocusEntityID(player.getEntity());

        /* Register Timers */
        new SpawnStoneTask(this).scheduleSelf();
        new SpawnTokenTask(this).scheduleSelf();

        /* Add Other Stages */
        scoreBoard = new ScoreBoardHUD(playerScript.getPlayerComponent(), new ExtendViewport(768, 576), sceneLoader);

        /* Add Rectangle Boundary */
        boundaryManager.createPolygonBoundary(new ArrayList<Vector2>() {
            {
                this.add(new Vector2(0, 0));
                this.add(new Vector2(0, WORLD_HEIGHT));
                this.add(new Vector2(WORLD_WIDTH, WORLD_HEIGHT));
                this.add(new Vector2(WORLD_WIDTH, 0));
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.app.log("GameScreen", "render");

        System.out.printf("frame per second: %d", Gdx.graphics.getFramesPerSecond());
        ScreenUtils.clear(1, 1, 1, 1);

        /* Render -> Box2D World */
        viewport.apply();
        sceneLoader.getEngine().process();

        /* Render -> ScoreBoard */
        scoreBoard.act(Gdx.graphics.getDeltaTime());
        scoreBoard.getViewport().apply();
        scoreBoard.draw();

        System.out.printf("world width = %.2f, world height = %.2f\n", viewport.getWorldWidth(), viewport.getWorldHeight());
        System.out.printf("world ppm = %d\n", sceneLoader.getPixelsPerWU());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

        // batch.setProjectionMatrix(camera.combined);
        if (width != 0 && height != 0) {
            sceneLoader.resize(width, height);
        }
    }

    // TODO: get real-time world size
    public Vector2 getWorldSize() {
        Vector2 size = new Vector2();
        size.x = viewport.getWorldWidth();
        size.y = viewport.getWorldHeight();
        return size;
    }

    public boolean isOutsideWorld(Vector2 position) {
        System.out.println("position:" + position);
        System.out.println("viewport:" + viewport.getWorldWidth() + "," + viewport.getWorldHeight());
        return position.x < 0 || position.x > viewport.getWorldWidth() || position.y < 0 || position.y > viewport.getWorldHeight();
    }

//    public static Vector2 getConstantWorldSize() {
//        Vector2 size = new Vector2();
//        size.x = WORLD_WIDTH;
//        size.y = WORLD_HEIGHT;
//        return size;
//    }

    public ItemWrapper getPlayer() {
        return rootItemWrapper.getChild("image_aircraft_default");
    }
}
