package com.sakurawald.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TimerManager {
    public static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
}
