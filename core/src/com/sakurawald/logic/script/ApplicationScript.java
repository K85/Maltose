package com.sakurawald.logic.script;

import com.artemis.ComponentMapper;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.scripts.BasicScript;
import lombok.Getter;

public abstract class ApplicationScript extends BasicScript {

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
        this.physicsBodyComponent = this.physicsBodyMapper.get(attachedEntityID);
        this.doInit(attachedEntityID);
    }


    public abstract void doInit(int attachedEntityID);
}
