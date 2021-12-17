package com.sakurawald.logic.script;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.sakurawald.logic.component.DeadlyObstacleComponent;
import com.sakurawald.logic.component.PlayerComponent;
import com.sakurawald.logic.component.TokenComponent;
import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.MainItemComponent;
import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.physics.PhysicsContact;
import games.rednblack.editor.renderer.scripts.BasicScript;

public class PlayerScript extends BasicScript implements PhysicsContact {

    /* Constants */
    public static final float PLAYER_MAX_VELOCITY = 1;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;

    /* Engine */
    protected com.artemis.World engine;

    /* Player */
//    private int animEntity;
    private int playerEntity;
    private PhysicsBodyComponent mPhysicsBodyComponent;

    /* Mappers */
    protected ComponentMapper<PhysicsBodyComponent> physicsMapper;
    protected ComponentMapper<TransformComponent> transformMapper;
    protected ComponentMapper<PlayerComponent> playerMapper;
    protected ComponentMapper<MainItemComponent> mainItemMapper;
    protected ComponentMapper<DeadlyObstacleComponent> deadlyObstacleMapper;
    protected ComponentMapper<TokenComponent> tokenMapper;
    protected ComponentMapper<DimensionsComponent> dimensionsMapper;

    @Override
    public void init(int entity) {
        // Entity this script is attached to
        super.init(entity);

//        ItemWrapper itemWrapper = new ItemWrapper(entity, mEngine);
//        animEntity = itemWrapper.getChild("player-anim").getEntity();
        playerEntity = entity;
        mPhysicsBodyComponent = physicsMapper.get(entity);
    }

    @Override
    public void act(float delta) {
        /* Handle inputs */
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movePlayer(LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movePlayer(RIGHT);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            movePlayer(UP);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            movePlayer(DOWN);
        }

    }

    public void movePlayer(int direction) {
        Body body = mPhysicsBodyComponent.body;
        Gdx.app.log("PlayerScript", "movePlayer: currentPosition = " + body.getPosition() + ", currentVelocity = " + body.getLinearVelocity());

        /* Check map boundary */
//        Vector2 position = body.getPosition();
//        if (position.x < 0 || position.y < 0 || position.x > GameScreen.WORLD_WIDTH || position.y > GameScreen.WORLD_HEIGHT) {
//            Vector2 velocity = body.getLinearVelocity();
//            velocity.set(-velocity.x, -velocity.y);
//            body.setLinearVelocity(velocity);
//            return;
//        }

        /* Apply impulse */
        switch (direction) {
            case LEFT:
                if (body.getLinearVelocity().x > -PLAYER_MAX_VELOCITY) {
                    body.applyLinearImpulse(new Vector2(-PLAYER_MAX_VELOCITY, 0), body.getWorldCenter(), true);
                }
                break;
            case RIGHT:
                if (body.getLinearVelocity().x < PLAYER_MAX_VELOCITY) {
                    body.applyLinearImpulse(new Vector2(PLAYER_MAX_VELOCITY, 0), body.getWorldCenter(), true);
                }
                break;
            case UP:
                if (body.getLinearVelocity().y < PLAYER_MAX_VELOCITY) {
                    body.applyLinearImpulse(new Vector2(0, PLAYER_MAX_VELOCITY), body.getWorldCenter(), true);
                }
                break;
            case DOWN:
                if (body.getLinearVelocity().y > -PLAYER_MAX_VELOCITY) {
                    body.applyLinearImpulse(new Vector2(0, -PLAYER_MAX_VELOCITY), body.getWorldCenter(), true);
                }
                break;
        }
    }

    public PlayerComponent getPlayerComponent() {
//        return playerMapper.get(animEntity);
        return playerMapper.get(playerEntity);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void beginContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
        MainItemComponent mainItemComponent = mainItemMapper.get(contactEntity);

//        PlayerComponent playerComponent = playerMapper.get();
//        if (mainItemComponent.tags.contains("platform"))
//            playerComponent.touchedPlatforms++;

        /* Collide with: DeadlyObstacle */
        DeadlyObstacleComponent deadlyObstacleComponent = deadlyObstacleMapper.get(contactEntity);
        if (deadlyObstacleComponent != null) {
            Gdx.app.log("PlayerScript", "beginContact: deadlyObstacleComponent = " + deadlyObstacleComponent);
            engine.delete(contactEntity);
            this.getPlayerComponent().leftLives--;
        }

        /* Collide With: Token */
        TokenComponent tokenComponent = tokenMapper.get(contactEntity);
        if (tokenComponent != null) {
            Gdx.app.log("PlayerScript", "Collide with token: " + getPlayerComponent().tokenCollected);
            engine.delete(contactEntity);
            this.getPlayerComponent().tokenCollected += tokenComponent.value;
        }
    }

    @Override
    public void endContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
//        MainItemComponent mainItemComponent = mainItemMapper.get(contactEntity);
//
//        PlayerComponent playerComponent = playerMapper.get(animEntity);
//
//        if (mainItemComponent.tags.contains("platform"))
//            playerComponent.touchedPlatforms--;
    }

    @Override
    public void preSolve(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
//        TransformComponent transformComponent = transformMapper.get(this.entity);
//
//        TransformComponent colliderTransform = transformMapper.get(contactEntity);
//        DimensionsComponent colliderDimension = dimensionsMapper.get(contactEntity);
//
//        if (transformComponent.y < colliderTransform.y + colliderDimension.height) {
//            contact.setFriction(0);
//        } else {
//            contact.setFriction(1);
//        }
    }

    @Override
    public void postSolve(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

    }
}