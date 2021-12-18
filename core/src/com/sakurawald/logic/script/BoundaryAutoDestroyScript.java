package com.sakurawald.logic.script;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.physics.box2d.*;
import com.sakurawald.manager.BoundaryManager;
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
        System.out.println("");
        Long bodyAddress = Box2DUtils.getAddr(body);


        System.out.println("body fixture desity: %.6f\n" + contactFixture.getDensity());
        System.out.println("body fixture damp: %.6f\n" + body.getLinearDamping());
        System.out.printf("contactEntity address: %d\n", bodyAddress);

        System.out.printf("contaftFixture.body.address: %d\n", Box2DUtils.getAddr(contactFixture.getBody()));
        System.out.printf("ownFixture.body.address: %d\n", Box2DUtils.getAddr(ownFixture.getBody()));

        System.out.printf("contact.getFixtureA%d\n", Box2DUtils.getAddr(contact.getFixtureA()));
        System.out.printf("contact.getFixtureB%d\n", Box2DUtils.getAddr(contact.getFixtureB()));


        System.out.printf("thisEntity address: %d\n", Box2DUtils.getAddr(physicMapper.get(this.getEntity()).body));
//        for (long boundaryAddress : gameScreen.getBoundaryManager().getBoundaryShapes()) {
//            System.out.printf("boundary address: %d\n", boundaryAddress);
//        }

        System.out.println("body.getGravityScale: " + body.getGravityScale());
        System.out.printf("BoundaryManager.Boundary_Flag: %.6f\n", BoundaryManager.BOUNDARY_FLAG);


        System.out.println("body shape: " + contactFixture.getShape());
        if (gameScreen.getBoundaryManager().getBoundaryShapes().contains(contactFixture.getShape())) {
            System.out.println("collides with boundary !!!!!!!");
            engine.delete(this.getEntity());
        }


    }

    @Override
    public void endContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

    }

    @Override
    public void preSolve(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

        Body body = physicMapper.get(contactEntity).body;
        System.out.printf("presolve -> body.getGravityScale: %.6f\n", body.getGravityScale());
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
