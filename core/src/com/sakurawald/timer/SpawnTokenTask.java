package com.sakurawald.timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.sakurawald.logic.component.TokenComponent;
import com.sakurawald.manager.ApplicationAssetManager;
import com.sakurawald.screen.GameScreen;
import com.sakurawald.util.MathUtils;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;

import java.util.ArrayList;

public class SpawnTokenTask extends SpawnEntityTask {

    public SpawnTokenTask(GameScreen gameScreen) {
        super(gameScreen, TokenComponent.class, 30, 10);
    }

    @Override
    public void spawnEntity() {
        // Generate random position and random velocity
        Vector2 randomPosition = MathUtils.getRandomPositionInWorld(this.getGameScreen());
        Vector2 playerPosition = getGameScreen().getPlayer().getComponent(PhysicsBodyComponent.class).body.getPosition();

        // Cancel spawn if outside the world
        if (getGameScreen().isOutsideWorld(randomPosition)) {
            return;
        }

        // Cancel spawn if player is too close
        if (randomPosition.dst(playerPosition) < 10) {
            return;
        }

        Gdx.app.log("SpawnTokenTask", "Spawning token at " + randomPosition);

        // Create new token
        SceneLoader sceneLoader = this.getGameScreen().getSceneLoader();
        ApplicationAssetManager.loadCompositeFromLibrary(sceneLoader, "library_token", "Default", randomPosition.x, randomPosition.y, new ArrayList<Class<?>>(
        ){
            {
                this.add(TokenComponent.class);
            }
        });
        sceneLoader.getEngine().process();
    }
}
