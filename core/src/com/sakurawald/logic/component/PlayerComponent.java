package com.sakurawald.logic.component;

import com.artemis.PooledComponent;

public class PlayerComponent extends PooledComponent {

    public int touchedPlatforms = 0;

    public int diamondsCollected = 0;

    @Override
    protected void reset() {

    }
}