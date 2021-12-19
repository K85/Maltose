package com.sakurawald.timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.sakurawald.logic.component.DeadlyObstacleComponent;
import com.sakurawald.logic.component.StoneComponent;
import com.sakurawald.logic.script.DestroyedByBoundary;
import com.sakurawald.manager.ApplicationAssetManager;
import com.sakurawald.screen.GameScreen;
import com.sakurawald.util.MathUtils;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.utils.ItemWrapper;

import java.util.ArrayList;

public class SpawnStoneTask extends SpawnEntityTask {

    public static final float STONE_MAX_VELOCITY = 5;

    public SpawnStoneTask(GameScreen gameScreen) {
        super(gameScreen, StoneComponent.class, 30,1);
    }

    @Override
    public void spawnEntity() {
        // Generate random position and random velocity
        Vector2 randomPosition = MathUtils.getRandomPositionInWorld(this.getGameScreen());
        Vector2 randomVelocity = MathUtils.getRandomVelocity(STONE_MAX_VELOCITY);
        Gdx.app.log("SpawnStoneTask", "Spawning stone at " + randomPosition);
        Gdx.app.log("SpawnStoneTask", "Spawning stone with velocity " + randomVelocity);

        // Cancel spawn if outside of world bounds
        if (getGameScreen().isOutsideWorld(randomPosition)) {
            return;
        }

        // Create new stone
        SceneLoader sceneLoader = this.getGameScreen().getSceneLoader();

        // Apply random velocity
        int entityID = ApplicationAssetManager.createEntityFromLibrary(sceneLoader, "library_stone","Default", randomPosition.x, randomPosition.y, new ArrayList<Class<?>>(){
            {
                this.add(DeadlyObstacleComponent.class);
            }
        });

        // Add Scripts
        ItemWrapper itemWrapper = new ItemWrapper(entityID, sceneLoader.getEngine());
        itemWrapper.addScript(new DestroyedByBoundary(this.getGameScreen()));

        // Call ECS system to process
        sceneLoader.getEngine().process();
        PhysicsBodyComponent physicsBodyComponent = sceneLoader.getEngine().getEntity(entityID).getComponent(PhysicsBodyComponent.class);

        // Double check that the physics body exists
        if (physicsBodyComponent != null && physicsBodyComponent.body != null) {
            physicsBodyComponent.body.applyLinearImpulse(randomVelocity, physicsBodyComponent.body.getWorldCenter(), true);
        }
    }

}
