package com.sakurawald.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.sakurawald.logic.bean.Player;
import com.sakurawald.logic.bean.PlayerControllerListener;
import com.sakurawald.logic.component.PlayerComponent;
import com.sakurawald.logic.entity.Libraries;
import com.sakurawald.screen.GameScreen;
import com.sakurawald.util.MathUtils;
import games.rednblack.editor.renderer.utils.ItemWrapper;
import lombok.Getter;

import java.util.ArrayList;

public class PlayerManager {

    private static final String PLAYER_LIBRARY_ID = Libraries.PLAYER;

    @Getter
    private final GameScreen gameScreen;

    @Getter
    private final ArrayList<Player> players = new ArrayList<>();

    public PlayerManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }


    public Player createPlayer() {
        /* Create player entity */
        Vector2 position = MathUtils.getCenterPosition(this.gameScreen);
        int entityID = ApplicationAssetManager.createEntityFromLibrary(
                this.gameScreen.getSceneLoader()
                , PLAYER_LIBRARY_ID
                , "Default"
                , position.x
                , position.y
                , new ArrayList<>() {
                    {
                        this.add(PlayerComponent.class);
                    }
                });

        // Call ECS system to process
        this.getGameScreen().getSceneLoader().getEngine().process();

        /* Construct and add the player to list */
        ItemWrapper itemWrapper = new ItemWrapper(entityID, this.gameScreen.getSceneLoader().getEngine());
        Player player = new Player(this, itemWrapper);
        this.players.add(player);

        /* Add InputProcessor */
        Gdx.input.setInputProcessor(new PlayerControllerListener(player));


        return player;
    }


    public Player getSolePlayer() {
        return this.players.get(0);
    }

}
