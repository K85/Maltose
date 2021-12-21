package com.sakurawald.logic.script;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.sakurawald.logic.adapter.PhysicsContactAdapter;
import com.sakurawald.logic.component.DeadlyObstacleComponent;
import com.sakurawald.logic.component.PlayerComponent;
import com.sakurawald.logic.component.StoneComponent;
import com.sakurawald.logic.component.TokenComponent;
import com.sakurawald.logic.enums.GroupIndexes;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.MainItemComponent;
import games.rednblack.editor.renderer.components.TransformComponent;

public class PlayerScript extends ApplicationScript implements PhysicsContactAdapter {

    /* Mappers */
    protected ComponentMapper<TransformComponent> transformMapper;
    protected ComponentMapper<MainItemComponent> mainItemMapper;
    protected ComponentMapper<DeadlyObstacleComponent> deadlyObstacleMapper;
    protected ComponentMapper<DimensionsComponent> dimensionsMapper;
    protected ComponentMapper<PlayerComponent> playerMapper;
    protected ComponentMapper<StoneComponent> stoneComponentMapper;
    protected ComponentMapper<TokenComponent> tokenMapper;

    public PlayerScript(GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    public void physicsBodyComponentInitialized() {
        /* Set the player's collision filter */
        Filter filter = new Filter();
        filter.groupIndex = GroupIndexes.PLAYER_ALLIANCE_NEGATIVE;
        this.getPhysicsBodyComponent().body.getFixtureList().forEach(fixture -> fixture.setFilterData(filter));
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

}