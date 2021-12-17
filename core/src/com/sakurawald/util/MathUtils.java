package com.sakurawald.util;

import com.badlogic.gdx.math.Vector2;
import com.sakurawald.screen.GameScreen;

import java.util.Random;

public class MathUtils {
    private static final Random random = new Random();

    public static int getRandomNumber(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static float getRandomNumber(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    public static Vector2 getRandomPositionInWorld() {
        Vector2 position = new Vector2();
        Vector2 world_size = GameScreen.getConstantWorldSize();
        position.x = getRandomNumber(0, world_size.x);
        position.y = getRandomNumber(0, world_size.y);
        return position;
    }

    public static Vector2 getRandomVelocity(float max_velocity) {
        Vector2 velocity = new Vector2();
        velocity.x = getRandomNumber(-max_velocity, max_velocity);
        velocity.y = getRandomNumber(-max_velocity, max_velocity);
        return velocity;
    }

}
