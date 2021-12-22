package com.sakurawald.timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.sakurawald.logic.component.DeadlyObstacleComponent;
import com.sakurawald.logic.component.PlayerComponent;
import com.sakurawald.logic.component.StoneComponent;
import com.sakurawald.logic.entity.Libraries;
import com.sakurawald.logic.script.CollisionDestroyeScript;
import com.sakurawald.logic.script.DestroyedByBoundaryScript;
import com.sakurawald.logic.script.StoneScript;
import com.sakurawald.manager.ApplicationAssetManager;
import com.sakurawald.screen.GameScreen;
import com.sakurawald.util.MathUtils;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.utils.ItemWrapper;

import java.util.ArrayList;

public class SpawnStoneTask extends SpawnEntityTask {


    public SpawnStoneTask(GameScreen gameScreen) {
        super(gameScreen, StoneComponent.class, 1, 5, 30);
    }

    @Override
    public void spawnEntity() {
        // Generate random position and random velocity
        Vector2 randomPosition = MathUtils.getRandomPositionInWorld(this.getGameScreen());
        Gdx.app.log("SpawnStoneTask", "Spawning stone at " + randomPosition);

        // Cancel spawn if outside of world bounds
        if (getGameScreen().isOutsideWorld(randomPosition)) {
            return;
        }

        // Cancel spawn if player is too close
        Vector2 playerPosition = getGameScreen().getPlayerManager().getSolePlayer().getPlayerItemWrapper().getComponent(PhysicsBodyComponent.class).body.getPosition();
        // Please Note that if the safe distance is too large, no stones will be generated if the player is at the right place.
        if (randomPosition.dst(playerPosition) < 1) {
            return;
        }

        // Create new stone
        SceneLoader sceneLoader = this.getGameScreen().getSceneLoader();
        int entityID = ApplicationAssetManager.createEntityFromLibrary(sceneLoader, Libraries.STONE, "Default", randomPosition.x, randomPosition.y, new ArrayList<>() {
            {
                this.add(DeadlyObstacleComponent.class);
                this.add(StoneComponent.class);
            }
        });

        // Add Scripts
        ItemWrapper itemWrapper = new ItemWrapper(entityID, sceneLoader.getEngine());
        itemWrapper.addScript(new DestroyedByBoundaryScript(this.getGameScreen()));
        itemWrapper.addScript(new StoneScript(this.getGameScreen()));
        itemWrapper.addScript(new CollisionDestroyeScript(this.getGameScreen(), StoneComponent.class, true, false));
        itemWrapper.addScript(new CollisionDestroyeScript(this.getGameScreen(), PlayerComponent.class, true, false));
    }

}
