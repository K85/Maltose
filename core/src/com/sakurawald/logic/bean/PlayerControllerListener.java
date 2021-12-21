package com.sakurawald.logic.bean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.sakurawald.logic.enums.PlayerInstruction;

public class PlayerControllerListener extends InputAdapter {

    // TODO key pressed masks

    private final Player player;

    public PlayerControllerListener(Player player) {
        this.player = player;
    }

    @Override
    public boolean keyTyped(char character) {

        Gdx.app.log("PlayerControllerListener", "keyTyped: " + character);
        /* Handle inputs */
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.player.sendInstruction(PlayerInstruction.MOVE_LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.player.sendInstruction(PlayerInstruction.MOVE_RIGHT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.player.sendInstruction(PlayerInstruction.MOVE_UP);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.player.sendInstruction(PlayerInstruction.MOVE_DOWN);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            this.player.sendInstruction(PlayerInstruction.SHOOT);
        }

        return true;
    }

}
