package com.sakurawald.manager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.sakurawald.logic.component.BulletComponent;
import com.sakurawald.logic.entity.Libraries;
import com.sakurawald.logic.enums.FilterBits;
import com.sakurawald.logic.enums.GroupIndexes;
import com.sakurawald.logic.script.BulletScript;
import com.sakurawald.logic.script.DestroyedByBoundaryScript;
import com.sakurawald.screen.GameScreen;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.data.CompositeItemVO;
import com.uwsoft.editor.renderer.data.CompositeVO;
import com.uwsoft.editor.renderer.data.PhysicsBodyDataVO;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.data.PolygonShapeVO;
import games.rednblack.editor.renderer.utils.ItemWrapper;
import lombok.Getter;

import javax.swing.*;
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
        Vector2 bulletPosition = playerBody.getPosition().add(0, 2);

        /* Create entity from library */
        SceneLoader sceneLoader = this.getGameScreen().getSceneLoader();

        int entityID = ApplicationAssetManager.createEntityFromLibrary(sceneLoader, Libraries.BULLET,
                "Default", bulletPosition.x, bulletPosition.y, new ArrayList<>() {
                    {
                        this.add(BulletComponent.class);
                    }
                });
        sceneLoader.getEngine().process();

        /* Add scripts */
        ItemWrapper itemWrapper = new ItemWrapper(entityID, sceneLoader.getEngine());
        itemWrapper.addScript(new DestroyedByBoundaryScript(this.getGameScreen()));
        itemWrapper.addScript(new BulletScript(this.getGameScreen()));
    }
}
