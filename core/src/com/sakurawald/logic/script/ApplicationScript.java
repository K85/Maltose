package com.sakurawald.logic.script;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.scripts.BasicScript;
import lombok.Getter;

public abstract class ApplicationScript extends BasicScript {

    /** the BasicScript#init may be called more than once, so we just use our oun flag to ensure the safeInit() works correctly */
    private boolean safeInitFlag = false;

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
    public final void init(int item) {
        super.init(item);
        if (this.safeInitFlag) return;
        else this.safeInitFlag = true;

        // Do init.
        Gdx.app.getApplicationLogger().debug("ApplicationScript", "init() -> doInit(): entityID = " + this.getEntity());
        this.safeInit(item);
    }

    public void safeInit(int item) {
        // do nothing.
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
        Gdx.app.getApplicationLogger().debug("ApplicationScript", "act() -> doAct(): entityID = " + this.getEntity());
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
    public void dispose() {
        // do nothing
    }

    /** is the Entity has dead in the ECS system */
    public boolean isEntityDead() {
        return this.getEntity() == -1;
    }
}
