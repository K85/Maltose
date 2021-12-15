package com.sakurawald.screen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sakurawald.data.component.DiamondComponent;
import com.sakurawald.data.component.PlayerComponent;
import com.sakurawald.data.system.CameraSystem;
import com.sakurawald.manager.ApplicationAssetManager;
import com.sakurawald.manager.TextureAtlasManager;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.systems.action.ActionSystem;
import games.rednblack.editor.renderer.systems.action.Actions;
import games.rednblack.editor.renderer.systems.action.data.ActionData;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import games.rednblack.editor.renderer.utils.ItemWrapper;

public class GameScreen extends ApplicationScreen {

    /* Constants */
    // define Box2D 1 unit = 1 meter (we don't use pixels !)
    private final static float WORLD_SCALE = 1;
    private final static float MAX_VELOCITY = 100;

    /* Common Props */
    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final Camera camera = new OrthographicCamera();
    private final Viewport viewport = new ScreenViewport(camera);
    private final Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();

    Texture texture = TextureAtlasManager.MALTOSE_ATLAS.findRegion("com/sakurawald/badlogic").getTexture();
    private SceneLoader sceneLoader;

    @Override
    public void show() {

        Box2D.init();


//        /* Add Systems */
//        CameraSystem cameraSystem = new CameraSystem(5, 50, 5, 6);this.getSceneLoader().getEngine().addSystem(cameraSystem);
////        this.getSceneLoader().getEngine().addSystem(new PlayerAnimationSystem());
//
//        ComponentRetriever.addMapper(PlayerComponent.class);
//        ComponentRetriever.addMapper(DiamondComponent.class);

        /* Load object from .json */
        ApplicationAssetManager.getInstance().getSceneLoader().loadScene("MainScene", viewport);

        /* Create BodyDef and Shape */
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);

        CircleShape circleShape = new CircleShape();
        circleShape.setPosition(new Vector2(0, 0));
        circleShape.setRadius(5f);

//        Body body = sceneLoader.getWorld().createBody(bodyDef);
//        body.createFixture(circleShape, 1);


        /* Add Actions */
        ItemWrapper root = new ItemWrapper(sceneLoader.getRoot());
        Entity entity = root.getChild("image_mc").getEntity();

            ActionData rotation = Actions.sequence(
                Actions.delay(2),
                Actions.parallel(
                        Actions.moveBy(-30, -30, 5, Interpolation.pow2),
                        Actions.rotateBy(180, 2, Interpolation.exp5))
        );
        ActionData repeatData = Actions.forever(rotation);
        Actions.addAction(sceneLoader.getEngine(), entity, repeatData);
    }

    @Override
    public void render(float delta) {
        Gdx.app.log("GameScreen", "render");

        camera.update();
        ScreenUtils.clear(1, 1, 1, 1);

        viewport.apply();
        sceneLoader.getEngine().update(Gdx.graphics.getDeltaTime());

        System.out.printf("world body count = %d", sceneLoader.getWorld().getBodyCount());
        System.out.printf("world fixture count = %d", sceneLoader.getWorld().getFixtureCount());
        System.out.printf("world contact count = %d", sceneLoader.getWorld().getContactCount());
        System.out.printf("world joint count = %d", sceneLoader.getWorld().getJointCount());
        System.out.printf("world proxy count = %d", sceneLoader.getWorld().getProxyCount());
    }

}
