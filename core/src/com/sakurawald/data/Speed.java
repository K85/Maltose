package com.sakurawald.data;

public class Speed extends Point {

    public Speed(float x, float y) {
        super(x, y);
    }

    public static Speed ofZero() {
        return new Speed(0, 0);
    }
}
