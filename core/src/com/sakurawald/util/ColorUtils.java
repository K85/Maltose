package com.sakurawald.util;

import com.badlogic.gdx.graphics.Color;


public class ColorUtils {

    public static Color getColor(int r, int g, int b, int a) {
        return new Color(r / 255f, g / 255f, b / 255f, a / 255f);
    }

    public static Color getRandomColor() {
        return getColor(
            (int) (Math.random() * 255),
            (int) (Math.random() * 255),
            (int) (Math.random() * 255),
            (int) (Math.random() * 255)
        );
    }
}
