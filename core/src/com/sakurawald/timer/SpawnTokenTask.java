package com.sakurawald.timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.sakurawald.logic.component.PlayerComponent;
import com.sakurawald.logic.component.TokenComponent;
import com.sakurawald.logic.entity.Libraries;
import com.sakurawald.logic.script.CollisionDestroyeScript;
import com.sakurawald.logic.script.TokenScript;
import com.sakurawald.manager.ApplicationAssetManager;
import com.sakurawald.screen.GameScreen;
import com.sakurawald.util.MathUtils;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.utils.ItemWrapper;

import java.util.ArrayList;

public class SpawnTokenTask extends SpawnEntityTask {


    public SpawnTokenTask(GameScreen gameScreen) {
        super(gameScreen, TokenComponent.class, 5, 1, 15);
    }

    @Override
    public void spawnEntity() {
        // Generate random position and random velocity
        Vector2 randomPosition = MathUtils.getRandomPositionInWorld(this.getGameScreen());

        // Cancel spawn if outside the world
        if (getGameScreen().isOutsideWorld(randomPosition)) {
            return;
        }

        Gdx.app.log("SpawnTokenTask", "Spawning token at " + randomPosition);

        // Create new token
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
        itemWrapper.addScript(new CollisionDestroyeScript(this.getGameScreen(), PlayerComponent.class, true, false));
    }

}
