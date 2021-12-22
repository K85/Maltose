package com.sakurawald.timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.sakurawald.logic.component.PlayerComponent;
import com.sakurawald.logic.component.TokenComponent;
import com.sakurawald.logic.entity.Libraries;
import com.sakurawald.logic.script.CollisionDestroyScript;
import com.sakurawald.logic.script.DestroyedByBoundaryScript;
import com.sakurawald.logic.script.TokenScript;
import com.sakurawald.manager.ApplicationAssetManager;
import com.sakurawald.screen.GameScreen;
import com.sakurawald.util.MathUtils;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.utils.ItemWrapper;

import java.util.ArrayList;

public class SpawnTokenTask extends SpawnEntityTask {

    public SpawnTokenTask(GameScreen gameScreen) {
        super(gameScreen, TokenComponent.class, 5, 3, 1);
    }

    @Override
    public void spawnEntity() {
        // Generate random position and random velocity
        Vector2 randomPosition = MathUtils.getRandomPositionInWorld(this.getGameScreen());

        // Cancel spawn if outside the world (also: the token will not be spawned near the boundary)
        if (getGameScreen().isOutsideWorld(randomPosition, 1f)) {
            return;
        }

        // Create new token
        Gdx.app.log("SpawnTokenTask", "Spawning token at " + randomPosition);

        SceneLoader sceneLoader = this.getGameScreen().getSceneLoader();
        int entityID = ApplicationAssetManager.createEntityFromLibrary(sceneLoader, Libraries.TOKEN, "Default", randomPosition.x, randomPosition.y, new ArrayList<>(
        ) {
           {
                this.add(TokenComponent.class);
            }
        });

        // Add scripts
        ItemWrapper itemWrapper = new ItemWrapper(entityID, this.getGameScreen().getSceneLoader().getEngine());
        itemWrapper.addScript(new TokenScript(this.getGameScreen()));
        itemWrapper.addScript(new CollisionDestroyScript(this.getGameScreen(), PlayerComponent.class, true, false));
    }

}
