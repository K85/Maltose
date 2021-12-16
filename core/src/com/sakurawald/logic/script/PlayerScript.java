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
import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.MainItemComponent;
import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.physics.PhysicsContact;
import games.rednblack.editor.renderer.scripts.BasicScript;
import games.rednblack.editor.renderer.utils.ItemWrapper;

public class PlayerScript extends BasicScript implements PhysicsContact {

    protected com.artemis.World mEngine;
    protected ComponentMapper<PhysicsBodyComponent> physicsMapper;
    protected ComponentMapper<TransformComponent> transformMapper;
    protected ComponentMapper<PlayerComponent> playerMapper;
    protected ComponentMapper<MainItemComponent> mainItemMapper;
    protected ComponentMapper<DeadlyObstacleComponent> diamondMapper;
    protected ComponentMapper<DimensionsComponent> dimensionsMapper;

    public static final float PLAYER_MAX_VELOCITY = 1;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;

    private int animEntity;
    private PhysicsBodyComponent mPhysicsBodyComponent;

    @Override
    public void init(int item) {
        super.init(item);

        ItemWrapper itemWrapper = new ItemWrapper(item, mEngine);
//        animEntity = itemWrapper.getChild("player-anim").getEntity();
        mPhysicsBodyComponent = physicsMapper.get(item);
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

        System.out.println("player script: movePlayer");
        Body body = mPhysicsBodyComponent.body;

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
        return playerMapper.get(animEntity);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void beginContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
        MainItemComponent mainItemComponent = mainItemMapper.get(contactEntity);

        PlayerComponent playerComponent = playerMapper.get(animEntity);
        if (mainItemComponent.tags.contains("platform"))
            playerComponent.touchedPlatforms++;

        DeadlyObstacleComponent deadlyObstacleComponent = diamondMapper.get(contactEntity);
        if (deadlyObstacleComponent != null) {
//            playerComponent.diamondsCollected += diamondComponent.value;
            mEngine.delete(contactEntity);
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