package com.sakurawald.timer;

import com.badlogic.gdx.utils.Timer;
import com.sakurawald.screen.GameScreen;
import lombok.Getter;
import lombok.Setter;

public abstract class ApplicationTimerTask extends Timer.Task {

    @Getter
    protected final GameScreen gameScreen;

    @Getter
    protected final int taskScheduledIntervalSecond;

    @Getter
    @Setter
    protected int taskDoIntervalSeconds;

    private int accumulateSeconds;

    public ApplicationTimerTask(GameScreen gameScreen, int taskScheduledIntervalSecond, int taskDoIntervalSecond) {
        this.gameScreen = gameScreen;
        this.taskScheduledIntervalSecond = taskScheduledIntervalSecond;
        this.taskDoIntervalSeconds = taskDoIntervalSecond;
    }

    @Override
    public final void run() {
        // Accumulate the delta.
        accumulateSeconds += taskScheduledIntervalSecond;

        // If the delta is greater than the interval, do the task.
        if (accumulateSeconds >= taskDoIntervalSeconds) {
            accumulateSeconds -= taskDoIntervalSeconds;
           doRun();
        }
    }

    public void scheduleSelf() {
        Timer.instance().scheduleTask(this, taskScheduledIntervalSecond, taskScheduledIntervalSecond);
    }

    public abstract void doRun();

}
