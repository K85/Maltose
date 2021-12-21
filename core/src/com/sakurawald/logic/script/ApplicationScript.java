package com.sakurawald.logic.script;

import com.artemis.ComponentMapper;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.scripts.BasicScript;
import lombok.Getter;

public abstract class ApplicationScript extends BasicScript {

    private boolean isInit = false;

    /* GameScreen */
    @Getter
    private final GameScreen gameScreen;

    /* Engines */
    @Getter
    private com.artemis.World engine;

    /* Mappers */
    @Getter
    private ComponentMapper<PhysicsBodyComponent> physicsBodyMapper;

    /* Components */
    @Getter
    // the physicsBodyComponent may be null in init() method, but is non-null in act() method
    private PhysicsBodyComponent physicsBodyComponent;

    public ApplicationScript(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public final void init(int attachedEntityID) {
        super.init(attachedEntityID);

        /* Avoid Multiple Calls (the ScriptSystem in HyperLap2D may call Script#init more than once) */
        if (this.isInit) return;
        else this.isInit = true;

        /* Do init */
//        this.physicsBodyComponent = this.physicsBodyMapper.get(attachedEntityID);
//        this.physicsBodyComponent = getGameScreen().getSceneLoader().getEngine().getEntity(this.entity).getComponent(PhysicsBodyComponent.class);
        this.doInit(attachedEntityID);
    }


    public void doInit(int attachedEntityID) {
        // do nothing
    }

    @Override
    public final void act(float delta) {
        // physicsBodyComponent and physicsBodyComponent.body are not always set to non-null value at the same time.
        if (this.physicsBodyComponent == null || this.physicsBodyComponent.body == null) {
            this.trySetPhysicsBodyComponent();
            if (this.physicsBodyComponent != null && this.physicsBodyComponent.body != null) {
                this.physicsBodyComponentInitialized();
            }
        }
        // Do act
        this.doAct(delta);
    }

    public void doAct(float delta) {
        // do nothing
    }

    public void physicsBodyComponentInitialized() {
        // do nothing.
    }

    /**
     * this method will set PhysicsBodyComponent to non-null value after act() method
     */
    public final void trySetPhysicsBodyComponent() {
        this.physicsBodyComponent = this.physicsBodyMapper.get(this.entity);
    }

    @Override
    public void reset() {
        super.reset();
        System.out.println("WARNING ApplicationScript reset() is called !");
    }

    @Override
    public void dispose() {
        // do nothing
    }
}
