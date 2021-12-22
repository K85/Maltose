package com.sakurawald.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.sakurawald.screen.GameScreen;
import com.sakurawald.timer.ApplicationTimerTask;
import lombok.Getter;

import java.util.ArrayList;

public class TimerManager {

    @Getter
    private final GameScreen gameScreen;

    private final ArrayList<Timer.Task> tasks = new ArrayList<>();

    public TimerManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void scheduleTask(ApplicationTimerTask applicationTimerTask) {

        /* Schedule the timer task */
        Timer.Task task = applicationTimerTask.scheduleSelf();

        /* Add timer task to the list */
        this.tasks.add(task);
    }

    public void cancelAllTasks() {
        tasks.forEach(task -> {
            Gdx.app.getApplicationLogger().debug("cancelAllTasks","Cancelling task: " + task);
            task.cancel();
        });
    }

}
