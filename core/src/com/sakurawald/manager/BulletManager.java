package com.sakurawald.manager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sakurawald.logic.component.BulletComponent;
import com.sakurawald.logic.script.BulletScript;
import com.sakurawald.logic.script.DestroyedByBoundaryScript;
import com.sakurawald.screen.GameScreen;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.utils.ItemWrapper;
import lombok.Getter;

import java.util.ArrayList;

public class BulletManager {

    @Getter
    private final GameScreen gameScreen;

    public BulletManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void createBullet() {

        /* Get player body */
        ItemWrapper playerItemWrapper = this.getGameScreen().getPlayerManager().getSolePlayer();
        PhysicsBodyComponent playerPhysicsBodyComponent = playerItemWrapper.getComponent(PhysicsBodyComponent.class);
        Body playerBody = playerPhysicsBodyComponent.body;
        Vector2 playerPosition = playerBody.getPosition();
        Vector2 bulletPosition = playerPosition.add(new Vector2(2f, 2f));

        /* Create entity from library */
        SceneLoader sceneLoader = this.getGameScreen().getSceneLoader();
        int entityID = ApplicationAssetManager.createEntityFromLibrary(sceneLoader, "library_bullet",
                "Default", bulletPosition.x, bulletPosition.y, new ArrayList<>() {
                    {
                        this.add(BulletComponent.class);
                    }
                });

        /* Add scripts */
        ItemWrapper itemWrapper = new ItemWrapper(entityID, sceneLoader.getEngine());
        itemWrapper.addScript(new DestroyedByBoundaryScript(this.getGameScreen()));
        itemWrapper.addScript(new BulletScript(this.getGameScreen()));

        // Call ECS system to process
        sceneLoader.getEngine().process();

        /* Apply the initial velocity to the bullet */
        PhysicsBodyComponent physicsBodyComponent = sceneLoader.getEngine().getEntity(entityID).getComponent(PhysicsBodyComponent.class);
        // Double check that the physics body exists
        if (physicsBodyComponent != null && physicsBodyComponent.body != null) {

            Vector2 orientation = playerBody.getLinearVelocity();
            Vector2 velocity = orientation.add(new Vector2(1, 1));

            /* Apply the impulse to the bullet */
//            physicsBodyComponent.body.applyLinearImpulse(velocity, physicsBodyComponent.body.getWorldCenter(), true);
        }

    }
}
