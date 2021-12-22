package com.sakurawald.logic.component;

import com.artemis.PooledComponent;

import java.util.Calendar;

public class PlayerComponent extends PooledComponent {

    /* Physics Props */
    public int touchedPlatforms = 0;

    /* Player Fields */
    public int tokenCollected = 0;
    public double startGameTimestamp = Calendar.getInstance().getTimeInMillis();
    public int leftLives = 3;

    @Override
    protected void reset() {
        tokenCollected = 0;
        startGameTimestamp = Calendar.getInstance().getTimeInMillis();
        leftLives = 3;
    }
}