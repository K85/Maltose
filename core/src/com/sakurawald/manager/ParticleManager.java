package com.sakurawald.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.sakurawald.screen.GameScreen;
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

    @Getter
    private final ArrayList<ParticleEffectInstance> particleEffectInstances = new ArrayList<>();

    public ParticleManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void handle(float delta) {

        polygonSpriteBatch.setProjectionMatrix(getGameScreen().getViewport().getCamera().projection);
        polygonSpriteBatch.begin();
        getSpriteBatchParticleRenderer().setBatch(polygonSpriteBatch);

        /* Render -> All the ParticleEffectInstance */
        for (ParticleEffectInstance particleEffectInstance : getParticleEffectInstances()) {
            Gdx.app.getApplicationLogger().debug("ParticleManager", "rendering particle effect: " + particleEffectInstance);
            particleEffectInstance.update(delta);
            particleEffectInstance.render(spriteBatchParticleRenderer);
        }

        polygonSpriteBatch.end();
    }

}


