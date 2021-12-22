package com.sakurawald.logic.component;

import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Pool;

public class TokenComponent extends ApplicationComponent {

    public TokenComponent() {
        // The value in super class will be initialized to 0.
        super.value = 1;
    }

    @Override
    protected void reset() {
        this.value = 1;
        this.ignored = false;
    }
}
