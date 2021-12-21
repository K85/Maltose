package com.sakurawald.logic.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sakurawald.manager.ParticleManager;
import com.sakurawald.screen.GameScreen;
import com.sakurawald.util.MathUtils;
import com.talosvfx.talos.runtime.ParticleEffectDescriptor;
import com.talosvfx.talos.runtime.ParticleEffectInstance;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import lombok.Getter;

public class StoneScript extends ApplicationScript {

    public static final float STONE_MAX_VELOCITY = 5;

    @Getter
    private static final ParticleEffectDescriptor fireParticleEffectDescriptor = ParticleManager.buildParticleEffectDescriptor("fire.p");

    @Getter
    private ParticleEffectInstance particleEffectInstance;

    public StoneScript(GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    public void physicsBodyComponentInitialized() {
        Body stoneBody = this.getPhysicsBodyComponent().body;

        /* Apply random velocity */
        Vector2 randomVelocity = MathUtils.getRandomVelocity(STONE_MAX_VELOCITY);
        Gdx.app.log("SpawnStoneTask", "Apply stone with velocity " + randomVelocity);
        stoneBody.applyLinearImpulse(randomVelocity, stoneBody.getWorldCenter(), true);
    }

    @Override
    public void doInit(int attachedEntityID) {
        // Construct the particle instance
        particleEffectInstance = fireParticleEffectDescriptor.createEffectInstance();
        particleEffectInstance.loopable = true;

        // Add the particle effect to the particle manager
        Gdx.app.log("StoneParticleScript", "Adding particle effect instance: " + particleEffectInstance);
        this.getGameScreen().getParticleManager().getParticleEffectInstances().add(particleEffectInstance);
    }

    @Override
    public void doAct(float delta) {

        /* Get the position of the stone */
        PhysicsBodyComponent physicsBodyComponent = this.getPhysicsBodyComponent();
        // Concurrency issue
        if (physicsBodyComponent.body == null) return;
        Vector2 position = physicsBodyComponent.body.getPosition();

        /* Set the fire particle to the stone */
        particleEffectInstance.setPosition(position.x, position.y);
        particleEffectInstance.update(delta);
    }

    @Override
    public void dispose() {
        particleEffectInstance.allowCompletion();
        Gdx.app.getApplicationLogger().debug("StoneParticleScript", "Disposing particle effect instance: " + particleEffectInstance);
        this.getGameScreen().getParticleManager().getParticleEffectInstances().remove(this.particleEffectInstance);
    }

}
