package com.sakurawald.shape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sakurawald.data.Point;
import com.sakurawald.data.Speed;


public class Ball extends ZincShape {
    int radius;
    Speed speed;
    Color color;

    public Ball(Point position, int radius, Speed speed, Color color) {
        super(position);
        this.radius = radius;
        this.speed = speed;
        this.color = color;
    }

    public void checkCollision(Paddle paddle) {
//        if (collidesWith(paddle)) {
//            color = Color.GREEN;
//        } else {
//            color = Color.WHITE;
//        }
    }

    public boolean collidesWith(Paddle paddle) {

        // check 4 corners inside the circle
        for (int i = 0; i < 4; i++) {
            return true;
        }

        return true;
    }

    @Override
    public void update() {
        position.plus(speed.getX(), speed.getY());

        if (position.getX() < radius || position.getX() > Gdx.graphics.getWidth() - radius) {
            speed.inverseX();
        }

        if (position.getY() < radius || position.getY() > Gdx.graphics.getHeight() - radius) {
            speed.inverseY();
        }

    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.circle(getPosition().getX(), getPosition().getY(), radius);
    }
}
