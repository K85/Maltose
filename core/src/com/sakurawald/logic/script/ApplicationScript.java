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
        this.physicsBodyComponent = this.physicsBodyMapper.get(attachedEntityID);
        this.doInit(attachedEntityID);
    }


    public void doInit(int attachedEntityID) {

    }
}
