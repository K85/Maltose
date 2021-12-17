package com.sakurawald.timer;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.sakurawald.screen.GameScreen;
import lombok.Getter;
import lombok.Setter;

public abstract class SpawnEntityTask<T extends Component> extends Timer.Task {

    @Getter
    private GameScreen gameScreen;
    @Getter
    private Class<T> classType;
    @Getter
    private int taskIntervalSeconds;
    @Getter
    @Setter
    private int maxSpawnedEntityAmount;

    public SpawnEntityTask(GameScreen gameScreen, Class<T> classType, int maxSpawnedEntityAmount, int taskIntervalSeconds) {
        this.gameScreen = gameScreen;
        this.classType = classType;
        this.maxSpawnedEntityAmount = maxSpawnedEntityAmount;
        this.taskIntervalSeconds = taskIntervalSeconds;
    }

    @Override
    public void run() {
        Gdx.app.log("SpawnEntityTask", "Spawning entity: classType = " + classType.getSimpleName());

        // Cancel task if max amount of spawned entities is reached
        if (getSpawnedTypeEntities().size() < maxSpawnedEntityAmount) {
            spawnEntity();
        }
    }

    public void scheduleSelf() {
        Timer.instance().scheduleTask(this, taskIntervalSeconds, taskIntervalSeconds);
    }

    public IntBag getSpawnedTypeEntities() {
        return this.getGameScreen().getSceneLoader().getEngine().getAspectSubscriptionManager().get(Aspect.all(classType)).getEntities();
    }

    public abstract void spawnEntity();
}
