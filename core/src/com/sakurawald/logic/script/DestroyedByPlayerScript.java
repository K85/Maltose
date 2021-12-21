package com.sakurawald.logic.script;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.bullet.collision._btMprSimplex_t;
import com.sakurawald.logic.adapter.PhysicsContactAdapter;
import com.sakurawald.logic.component.BoundaryComponent;
import com.sakurawald.logic.component.PlayerComponent;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.physics.PhysicsContact;
import games.rednblack.editor.renderer.utils.ItemWrapper;

public class DestroyedByPlayerScript extends ApplicationScript implements PhysicsContactAdapter {

    protected ComponentMapper<PlayerComponent> playerMapper;

    public DestroyedByPlayerScript(GameScreen gameScreen) {
        super(gameScreen);
    }


    @Override
    public void beginContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

        /* Collide with: Boundary */
        PlayerComponent playerComponent = playerMapper.get(contactEntity);
        if (playerComponent != null) {
            Gdx.app.getApplicationLogger().debug("PlayerAutoDestroyScript", "Player Auto Destroy EntityID: " + this.getEntity());
            this.getEngine().delete(this.getEntity());
        }
    }

    @Override
    public void dispose() {

    }

}
