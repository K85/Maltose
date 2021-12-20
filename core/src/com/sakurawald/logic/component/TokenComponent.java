package com.sakurawald.logic.component;

import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Pool;

public class TokenComponent extends ApplicationComponent {

    @Override
    protected void reset() {
        this.value = 1;
    }
}
