package com.sakurawald.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.sakurawald.screen.GameScreen;
import com.talosvfx.talos.runtime.ParticleEffectDescriptor;
import com.talosvfx.talos.runtime.ParticleEffectInstance;
import com.talosvfx.talos.runtime.render.SpriteBatchParticleRenderer;
import lombok.Getter;

import java.util.ArrayList;

public class ParticleManager {

    @Getter
    private final GameScreen gameScreen;
    @Getter
    private final PolygonSpriteBatch polygonSpriteBatch = new PolygonSpriteBatch();
    @Getter
    private final SpriteBatchParticleRenderer spriteBatchParticleRenderer = new SpriteBatchParticleRenderer();

    private final ArrayList<ParticleEffectInstance> particleEffectInstances = new ArrayList<>();

    public ParticleManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public static ParticleEffectDescriptor buildParticleEffectDescriptor(String particlePath) {
        FileHandle fileHandle = Gdx.files.internal(ApplicationAssetManager.PARTICLE_JSON_PATH + particlePath);
//        FileHandle fileHandle = ApplicationAssetManager.getAsyncResourceLoader().getTalosVFX(particlePath);
        TextureAtlas textureAtlas = ApplicationAssetManager.getTextureAtlas();
        return new ParticleEffectDescriptor(fileHandle, textureAtlas);
    }

    public void submitParticleEffectInstance(ParticleEffectInstance particleEffectInstance) {
        this.particleEffectInstances.add(particleEffectInstance);
    }

    public void cancelParticleEffectInstance(ParticleEffectInstance particleEffectInstance) {
        this.particleEffectInstances.remove(particleEffectInstance);
    }

    @Deprecated
    public static ParticleEffect buildParticleEffect() {
        throw new RuntimeException("Not implemented");
    }

    public void process(float delta) {

        polygonSpriteBatch.setProjectionMatrix(getGameScreen().getViewport().getCamera().combined);

        polygonSpriteBatch.begin();
        this.spriteBatchParticleRenderer.setBatch(polygonSpriteBatch);

        /* Render -> All the ParticleEffectInstance */
        Gdx.app.getApplicationLogger().debug("ParticleManager", "ParticleManager.process() -> particleEffectInstances.size() = " + particleEffectInstances.size());
        for (ParticleEffectInstance particleEffectInstance : this.particleEffectInstances) {
            Gdx.app.getApplicationLogger().debug("ParticleManager", "rendering particle effect: " + particleEffectInstance + ", isComplete = " + particleEffectInstance.isComplete());
            particleEffectInstance.render(spriteBatchParticleRenderer);
        }
        polygonSpriteBatch.end();
    }

}


