package com.sakurawald.logic.script;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.sakurawald.logic.adapter.PhysicsContactAdapter;
import com.sakurawald.logic.component.PlayerComponent;
import com.sakurawald.logic.component.TokenComponent;
import com.sakurawald.screen.GameScreen;

public class TokenScript extends ApplicationScript implements PhysicsContactAdapter {

    /* Mapper */
    private ComponentMapper<PlayerComponent> playerMapper;
    private ComponentMapper<TokenComponent> tokenMapper;

    public TokenScript(GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    public void beginContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
        Gdx.app.getApplicationLogger().debug("TokenScript", "begin contact: contactEntity = " + contactEntity + ", contactFixture = " + contactFixture + ", ownFixture = " + ownFixture + ", this.Entity = " + this.getEntity());

        TokenComponent tokenComponent = tokenMapper.get(this.getEntity());

        /* Collide With: Player */
        PlayerComponent playerComponent = playerMapper.get(contactEntity);
        if (playerComponent != null && !tokenComponent.ignored) {
            Gdx.app.getApplicationLogger().debug("TokenScript", "Collide with token: " + playerComponent.tokenCollected + ", value = " + tokenComponent.value);
            tokenComponent.ignored = true;
            playerComponent.tokenCollected += tokenComponent.value;
        }
    }
}
