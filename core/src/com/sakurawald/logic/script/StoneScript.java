package com.sakurawald.logic.script;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.sakurawald.logic.adapter.PhysicsContactAdapter;
import com.sakurawald.logic.component.PlayerComponent;
import com.sakurawald.logic.component.StoneComponent;
import com.sakurawald.manager.ParticleManager;
import com.sakurawald.screen.GameScreen;
import com.sakurawald.util.MathUtils;
import com.talosvfx.talos.runtime.ParticleEffectDescriptor;
import com.talosvfx.talos.runtime.ParticleEffectInstance;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import lombok.Getter;

public class StoneScript extends ApplicationScript implements PhysicsContactAdapter {


    public static final float STONE_MAX_VELOCITY = 5;

    @Getter
    private static final ParticleEffectDescriptor fireParticleEffectDescriptor = ParticleManager.buildParticleEffectDescriptor("fire.p");

    /* Mapper */
    private ComponentMapper<StoneComponent> stoneMapper;
    private ComponentMapper<PlayerComponent> playerMapper;

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
        this.getGameScreen().getParticleManager().submitParticleEffectInstance(particleEffectInstance);
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
        this.getGameScreen().getParticleManager().cancelParticleEffectInstance(particleEffectInstance);
    }

    @Override
    public void beginContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

        StoneComponent stoneComponent = stoneMapper.get(this.getEntity());

        /* Collide with: PlayerComponent */
        PlayerComponent playerComponent = playerMapper.get(contactEntity);
        if (playerComponent != null && !stoneComponent.ignored) {
            Gdx.app.getApplicationLogger().debug("StoneScript", "beginContact: stone = " + stoneComponent);
            stoneComponent.ignored = true;
            playerComponent.leftLives--;
        }
    }
}
