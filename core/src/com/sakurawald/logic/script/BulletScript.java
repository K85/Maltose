package com.sakurawald.logic.script;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.sakurawald.logic.adapter.PhysicsContactAdapter;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.physics.PhysicsContact;
import games.rednblack.editor.renderer.scripts.IScript;

public class BulletScript extends ApplicationScript implements PhysicsContactAdapter {

    public BulletScript(GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    public void doInit(int attachedEntityID) {}

    @Override
    public void act(float delta) {



    }

    @Override
    public void dispose() {

    }

    @Override
    public void beginContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

    }

}
