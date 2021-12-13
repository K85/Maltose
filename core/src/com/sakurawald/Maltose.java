package com.sakurawald;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sakurawald.manager.SkinAtlasManager;
import com.sakurawald.manager.TextureAtlasManager;

public class Maltose extends ApplicationAdapter {

    // define Box2D 1 unit = 1 meter (we don't use pixels !)
    private final static float WORLD_SCALE = 1;
    private final static float MAX_VELOCITY = 100;
    private final static Camera camera = new OrthographicCamera();
    //    private final static SceneLoader sceneLoader = new SceneLoader();
    private final static Viewport mViewport = new ExtendViewport(360, 640, 0, 0, camera);

    private final World world = new World(new Vector2(0, 0), true);

    /* Common Props */
    SpriteBatch batch;
    Texture img;
    Skin skin;
    Stage stage;

    private Body body;
    private Body groundBody;

    private Stage stage_hello;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("com/sakurawald/badlogic.jpg");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        /* define skin */
        skin = new Skin();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", new BitmapFont());
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        /* Add components */
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        final TextButton textButton = new TextButton("Click me !", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("clicked! is checked: " + textButton.isChecked());
                textButton.setText("good job !");
            }
        });
        table.add(textButton);


        /* Load a scene */
        stage_hello = SkinAtlasManager.buildStage("hello-layout.json");
        TextButton textbutton_hello = stage_hello.getRoot().findActor("textbutton_hello");
        textbutton_hello.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("you click textbutton hello !!!");
            }
        });


//        /* add a ball */
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.DynamicBody;
//        bodyDef.position.set(0, 0);
//        body = world.createBody(bodyDef);
////        body.applyForceToCenter(new Vector2(0, 100), true);
//        CircleShape circle = new CircleShape();
//        circle.setRadius(6.0f);
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = circle;
//        fixtureDef.density = 0.5f;
//        fixtureDef.friction = 0.4f;
//        fixtureDef.restitution = 0.6f;
//        Fixture fixture = body.createFixture(fixtureDef);
//        circle.dispose();
//
//
//        /* add a floor */
//        BodyDef groundBodyDef = new BodyDef();
//        groundBodyDef.position.set(new Vector2(0, 10));
//        groundBody = world.createBody(groundBodyDef);
//        PolygonShape groundBox = new PolygonShape();
//        groundBox.setAsBox(camera.viewportWidth, 10.0f);
//        groundBody.createFixture(groundBox, 0.0f);
//        groundBox.dispose();
//
//        /* load a object from body-editor */
////        SceneVO sceneVO = sceneLoader.loadScene("MainScene", true);
//        mViewport.apply();
//
////        EntitySystem system = sceneLoader.getEngine().getSystem(EntitySystem.class);
////        sceneLoader.getEngine().update(Gdx.graphics.getDeltaTime());
////        sceneLoader.getRoot().getChild(0);
////        System.out.println(sceneLoader.getSceneVO().sceneName);
//        Engine engine = new Engine();
//        Entity entity = new Entity();
//        entity.add(new PositionComponent());
//        entity.add(new VelocityComponent());
//        engine.addEntity(entity);
//        Family family = Family.all(PositionComponent.class, VelocityComponent.class).get();
//        engine.update(Gdx.graphics.getDeltaTime());


    }


    @Override
    public void render() {
        ScreenUtils.clear(1, 0, 0, 1);

        stage.act(1 / 60.0f);
        stage.draw();


//        sceneLoader.getRoot().draw(batch, 0.5f);
		batch.begin();
//		batch.draw(img, 0, 0);
        batch.draw(TextureAtlasManager.MALTOSE_ATLAS.findRegion("itch/gamesupply/space_ship_samples/AngelWing_A_150x100"),0,0);
		batch.end();

        /* draw and act stage */
        stage_hello.draw();
        stage_hello.act();

//
//        world.step(1 / 60f, 6, 2);
//
//        Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
//        debugRenderer.render(world, camera.combined);
//
//        // move object
//        Vector2 vel = this.body.getLinearVelocity();
//        Vector2 pos = this.body.getPosition();
//        if (Gdx.input.isKeyPressed(Input.Keys.A) && vel.x > -MAX_VELOCITY) {
//            body.applyLinearImpulse(-0.80f, 0, pos.x, pos.y, true);
//        }
//
//        if (Gdx.input.isKeyPressed(Input.Keys.D) && vel.x < MAX_VELOCITY) {
//            body.applyLinearImpulse(0.80f, 0, pos.x, pos.y, true);
//        }
//
//        System.out.println(Controllers.getControllers().size + " controllers");

    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
