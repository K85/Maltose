package com.sakurawald.logic.bean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.sakurawald.logic.enums.PlayerInstruction;

import java.util.HashMap;

public class PlayerControllerListener extends InputAdapter {

    public static final HashMap<Integer, Boolean> pressedKeys = new HashMap<Integer, Boolean>();

    @Override
    public boolean keyDown(int keycode) {
        PlayerControllerListener.pressedKeys.put(keycode, true);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        PlayerControllerListener.pressedKeys.put(keycode, false);
        return false;
    }

}
