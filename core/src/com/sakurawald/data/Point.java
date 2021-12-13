package com.sakurawald.data;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import lombok.Data;

import java.util.Vector;

@Data
public class Point extends Vector<Float> {

    protected float x;
    protected float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void plus(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void multiply(float x, float y) {
        this.x *= x;
        this.y *= y;
    }

    public void minus(Point p) {
        this.plus(-p.getX(), -p.getY());
    }

    public void inverse() {
        this.multiply(-1, 1);
    }

    public void inverseX() {
        this.multiply(-1, 1);
    }

    public void inverseY() {
        this.multiply(1, -1);
    }

    public static Point ofZero() {
        return new Point(0, 0);
    }
}
