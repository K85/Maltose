package com.sakurawald.logic.script;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.sakurawald.logic.adapter.PhysicsContactAdapter;
import com.sakurawald.logic.component.BoundaryComponent;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.physics.PhysicsContact;
import games.rednblack.editor.renderer.utils.ItemWrapper;

public class DestroyedByBoundaryScript extends ApplicationScript implements PhysicsContactAdapter {

    protected ComponentMapper<BoundaryComponent> boundaryMapper;

    public DestroyedByBoundaryScript(GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    public void beginContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

        /* Collide with: Boundary */
        BoundaryComponent boundaryComponent = boundaryMapper.get(contactEntity);
        if (boundaryComponent != null) {

            Gdx.app.getApplicationLogger().debug("BoundaryAutoDestroyScript", "Boundary Auto Destroy EntityID: " + this.getEntity());

            ItemWrapper itemWrapper = new ItemWrapper(this.getEntity(), this.getEngine());
//             TODO add some particle effect
            this.getEngine().delete(this.getEntity());
        }

    }

}
