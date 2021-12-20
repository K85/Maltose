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
import com.sakurawald.logic.component.StoneComponent;
import com.sakurawald.logic.component.TokenComponent;
import com.sakurawald.logic.enums.PlayerControls;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.MainItemComponent;
import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.physics.PhysicsContact;

public class PlayerScript extends ApplicationScript implements PhysicsContact {

    /* Constants */
    public static final float PLAYER_MAX_VELOCITY = 1;

    /* Entities */
//    private int animEntity;

    /* Mappers */
    protected ComponentMapper<TransformComponent> transformMapper;
    protected ComponentMapper<PlayerComponent> playerMapper;
    protected ComponentMapper<MainItemComponent> mainItemMapper;
    protected ComponentMapper<DeadlyObstacleComponent> deadlyObstacleMapper;
    protected ComponentMapper<StoneComponent> stoneComponentMapper;
    protected ComponentMapper<TokenComponent> tokenMapper;
    protected ComponentMapper<DimensionsComponent> dimensionsMapper;

    public PlayerScript(GameScreen gameScreen) {
        super(gameScreen);
    }


    @Override
    public void doInit(int attachedEntityID) {
//        ItemWrapper itemWrapper = new ItemWrapper(entity, mEngine);
//        animEntity = itemWrapper.getChild("player-anim").getEntity();
    }

    @Override
    public void act(float delta) {
        /* Handle inputs */
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movePlayer(PlayerControls.MOVE_LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movePlayer(PlayerControls.MOVE_RIGHT);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            movePlayer(PlayerControls.MOVE_UP);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            movePlayer(PlayerControls.MOVE_DOWN);
        }

    }

    public void movePlayer(PlayerControls playerControls) {
        Body body = this.getPhysicsBodyComponent().body;
        Gdx.app.getApplicationLogger().debug("PlayerScript", "movePlayer: currentPosition = " + body.getPosition() + ", currentVelocity = " + body.getLinearVelocity());

        /* Check map boundary */
//        if (gameScreen.isOutsideWorld(body.getPosition())) {
//            Vector2 playerPosition = body.getPosition();
//            Vector2 targetPosition = new Vector2(RESET_POSITION_X, RESET_POSITION_Y);
//            targetPosition.sub(playerPosition);
//            targetPosition.set(targetPosition.x * -1, targetPosition.y * -1);
//            body.setTransform(targetPosition, body.getAngle());
//
//            return;
//        }

        /* Apply impulse */
        switch (playerControls) {
            case MOVE_LEFT:
                if (body.getLinearVelocity().x > -PLAYER_MAX_VELOCITY) {
                    body.applyLinearImpulse(new Vector2(-PLAYER_MAX_VELOCITY, 0), body.getWorldCenter(), true);
                }
                break;
            case MOVE_RIGHT:
                if (body.getLinearVelocity().x < PLAYER_MAX_VELOCITY) {
                    body.applyLinearImpulse(new Vector2(PLAYER_MAX_VELOCITY, 0), body.getWorldCenter(), true);
                }
                break;
            case MOVE_UP:
                if (body.getLinearVelocity().y < PLAYER_MAX_VELOCITY) {
                    body.applyLinearImpulse(new Vector2(0, PLAYER_MAX_VELOCITY), body.getWorldCenter(), true);
                }
                break;
            case MOVE_DOWN:
                if (body.getLinearVelocity().y > -PLAYER_MAX_VELOCITY) {
                    body.applyLinearImpulse(new Vector2(0, -PLAYER_MAX_VELOCITY), body.getWorldCenter(), true);
                }
                break;
        }
    }

    public PlayerComponent getPlayerComponent() {
        return playerMapper.get(this.getEntity());
    }

    @Override
    public void dispose() {
    }

    @Override
    public void beginContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
        Gdx.app.getApplicationLogger().log("beginContact", "beginContact: contactEntity = " + contactEntity + ", contactFixture = " + contactFixture.getBody().getPosition() + ", ownFixture = " + ownFixture.getBody().getPosition());

//        MainItemComponent mainItemComponent = mainItemMapper.get(contactEntity);
//        PlayerComponent playerComponent = playerMapper.get();
//        if (mainItemComponent.tags.contains("platform"))
//            playerComponent.touchedPlatforms++;

        /* Collide with: StoneComponent */
        StoneComponent stoneComponent = stoneComponentMapper.get(contactEntity);
        if (stoneComponent != null && !stoneComponent.ignored) {
            Gdx.app.getApplicationLogger().debug("PlayerScript", "beginContact: stone = " + stoneComponent);
            stoneComponent.ignored = true;
            this.getEngine().delete(contactEntity);
            this.getPlayerComponent().leftLives--;
        }

        /* Collide With: Token */
        TokenComponent tokenComponent = tokenMapper.get(contactEntity);
        if (tokenComponent != null && !tokenComponent.ignored) {
            Gdx.app.getApplicationLogger().debug("PlayerScript", "Collide with token: " + getPlayerComponent().tokenCollected);
            tokenComponent.ignored = true;
            this.getEngine().delete(contactEntity);
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