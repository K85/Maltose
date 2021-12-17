package com.sakurawald.logic.component;

import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Pool;

public class TokenComponent extends PooledComponent {

    public int value = 1;

    @Override
    protected void reset() {
        value = 1;
    }
}
