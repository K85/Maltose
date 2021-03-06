package com.sakurawald.logic.script;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.sakurawald.logic.adapter.PhysicsContactAdapter;
import com.sakurawald.logic.component.BoundaryComponent;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;

public class DestroyedByBoundaryScript extends ApplicationScript implements PhysicsContactAdapter {

    protected ComponentMapper<BoundaryComponent> boundaryMapper;
    protected ComponentMapper<PhysicsBodyComponent> physicsBodyMapper;

    public DestroyedByBoundaryScript(GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    public void beginContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

        Gdx.app.getApplicationLogger().debug("DestroyedByBoundaryScript", "beginContact: contactEntity = " + contact + ", contactFixture = " + contact + ", ownFixture = " + ownFixture);

        /* Collide with: Boundary */
        BoundaryComponent boundaryComponent = boundaryMapper.get(contactEntity);
        if (boundaryComponent != null) {
            Gdx.app.getApplicationLogger().debug("DestroyedByBoundaryScript", "Boundary Auto Destroy EntityID: " + this.getEntity());

            try {
                this.getEngine().delete(this.getEntity());
            } catch (IndexOutOfBoundsException e) {
                Gdx.app.getApplicationLogger().debug("DestroyedByBoundaryScript", "the Entity is already removed: " + this.getEntity());
            }
        }
    }

}
