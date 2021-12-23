package com.sakurawald.manager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sakurawald.logic.component.BulletComponent;
import com.sakurawald.logic.component.StoneComponent;
import com.sakurawald.logic.entity.Libraries;
import com.sakurawald.logic.script.BulletScript;
import com.sakurawald.logic.script.CollisionDestroyScript;
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
        ItemWrapper playerItemWrapper = this.getGameScreen().getPlayerManager().getSolePlayer().getPlayerItemWrapper();
        PhysicsBodyComponent playerPhysicsBodyComponent = playerItemWrapper.getComponent(PhysicsBodyComponent.class);
        Body playerBody = playerPhysicsBodyComponent.body;
        Vector2 bulletPosition = playerBody.getPosition().add(0, 0.2f);

        /* Create entity from library */
        SceneLoader sceneLoader = this.getGameScreen().getSceneLoader();

        int entityID = ApplicationAssetManager.createEntityFromLibrary(sceneLoader, Libraries.BULLET,
                "Default", bulletPosition.x, bulletPosition.y);
        sceneLoader.getEngine().process();

        /* Add scripts */
        ItemWrapper itemWrapper = new ItemWrapper(entityID, sceneLoader.getEngine());
        itemWrapper.addScript(new DestroyedByBoundaryScript(this.getGameScreen()));
        itemWrapper.addScript(new CollisionDestroyScript(this.getGameScreen(), StoneComponent.class, false, true));
        itemWrapper.addScript(new BulletScript(this.getGameScreen()));
    }
}
