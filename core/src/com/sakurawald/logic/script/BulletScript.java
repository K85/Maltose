package com.sakurawald.logic.script;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sakurawald.logic.adapter.PhysicsContactAdapter;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;

public class BulletScript extends ApplicationScript implements PhysicsContactAdapter {

    public BulletScript(GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    public void physicsBodyComponentInitialized() {
        /* Apply the initial velocity to the bullet */
        Vector2 velocity = new Vector2(0, 2);

        Body bulletBody = this.getPhysicsBodyComponent().body;

        /* Apply the impulse to the bullet */
        bulletBody.applyLinearImpulse(velocity, bulletBody.getWorldCenter(), true);
    }

    @Override
    public void doAct(float delta) {
        // Remove this bullet if the velocity is zero
        Body body = this.getPhysicsBodyComponent().body;
        if (body != null && body.getLinearVelocity().epsilonEquals(0, 0)) {
            this.getEngine().delete(this.getEntity());
        }
    }
}
