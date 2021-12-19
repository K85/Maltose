package com.sakurawald.logic.script;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.sakurawald.logic.component.BoundaryComponent;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.physics.PhysicsContact;
import games.rednblack.editor.renderer.utils.ItemWrapper;

public class DestroyedByBoundary extends ApplicationScript implements PhysicsContact {

    protected ComponentMapper<BoundaryComponent> boundaryMapper;

    public DestroyedByBoundary(GameScreen gameScreen) {
        super(gameScreen);
    }


    @Override
    public void doInit(int attachedEntityID) {

    }

    @Override
    public void beginContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

        /* Collide with: Boundary */
        BoundaryComponent boundaryComponent = boundaryMapper.get(contactEntity);
        if (boundaryComponent != null) {
            Gdx.app.getApplicationLogger().debug("BoundaryAutoDestroyScript", "Boundary Auto Destroy EntityID: " + this.getEntity());

            ItemWrapper itemWrapper = new ItemWrapper(this.getEntity(), this.getEngine());

            // TODO add some particle effect

            this.getEngine().delete(this.getEntity());
        }
    }

    @Override
    public void endContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

    }

    @Override
    public void preSolve(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

    }

    @Override
    public void postSolve(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void dispose() {

    }

}
