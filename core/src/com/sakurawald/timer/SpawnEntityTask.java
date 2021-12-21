package com.sakurawald.timer;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.sakurawald.screen.GameScreen;
import lombok.Getter;
import lombok.Setter;

public abstract class SpawnEntityTask<T extends Component> extends ApplicationTimerTask {

    @Getter
    private final GameScreen gameScreen;
    @Getter
    private final Class<T> classType;
    @Getter
    @Setter
    private int maxSpawnedEntityAmount;

    public SpawnEntityTask(GameScreen gameScreen, Class<T> classType, int taskDoIntervalSeconds, int maxSpawnedEntityAmount) {
        super(gameScreen, 1, taskDoIntervalSeconds);
        this.gameScreen = gameScreen;
        this.classType = classType;
        this.taskDoIntervalSeconds = taskDoIntervalSeconds;
        this.maxSpawnedEntityAmount = maxSpawnedEntityAmount;
    }

    @Override
    public void doRun() {
        int spawnedEntityAmount = this.getSpawnedTypeEntities().size();
        int maxSpawnedEntityAmount = this.getMaxSpawnedEntityAmount();
        Gdx.app.log("SpawnEntityTask", "Spawning entity: classType = " + classType.getSimpleName() + ", maxSpawnedEntityAmount = " + maxSpawnedEntityAmount + ", spawnedEntityAmount = " + spawnedEntityAmount);

        // Cancel task if max amount of spawned entities is reached
        if (spawnedEntityAmount < maxSpawnedEntityAmount) {
            spawnEntity();
        }
    }

    public IntBag getSpawnedTypeEntities() {
        return this.getGameScreen().getSceneLoader().getEngine().getAspectSubscriptionManager().get(Aspect.all(classType)).getEntities();
    }

    public abstract void spawnEntity();
}
