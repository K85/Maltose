package com.sakurawald.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.sakurawald.logic.bean.Player;
import com.sakurawald.logic.bean.PlayerControllerListener;
import com.sakurawald.logic.component.PlayerComponent;
import com.sakurawald.logic.entity.Libraries;
import com.sakurawald.logic.enums.GroupIndexes;
import com.sakurawald.logic.enums.PlayerInstruction;
import com.sakurawald.screen.GameScreen;
import com.sakurawald.util.MathUtils;
import games.rednblack.editor.renderer.utils.ItemWrapper;
import lombok.Getter;

import java.util.ArrayList;

public class PlayerManager {

    @Getter
    private final GameScreen gameScreen;

    @Getter
    private final ArrayList<Player> players = new ArrayList<>();

    public PlayerManager(GameScreen gameScreen) {
        /* Set fields */
        this.gameScreen = gameScreen;
    }

    public Player createPlayer() {
        /* Create player entity */
        Vector2 position = MathUtils.getCenterPosition(this.gameScreen);
        int entityID = ApplicationAssetManager.createEntityFromLibrary(
                this.gameScreen.getSceneLoader()
                , Libraries.PLAYER
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

        return player;
    }


    public void process(float delta) {
        processInstruction();
    }

    public void processInstruction() {

        /* Select Instruction */
        if (PlayerControllerListener.pressedKeys.getOrDefault(Input.Keys.H, false)) {
            this.distributeInstruction(PlayerInstruction.MOVE_LEFT);
        }
        if (PlayerControllerListener.pressedKeys.getOrDefault(Input.Keys.L, false)) {
            this.distributeInstruction(PlayerInstruction.MOVE_RIGHT);
        }
        if (PlayerControllerListener.pressedKeys.getOrDefault(Input.Keys.K, false)) {
            this.distributeInstruction(PlayerInstruction.MOVE_UP);
        }
        if (PlayerControllerListener.pressedKeys.getOrDefault(Input.Keys.J, false)) {
            this.distributeInstruction(PlayerInstruction.MOVE_DOWN);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            this.distributeInstruction(PlayerInstruction.SHOOT);
        }

    }

    public void distributeInstruction(PlayerInstruction instruction) {
        /* Distribute the instruction */
        Gdx.app.getApplicationLogger().debug("PlayerManager", "distributeInstruction = " + instruction);
        for (Player player : this.players) {
            player.sendInstruction(instruction);
        }
    }

    public Player getSolePlayer() {
        return this.players.get(0);
    }

}
