package com.sakurawald.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sakurawald.logic.component.DeadlyObstacleComponent;
import com.sakurawald.logic.component.PlayerComponent;
import com.sakurawald.logic.entity.Tags;
import com.sakurawald.logic.script.PlayerScript;
import com.sakurawald.logic.system.CameraSystem;
import com.sakurawald.manager.ApplicationAssetManager;
import games.rednblack.editor.renderer.SceneConfiguration;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.data.CompositeItemVO;
import games.rednblack.editor.renderer.factory.v2.ComponentFactoryV2;
import games.rednblack.editor.renderer.factory.v2.EntityFactoryV2;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import games.rednblack.editor.renderer.utils.ItemWrapper;

public class GameScreen extends ApplicationScreen {

    /* Constants */
    protected static final float STEP_TIME = 1 / FPS;
    protected static final float VELOCITY_ITERATIONS = 6;
    protected static final float POSITION_ITERATIONS = 2;
    public final static float MAX_VELOCITY = 10;

    private final static float VIRTUAL_RESOLUTION_WIDTH = 1280;
    private final static float VIRTUAL_RESOLUTION_HEIGHT = 720;
    private final static float PPMU = 80.0f;

    // define Per Pixel Map Unit

    /* Common Props */
    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final OrthographicCamera camera = new OrthographicCamera();
    private Viewport viewport;

    private final Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();

    private SceneLoader sceneLoader;
    private SceneConfiguration sceneConfiguration;
    private ItemWrapper rootItemWrapper;

    @Override
    public void show() {

        /* Init the Virtural Environment */
        Box2D.init();
        viewport = new ExtendViewport(VIRTUAL_RESOLUTION_WIDTH / PPMU, VIRTUAL_RESOLUTION_HEIGHT / PPMU
                , camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);


        /* Load object from .json */
        CameraSystem cameraSystem = new CameraSystem(5, 16, 5, 12);
        sceneConfiguration = new SceneConfiguration();
        sceneConfiguration.setResourceRetriever(ApplicationAssetManager.getInstance().getAsyncResourceLoader());
        sceneConfiguration.addSystem(cameraSystem);

        sceneLoader = ApplicationAssetManager.getInstance().makeSceneLoader(sceneConfiguration);
        sceneLoader.loadScene("MainScene", viewport);
        rootItemWrapper = new ItemWrapper(sceneLoader.getRoot(), sceneLoader.getEngine());

//        /* Add Systems */
        ComponentRetriever.addMapper(PlayerComponent.class);
        ComponentRetriever.addMapper(DeadlyObstacleComponent.class);
        sceneLoader.addComponentByTagName(Tags.DEADLY_OBSTACLE, DeadlyObstacleComponent.class);
        ComponentRetriever.addMapper(DeadlyObstacleComponent.class);

        /* Add Scripts */
        ItemWrapper player = rootItemWrapper.getChild("image_aircraft_default");
        player.addScript(new PlayerScript());

        cameraSystem.setFocus(player.getEntity());

        /* Add Actions */
//        Entity entity = rootItemWrapper.getChild("image_mc").getEntity();
//        ActionData rotation = Actions.sequence(
//                Actions.delay(2),
//                Actions.parallel(
//                        Actions.moveBy(-30, -30, 5, Interpolation.pow2),
//                        Actions.rotateBy(180, 2, Interpolation.exp5))
//        );
//        ActionData repeatData = Actions.forever(rotation);
//        Actions.addAction(sceneLoader.getEngine(), entity, repeatData);

        float posX = 0;
        float posY = 0;
        float posDelta = 1;
        for (int i = 0; i < 10; i++) {
            CompositeItemVO library_stone = sceneLoader.loadVoFromLibrary("library_stone");
            library_stone.layerName= "Default";
            library_stone.x = posX + i * 1;
            library_stone.y = posY + i * 1;
            System.out.printf("tags.length = %d\n", library_stone.tags.length);

            ApplicationAssetManager.loadCompositeFromLib(sceneLoader, "library_stone","Default", posX, posY, DeadlyObstacleComponent.class);

//            int entity = sceneLoader.getEntityFactoryV2().createEntity(rootItemWrapper.getEntity(),
//                    EntityFactoryV2.COMPOSITE_TYPE, );
//            sceneLoader.getEntityFactory().initAllChildren(sceneLoader.getEngine(), e, vo.composite);
//            sceneLoader.getEngine().addEntity(e);

        }


    }

    @Override
    public void render(float delta) {
        Gdx.app.log("GameScreen", "render");

        System.out.printf("frame per second: %d", Gdx.graphics.getFramesPerSecond());
        ScreenUtils.clear(1, 1, 1, 1);

        viewport.apply();
        sceneLoader.getEngine().process();

//        System.out.printf("world body count = %d\n", sceneLoader.getWorld().getBodyCount());
//        System.out.printf("world fixture count = %d\n", sceneLoader.getWorld().getFixtureCount());
//        System.out.printf("world contact count = %d\n", sceneLoader.getWorld().getContactCount());
//        System.out.printf("world joint count = %d\n", sceneLoader.getWorld().getJointCount());
//        System.out.printf("world proxy count = %d\n", sceneLoader.getWorld().getProxyCount());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

        // batch.setProjectionMatrix(camera.combined);
        if (width != 0 && height != 0) {
            sceneLoader.resize(width, height);
        }
    }


}
