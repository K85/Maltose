package com.sakurawald.logic.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.sakurawald.manager.ApplicationAssetManager;
import com.sakurawald.screen.GameScreen;
import com.talosvfx.talos.runtime.ParticleEffectDescriptor;
import com.talosvfx.talos.runtime.ParticleEffectInstance;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import lombok.Getter;

public class StoneParticleScript extends ApplicationScript {

    private static final ParticleEffectDescriptor fireParticleEffectDescriptor = new ParticleEffectDescriptor(Gdx.files.internal("particle/fire.p"), ApplicationAssetManager.getInstance().getTextureAtlas());
    @Getter
    private ParticleEffectInstance particleEffectInstance;

    public StoneParticleScript(GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    public void doInit(int attachedEntityID) {

        // Construct the particle instance
        particleEffectInstance = fireParticleEffectDescriptor.createEffectInstance();
        particleEffectInstance.loopable = true;

        // Add the particle effect to the particle manager
        this.getGameScreen().getParticleManager().getParticleEffectInstances().add(particleEffectInstance);
    }

    @Override
    public void act(float delta) {

        /* Get the position of the stone */
        PhysicsBodyComponent physicsBodyComponent = this.getPhysicsBodyComponent();
        // Concurrency issue
        if (physicsBodyComponent.body == null) return;
        Vector2 position = physicsBodyComponent.body.getPosition();

        System.out.println("Stone position: " + position);

        /* Set the fire particle to the stone */
        particleEffectInstance.setPosition(position.x, position.y);
        particleEffectInstance.setPosition(0,0);

    }

    @Override
    public void dispose() {
        particleEffectInstance.allowCompletion();
        Gdx.app.getApplicationLogger().debug("StoneParticleScript", "Disposing particle effect instance: " + particleEffectInstance);
        this.getGameScreen().getParticleManager().getParticleEffectInstances().remove(this.particleEffectInstance);
    }

}