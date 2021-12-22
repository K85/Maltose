package com.sakurawald.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sakurawald.logic.bean.Player;
import com.sakurawald.logic.bean.PlayerControllerListener;
import com.sakurawald.logic.component.*;
import com.sakurawald.logic.entity.Tags;
import com.sakurawald.logic.script.PlayerScript;
import com.sakurawald.logic.stage.ScoreBoardHUD;
import com.sakurawald.logic.system.CameraSystem;
import com.sakurawald.manager.*;
import com.sakurawald.timer.SpawnStoneTask;
import com.sakurawald.timer.SpawnTokenTask;
import games.rednblack.editor.renderer.SceneConfiguration;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.data.ResolutionEntryVO;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import games.rednblack.editor.renderer.utils.ItemWrapper;
import lombok.Getter;

import java.util.ArrayList;

public class GameScreen extends ApplicationScreen {

    /* Constants */
//    protected static final float STEP_TIME = 1 / FPS;

    /* World Properties */
    public float VIRTUAL_RESOLUTION_WIDTH;
    public float VIRTUAL_RESOLUTION_HEIGHT;
    public float PPWU;
    public float WORLD_WIDTH;
    public float WORLD_HEIGHT;

    /* SpriteBatch */
    private final SpriteBatch spriteBatch = new SpriteBatch();

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

    /* HyperLap2D */
    @Getter
    private SceneLoader sceneLoader;
    @Getter
    private SceneConfiguration sceneConfiguration;

    @Getter
    private ItemWrapper rootItemWrapper;

    /* ScoreBoard */
    private ScoreBoardHUD scoreBoard;

    /* PlayerManager */
    @Getter
    private final PlayerManager playerManager = new PlayerManager(this);

    /* BulletManager */
    @Getter
    private final BulletManager bulletManager = new BulletManager(this);

    /* BoundaryManager */
    @Getter
    private final BoundaryManager boundaryManager = new BoundaryManager(this);

    /* Talos */
    @Getter
    private final ParticleManager particleManager = new ParticleManager(this);

    @Override
    public void show() {

        /* Init Box2D */
        Box2D.init();

        /* Camera and Viewport */
        viewport = new ExtendViewport(0,  0, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        /* Add System and Load Scene */
        CameraSystem cameraSystem = new CameraSystem(5, 16, 5, 12);
        sceneConfiguration = new SceneConfiguration();
        sceneConfiguration.setResourceRetriever(ApplicationAssetManager.getAsyncResourceLoader());
        sceneConfiguration.addSystem(cameraSystem);

        sceneLoader = ApplicationAssetManager.buildSceneLoader(sceneConfiguration);
        sceneLoader.loadScene("MainScene", viewport);

        rootItemWrapper = new ItemWrapper(sceneLoader.getRoot(), sceneLoader.getEngine());

        /* Set World Constants */
        ResolutionEntryVO resolutionEntryVO = this.getProjectOriginalResolution();
        VIRTUAL_RESOLUTION_WIDTH = resolutionEntryVO.width;
        VIRTUAL_RESOLUTION_HEIGHT = resolutionEntryVO.height;
        PPWU = this.getProjectPPWU();
        WORLD_WIDTH = VIRTUAL_RESOLUTION_WIDTH / PPWU;
        WORLD_HEIGHT = VIRTUAL_RESOLUTION_HEIGHT / PPWU;

        // Assign Viewport
        ExtendViewport extendViewport = (ExtendViewport) viewport;
        extendViewport.setMinWorldWidth(WORLD_WIDTH);
        extendViewport.setMinWorldHeight(WORLD_HEIGHT);

        /* Add Components */

        // PlayerComponent
        ComponentRetriever.addMapper(PlayerComponent.class);
        sceneLoader.addComponentByTagName(Tags.PLAYER, PlayerComponent.class);

        // BulletComponent
        ComponentRetriever.addMapper(BulletComponent.class);
        sceneLoader.addComponentByTagName(Tags.BULLET, BulletComponent.class);

        // DeadlyObstacleComponent
        ComponentRetriever.addMapper(DeadlyObstacleComponent.class);
        sceneLoader.addComponentByTagName(Tags.DEADLY_OBSTACLE, DeadlyObstacleComponent.class);

        // StoneComponent
        ComponentRetriever.addMapper(StoneComponent.class);
        sceneLoader.addComponentByTagName(Tags.STONE, StoneComponent.class);

        // BoundaryComponent
        ComponentRetriever.addMapper(BoundaryComponent.class);
        sceneLoader.addComponentByTagName(Tags.BOUNDARY, BoundaryComponent.class);

        // TokenComponent
        ComponentRetriever.addMapper(TokenComponent.class);
        sceneLoader.addComponentByTagName(Tags.TOKEN, TokenComponent.class);

        /* Add Scripts */

        // PlayerScript
        Player player = this.getPlayerManager().createPlayer();
        ItemWrapper playerItemWrapper = player.getPlayerItemWrapper();
        PlayerScript playerScript = new PlayerScript(this);
        playerItemWrapper.addScript(playerScript);

        cameraSystem.setFocusEntityID(playerItemWrapper.getEntity());

        /* Add Stages */
        scoreBoard = new ScoreBoardHUD(this, new ExtendViewport(768, 576));

        /* Add Rectangle Boundary */
        boundaryManager.createPolygonBoundary(new ArrayList<>() {
            {
                this.add(new Vector2(0, 0));
                this.add(new Vector2(0, WORLD_HEIGHT));
                this.add(new Vector2(WORLD_WIDTH, WORLD_HEIGHT));
                this.add(new Vector2(WORLD_WIDTH, 0));
            }
        });

        /* Register Timers */
        new SpawnStoneTask(this).scheduleSelf();
        new SpawnTokenTask(this).scheduleSelf();

//        particleEffect = ParticleManager.buildParticleEffect("fire");
//        particleEffect.setPosition(1,1);
//        particleEffect.start();

        /* Set InputProcessor */
        Gdx.input.setInputProcessor(new InputMultiplexer(new PlayerControllerListener(), scoreBoard));
    }

    @Override
    public void render(float delta) {
        Gdx.app.getApplicationLogger().debug("GameScreen", "render");
        ScreenUtils.clear(1, 1, 1, 1);
//        ScreenUtils.clear(0, 0, 0, 1);

        /* Render -> Box2D World */
        this.viewport.apply();
        sceneLoader.getEngine().process();

        /* Render -> PlayerManager */
        this.playerManager.process(delta);

        /* Render -> Particle */
        particleManager.process(delta);

        /* Render -> ScoreBoard */
        this.scoreBoard.act(delta);
        this.scoreBoard.getViewport().apply();
        this.scoreBoard.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Update -> the viewport of GameScreen's camerr
        this.viewport.update(width, height, true);

        // Update -> the viewport of HUDs
        this.scoreBoard.getViewport().update(width, height, true);

        // Update -> SceneLoader (notify the FBO to resize)
        // batch.setProjectionMatrix(camera.combined);
        if (width != 0 && height != 0) {
            sceneLoader.resize(width, height);
        }
    }

    public Vector2 getWorldSize() {
        Vector2 size = new Vector2();
        size.x = viewport.getWorldWidth();
        size.y = viewport.getWorldHeight();
        return size;
    }

    public boolean isOutsideWorld(Vector2 position) {
        return isOutsideWorld(position, 0f);
    }

    public boolean isOutsideWorld(Vector2 position, float delta) {
        Gdx.app.getApplicationLogger().debug("isOutsideWorld", "position = " + position + ", delta = " + delta);
        return position.x < 0 + delta || position.x > getWorldSize().x - delta || position.y < 0 + delta || position.y > getWorldSize().y - delta;
    }

    public ResolutionEntryVO getProjectOriginalResolution() {
        return this.getSceneLoader().getRm().getProjectVO().originalResolution;
    }

    public float getProjectPPWU() {
        return this.getSceneLoader().getRm().getProjectVO().pixelToWorld;
    }
}