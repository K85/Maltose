package com.sakurawald.logic.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.sakurawald.logic.adapter.PhysicsContactAdapter;
import com.sakurawald.logic.enums.GroupIndexes;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;

public class BulletScript extends ApplicationScript implements PhysicsContactAdapter {

    public BulletScript(GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    public void physicsBodyComponentInitialized() {
        /* Set the bullet's collision filter */
        Filter filter = new Filter();
        filter.groupIndex = GroupIndexes.PLAYER_ALLIANCE_NEGATIVE;
        this.getPhysicsBodyComponent().body.getFixtureList().forEach(fixture -> fixture.setFilterData(filter));

        /* Apply the initial velocity to the bullet */
        Body playerBody = this.getGameScreen().getPlayerManager().getSolePlayer().getPlayerItemWrapper().getComponent(PhysicsBodyComponent.class).body;

        Vector2 orientation = playerBody.getLinearVelocity();
        Vector2 velocity = new Vector2(0, 2);

        /* Apply the impulse to the bullet */
        this.getPhysicsBodyComponent().body.applyLinearImpulse(velocity, this.getPhysicsBodyComponent().body.getWorldCenter(), true);
    }

    @Override
    public void doAct(float delta) {

        // Remove this bullet if the velocity is zero
        Body body = this.getPhysicsBodyComponent().body;
        if (body != null && body.getLinearVelocity().epsilonEquals(0,0)) {
            this.getEngine().delete(this.getEntity());
        }

    }
}
