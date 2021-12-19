package com.sakurawald.manager;

import com.badlogic.gdx.math.Vector2;
import com.sakurawald.logic.component.PlayerComponent;
import com.sakurawald.screen.GameScreen;
import com.sakurawald.util.MathUtils;
import games.rednblack.editor.renderer.utils.ItemWrapper;
import lombok.Getter;

import java.util.ArrayList;

public class PlayerManager {

    private static final String PLAYER_LIBRARY_ID = "library_player";

    @Getter
    private final GameScreen gameScreen;

    @Getter
    private final ArrayList<ItemWrapper> players = new ArrayList<>();

    public PlayerManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }


    // TODO modify the player's mass to 0.0f
    public ItemWrapper createPlayer() {
        /* Create player entity */
        Vector2 position = MathUtils.getCenterPosition(this.gameScreen);
        int entityID = ApplicationAssetManager.getInstance().createEntityFromLibrary(
                this.gameScreen.getSceneLoader()
                , PLAYER_LIBRARY_ID
                , "Default"
                , position.x
                , position.y
                , new ArrayList<>(){
                    {
                        this.add(PlayerComponent.class);
                    }
                });

        // TODO addComponentByTag

        // Call ECS system to process
        this.getGameScreen().getSceneLoader().getEngine().process();

        /* Add the player to list */
        ItemWrapper itemWrapper = new ItemWrapper(entityID, this.gameScreen.getSceneLoader().getEngine());
        this.players.add(itemWrapper);


        return itemWrapper;
    }

    public ItemWrapper getSolePlayer() {
        return this.players.get(0);
    }

}
