package com.sakurawald.timer;

import com.artemis.BaseComponentMapper;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.github.czyzby.lml.vis.parser.impl.attribute.table.UseCellDefaultsLmlAttribute;
import com.sakurawald.logic.component.DeadlyObstacleComponent;
import com.sakurawald.logic.component.StoneComponent;
import com.sakurawald.manager.ApplicationAssetManager;
import com.sakurawald.screen.GameScreen;
import com.sakurawald.util.MathUtils;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import lombok.Getter;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Vector;

public class SpawnStoneTask extends SpawnEntityTask {

    public static final float STONE_MAX_VELOCITY = 5;

    public SpawnStoneTask(GameScreen gameScreen) {
        super(gameScreen, StoneComponent.class, 30,1);
    }

    @Override
    public void spawnEntity() {
        // Generate random position and random velocity
        Vector2 randomPosition = MathUtils.getRandomPositionInWorld();
        Vector2 randomVelocity = MathUtils.getRandomVelocity(STONE_MAX_VELOCITY);
        Gdx.app.log("SpawnStoneTask", "Spawning stone at " + randomPosition);
        Gdx.app.log("SpawnStoneTask", "Spawning stone with velocity " + randomVelocity);

        // Create new stone
        SceneLoader sceneLoader = this.getGameScreen().getSceneLoader();

        // Give random velocity
        int entityID = ApplicationAssetManager.loadCompositeFromLibrary(sceneLoader, "library_stone","Default", randomPosition.x, randomPosition.y, DeadlyObstacleComponent.class);

        // Call ECS system to process
        sceneLoader.getEngine().process();
        PhysicsBodyComponent physicsBodyComponent = sceneLoader.getEngine().getEntity(entityID).getComponent(PhysicsBodyComponent.class);

        // Double check that the physics body exists
        if (physicsBodyComponent != null && physicsBodyComponent.body != null) {
            physicsBodyComponent.body.applyLinearImpulse(randomVelocity, physicsBodyComponent.body.getWorldCenter(), true);
        }
    }


}
