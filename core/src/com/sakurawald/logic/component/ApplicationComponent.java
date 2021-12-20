package com.sakurawald.logic.component;

import com.artemis.PooledComponent;
import lombok.Data;

public abstract class ApplicationComponent extends PooledComponent {

    public int value;
    public boolean ignored;

    @Override
    protected void reset() {
        value = 0;
        ignored = false;
    }

}
