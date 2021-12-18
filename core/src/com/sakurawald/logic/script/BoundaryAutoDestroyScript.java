package com.sakurawald.logic.script;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.physics.box2d.*;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.physics.PhysicsContact;
import games.rednblack.editor.renderer.scripts.BasicScript;
import lombok.Getter;

public class BoundaryAutoDestroyScript extends BasicScript implements PhysicsContact {

    @Getter
    private GameScreen gameScreen;

    //No needs to init these fields because scripts are injected using artemis
    protected ComponentMapper<PhysicsBodyComponent> physicMapper;
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

        System.out.println("boundary auto destroyed script: begin contact");
        // Collide with: Boundary
        Body body = physicMapper.get(contactEntity).body;

        Long bodyAddress = Box2DUtils.getAddr(body);

        System.out.printf("contactEntity address: %d\n", bodyAddress);
        System.out.printf("thisEntity address", Box2DUtils.getAddr(physicMapper.get(this.getEntity()).body));
        for (long boundaryAddress : gameScreen.getBoundaryManager().getBoundaryBodies()) {
            System.out.printf("boundary address: %d\n", boundaryAddress);
        }

        if (this.gameScreen.getBoundaryManager().getBoundaryBodies().contains(bodyAddress)) {
            System.out.println("collides with boundary !!!!!!!");

//            engine.delete(this.getEntity());
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
