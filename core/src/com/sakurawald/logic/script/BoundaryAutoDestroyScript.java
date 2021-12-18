package com.sakurawald.logic.script;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.sakurawald.logic.component.BoundaryComponent;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.physics.PhysicsContact;
import games.rednblack.editor.renderer.scripts.BasicScript;
import games.rednblack.editor.renderer.utils.ItemWrapper;
import lombok.Getter;

public class BoundaryAutoDestroyScript extends BasicScript implements PhysicsContact {

    @Getter
    private final GameScreen gameScreen;

    //No needs to init these fields because scripts are injected using artemis
    protected ComponentMapper<PhysicsBodyComponent> physicMapper;
    protected ComponentMapper<BoundaryComponent> boundaryMapper;

    protected com.artemis.World engine;

    private PhysicsBodyComponent physicsBodyComponent;

    public BoundaryAutoDestroyScript(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void init(int item) {
        super.init(item);
        physicsBodyComponent = physicMapper.get(item);
    }

    @Override
    public void beginContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

        /* Collide with: Boundary */
       BoundaryComponent boundaryComponent = boundaryMapper.get(contactEntity);
        if (boundaryComponent != null) {
            Gdx.app.log("BoundaryAutoDestroyScript", "Boundary Auto Destroye EntityID: " + this.getEntity());

            ItemWrapper itemWrapper = new ItemWrapper(this.getEntity(), engine);

            // TODO add some particle effect




            engine.delete(this.getEntity());
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
        // body can be used here, because act method is called after PhysicsSystem,
        //so all bodies should already be created
//        Body body = physicsBodyComponent.body;
    }

    @Override
    public void dispose() {

    }
}
