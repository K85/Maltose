package com.sakurawald.logic.script;

import com.artemis.ComponentMapper;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.scripts.BasicScript;
import lombok.Getter;

public abstract class ApplicationScript extends BasicScript {

    /* GameScreen */
    @Getter
    protected GameScreen gameScreen;

    /* Engines */
    @Getter
    protected com.artemis.World engine;

    /* Mappers */
    protected ComponentMapper<PhysicsBodyComponent> physicsBodyMapper;

    /* Components */
    protected PhysicsBodyComponent physicsBodyComponent;

    public ApplicationScript(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public final void init(int attachedEntityID) {
        super.init(attachedEntityID);
        physicsBodyComponent = physicsBodyMapper.get(attachedEntityID);
        doInit(attachedEntityID);
    }


    public abstract void doInit(int attachedEntityID);
}
