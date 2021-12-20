package com.sakurawald.logic.component;

import com.artemis.PooledComponent;
import lombok.Data;

@Data
public abstract class ApplicationComponent extends PooledComponent {

    private int value;
    private boolean ignored;

}
